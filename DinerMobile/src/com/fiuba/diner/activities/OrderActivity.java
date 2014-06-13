package com.fiuba.diner.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.fiuba.diner.R;
import com.fiuba.diner.adapters.OrderListAdapter;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.OrderDetailStateHelper;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.OrderDetailState;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.tasks.ChangeLockStateTableTask;
import com.fiuba.diner.tasks.CloseOrderTask;
import com.fiuba.diner.tasks.ConfirmOrderTask;
import com.fiuba.diner.tasks.GetCategoriesTask;
import com.fiuba.diner.tasks.GetCouponTask;
import com.fiuba.diner.tasks.ObtainOrderTask;
import com.fiuba.diner.util.Formatter;

public class OrderActivity extends Activity implements Caller<Integer> {

	public final String LOG_OUT = "event_logout";

	private OrderListAdapter adapter;
	private ListView listView;
	private Order order;
	private boolean hasChanged;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.session = new SessionManager(this.getApplicationContext());
		this.setTitle("Mozo: " + this.session.getUserDetails().get("name"));
		this.setContentView(R.layout.activity_order);
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));

		LinearLayout couponLayout = (LinearLayout) this.findViewById(R.id.couponLayout);
		couponLayout.setVisibility(LinearLayout.GONE);
		LinearLayout subtotalLayout = (LinearLayout) this.findViewById(R.id.subtotalLayout);
		subtotalLayout.setVisibility(LinearLayout.GONE);

		TextView tableTextView = (TextView) this.findViewById(R.id.orderTableTextView);
		tableTextView.setText("Mesa: " + DataHolder.getCurrentTable().getId());

		// aca obtengo la orden si existe.
		ObtainOrderTask obtainOrderTask = new ObtainOrderTask(null);
		ChangeLockStateTableTask changeLockStateTableTask = new ChangeLockStateTableTask(null);

		try {
			obtainOrderTask.execute(DataHolder.getCurrentTable().getId()).get();
			DataHolder.getCurrentTable().setLocked(true);
			DataHolder.getCurrentTable().setWaiter(DataHolder.getCurrentWaiter());

			changeLockStateTableTask.execute(DataHolder.getCurrentTable());

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		int customerAmount = com.fiuba.diner.constant.Constants.MIN_CUSTOMERS;
		if (DataHolder.getCurrentOrder() != null) {
			this.order = DataHolder.getCurrentOrder();
			customerAmount = this.order.getCustomerAmount();
		} else {
			this.order = new Order();
		}

		if (this.order.getDetails() == null) {
			this.order.setDetails(new ArrayList<OrderDetail>());
		}
		this.listView = (ListView) this.findViewById(R.id.orderListView);
		this.adapter = new OrderListAdapter(this, this.order.getDetails());
		this.listView.setAdapter(this.adapter);
		this.hasChanged = false;
		this.populateCustomerSpinner(customerAmount);

	}

	// handler for received Intents for logout event
	private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// do your code snippet here.
			OrderActivity.this.finish();
		}
	};

	public void addProduct(View view) {

		// Obtengo las mesas
		GetCategoriesTask getCategoriesTask = new GetCategoriesTask(null);
		try {
			getCategoriesTask.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		Intent intent = new Intent(this, CategoryListActivity.class);
		this.startActivityForResult(intent, 1);
	}

	public void deleteProduct(View view) {
		int position = this.listView.getPositionForView(view);

		OrderDetailState state = this.order.getDetails().get(position).getState();
		if (state == null || state.equals(OrderDetailStateHelper.NEW.getState()) || state.equals(OrderDetailStateHelper.REQUESTED.getState())
				|| state.equals(OrderDetailStateHelper.CANCELLED.getState())) {
			this.delete(position, view);
			this.hasChanged = true;
		} else {
			this.openDialog("No puede eliminarse el pedido, ya ha ingresado a la cocina.");
		}
	}

	public void confirmDelivered(View view) {
		int position = this.listView.getPositionForView(view);
		OrderDetail orderDetail = this.order.getDetails().get(position);
		this.openConfirmDialog(view, orderDetail, this.adapter);
	}

	private void openConfirmDialog(final View view, final OrderDetail orderDetail, final Adapter adapter) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("¿Está seguro que desea confirmar la entrega?");
		alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				orderDetail.setState(OrderDetailStateHelper.DELIVERED.getState());
				OrderActivity.this.hasChanged = true;
				OrderActivity.this.adapter.notifyDataSetChanged();
			}
		});
		alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	private void delete(Integer position, View view) {
		OrderDetail orderDetail = this.order.getDetails().get(position);
		this.order.getDetails().remove(orderDetail);
		this.updateTotal();
		this.adapter.notifyDataSetChanged();
	}

	private void openDialog(final String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setNeutralButton("Aceptar", null);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void confirmOrder(View view) throws Throwable {
		Spinner dinersSpinner = (Spinner) this.findViewById(R.id.dinersSpinner);
		this.order.setCustomerAmount(Integer.parseInt(dinersSpinner.getSelectedItem().toString()));

		for (OrderDetail orderDetail : this.order.getDetails()) {
			if (orderDetail.getId() == null) {
				orderDetail.setState(OrderDetailStateHelper.REQUESTED.getState());
				orderDetail.setRequestDate(new Date());
			}
		}

		this.order.setDetails(this.order.getDetails());

		List<Table> tables = new ArrayList<Table>();
		tables.add(DataHolder.getCurrentTable());
		this.order.setTables(tables);

		new ConfirmOrderTask(this).execute(this.order);

		DataHolder.getCurrentTable().setLocked(false);
		new ChangeLockStateTableTask(null).execute(DataHolder.getCurrentTable());
		// Updatear el Registration ID
		/*
		 * Device device = new Device(); device.setId("00B0D086BBF7"); device.setRegistrationId("pepe"); device.setWaiter(null); new
		 * UpdateDeviceTask(null).execute(device);
		 */
	}

	public void readQR(View view) throws Throwable {
		// Intent intent = new Intent(this, ZBarScannerActivity.class);
		// this.startActivityForResult(intent, com.fiuba.diner.constant.Constants.ZBAR_SCANNER_REQUEST);

		/* Esto de aca no va solo esta agregado para las pruebas con el cupon id 1 */
		GetCouponTask getCouponTask = new GetCouponTask(null);
		try {
			getCouponTask.execute("1").get();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (DataHolder.getCoupon() == null) {
			Toast.makeText(this, "El cupón no es válido", Toast.LENGTH_SHORT).show();
		} else {
			LinearLayout couponLayout = (LinearLayout) this.findViewById(R.id.couponLayout);
			couponLayout.setVisibility(LinearLayout.VISIBLE);
			this.order.setCoupon(DataHolder.getCoupon());
			this.updateTotal();
			this.hasChanged = true;
		}
	}

	public void closeOrder(View view) throws Throwable {
		if (this.order.getDetails() == null || this.order.getDetails().isEmpty()) {
			this.openDialog("La mesa no tiene ningún pedido ingresado.");
			return;
		}

		for (OrderDetail orderDetail : this.order.getDetails()) {
			if (!orderDetail.getState().equals(OrderDetailStateHelper.DELIVERED.getState())
					&& !orderDetail.getState().equals(OrderDetailStateHelper.CANCELLED.getState())) {
				this.openDialog("Existen pedidos que no han sido entregados.");
				return;
			}
		}

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("¿Confirma que desea cerrar la mesa?");
		alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				new CloseOrderTask(null).execute(OrderActivity.this.order.getId());
				OrderActivity.this.finish();
			}
		});
		alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case com.fiuba.diner.constant.Constants.ZBAR_SCANNER_REQUEST:
		case com.fiuba.diner.constant.Constants.ZBAR_QR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {
				// Aca se va a buscar por id de cupon el cupon, si no existe o no esta vigente se informa que no es valido.
				GetCouponTask getCouponTask = new GetCouponTask(null);
				try {
					getCouponTask.execute(data.getStringExtra(ZBarConstants.SCAN_RESULT)).get();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

				if (DataHolder.getCoupon() == null) {
					Toast.makeText(this, "El cupón no es válido", Toast.LENGTH_SHORT).show();
				} else {
					LinearLayout couponLayout = (LinearLayout) this.findViewById(R.id.couponLayout);
					couponLayout.setVisibility(LinearLayout.VISIBLE);
					LinearLayout subtotalLayout = (LinearLayout) this.findViewById(R.id.subtotalLayout);
					subtotalLayout.setVisibility(LinearLayout.VISIBLE);
					this.order.setCoupon(DataHolder.getCoupon());
					this.updateTotal();
					this.hasChanged = true;
				}

			} else if (resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				if (!TextUtils.isEmpty(error)) {
					Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case 1:
			if (resultCode == RESULT_OK) {
				Product selectedProduct = (Product) data.getSerializableExtra(ProductListActivity.EXTRA_PRODUCT);
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProduct(selectedProduct);
				orderDetail.setState(OrderDetailStateHelper.NEW.getState());
				orderDetail.setAmount(1);
				this.order.getDetails().add(orderDetail);
				this.adapter.notifyDataSetChanged();
				this.updateTotal();
				this.hasChanged = true;
			}
			break;
		}
	}

	public void updateTotal() {
		double total = 0;
		double subtotal = 0;
		double dinnerServiceTotal = 0;
		double coupon = 0;

		if (this.order.getDetails() != null) {
			for (OrderDetail orderDetail : this.order.getDetails()) {
				if (!OrderDetailStateHelper.CANCELLED.getState().equals(orderDetail.getState())) {
					total += (orderDetail.getProduct().getPrice() * orderDetail.getAmount());
				}
			}
		}

		// Se agrega el servicio de mesa
		dinnerServiceTotal = this.getDinnerServiceTotal();
		TextView dinnerServiceTotalText = (TextView) this.findViewById(R.id.dinnerServiceTotalTextView);
		dinnerServiceTotalText.setText(Formatter.getPriceFormat(dinnerServiceTotal));

		subtotal = total + dinnerServiceTotal;

		LinearLayout subtotalLayout = (LinearLayout) this.findViewById(R.id.subtotalLayout);
		TextView subtotalTextVier = (TextView) this.findViewById(R.id.subtotalTextView);
		subtotalTextVier.setText(Formatter.getPriceFormat(subtotal));

		if (this.order.getCoupon() != null) {
			TextView couponTextView = (TextView) this.findViewById(R.id.couponTextView);
			LinearLayout couponLayout = (LinearLayout) this.findViewById(R.id.couponLayout);
			couponLayout.setVisibility(LinearLayout.VISIBLE);
			TextView couponLabelTextView = (TextView) this.findViewById(R.id.couponLabelTextView);
			couponLabelTextView.setText(this.order.getCoupon().getDescription());

			coupon = (total + dinnerServiceTotal) * this.order.getCoupon().getPercentage();
			couponTextView.setText(Formatter.getPriceFormat(coupon));
			Button btn = (Button) this.findViewById(R.id.qrCamera);
			btn.setVisibility(View.GONE);
			subtotalLayout.setVisibility(LinearLayout.VISIBLE);
		}

		total += dinnerServiceTotal - coupon;

		TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
		totalTextView.setText(Formatter.getPriceFormat(total));
	}

	public double getDinnerServiceTotal() {
		double dinnerServiceTotal = Double.valueOf(0);

		if (DataHolder.getParameter().getDinnerServiceActive()) {
			Spinner dinersSpinner = (Spinner) this.findViewById(R.id.dinersSpinner);
			dinnerServiceTotal = (Integer.parseInt(dinersSpinner.getSelectedItem().toString()) * DataHolder.getParameter().getDinnerServicePrice());
		}

		return dinnerServiceTotal;
	}

	public void populateCustomerSpinner(int customerAmount) {
		Spinner spinner = (Spinner) this.findViewById(R.id.dinersSpinner);
		List<String> list = new ArrayList<String>();

		// Se cargan los valores del combo
		for (int i = com.fiuba.diner.constant.Constants.MIN_CUSTOMERS; i <= com.fiuba.diner.constant.Constants.MAX_CUSTOMERS; i++) {
			list.add(String.valueOf(i));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		// Se setea el valor que trae la orden
		int spinnerPosition = dataAdapter.getPosition(String.valueOf(customerAmount));
		spinner.setSelection(spinnerPosition);

		// Se define el evento cuando cambia el valor del combo
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				OrderActivity.this.updateTotal();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (this.hasChanged) {
			this.openConfirmExit();
		} else {
			DataHolder.getCurrentTable().setLocked(false);
			new ChangeLockStateTableTask(null).execute(DataHolder.getCurrentTable());
			super.onBackPressed();
		}

	}

	private void openConfirmExit() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("Existen cambios sin guardar, ¿desea continuar?");
		alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				DataHolder.getCurrentTable().setLocked(false);
				new ChangeLockStateTableTask(null).execute(DataHolder.getCurrentTable());
				OrderActivity.super.onBackPressed();
			}
		});
		alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_logout:
			this.logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		this.getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void logout() {
		this.session.logoutUser();
		Intent intent = new Intent(this.LOG_OUT);
		// send the broadcast to all activities who are listening
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	@Override
	public void afterCall(Integer result) {
		if (result != null && result == 0) {
			this.openDialog("Acceso no autorizado");
			this.logout();
		} else {
			this.finish();
		}
	}

}
