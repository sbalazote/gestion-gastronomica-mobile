package com.fiuba.diner.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.OrderListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.OrderStateHelper;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.tasks.ConfirmOrderTask;
import com.fiuba.diner.tasks.ObtainOrderTask;
import com.fiuba.diner.util.Formatter;

public class OrderActivity extends Activity {

	private OrderListAdapter adapter;
	private ListView listView;
	private Order order;
	private boolean hasChanged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order);

		TextView tableTextView = (TextView) this.findViewById(R.id.orderTableTextView);
		tableTextView.setText("Mesa: " + DataHolder.getCurrentTable().getId());

		// aca obtengo la orden si existe.
		ObtainOrderTask obtainOrderTask = new ObtainOrderTask(null);

		try {
			obtainOrderTask.execute(DataHolder.getCurrentTable().getId()).get();
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

	public void addProduct(View view) {
		Intent intent = new Intent(this, CategoryListActivity.class);
		this.startActivityForResult(intent, 1);
	}

	public void deleteProduct(View view) {
		int position = this.listView.getPositionForView(view);

		if (this.order.getDetails().get(position).getState() != null) {
			if (this.order.getDetails().get(position).getState().getId().equals(OrderStateHelper.REQUESTED.getState().getId())
					|| this.order.getDetails().get(position).getState().getId().equals(OrderStateHelper.NEW.getState().getId())) {
				this.delete(position, view);
				this.hasChanged = true;
			} else {
				this.openDialog(view);
			}
		} else {
			this.delete(position, view);
			this.hasChanged = true;
		}
	}

	public void confirmDelivered(View view) {
		int position = this.listView.getPositionForView(view);
		OrderDetail orderDetail = this.order.getDetails().get(position);
		this.openConfirmDialog(view, orderDetail, this.adapter);
	}

	private void openConfirmDialog(final View view, final OrderDetail orderDetail, final Adapter adapter) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("¿Esta seguro que desea confirmar la entrega?");
		alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				orderDetail.setState(OrderStateHelper.DELIVERED.getState());
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

	private void openDialog(final View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("No puede eliminarse el pedido, ya ha ingresado a la cocina.");
		alertDialogBuilder.setNeutralButton("Aceptar", null);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void confirmOrder(View view) throws Throwable {
		Spinner dinersSpinner = (Spinner) this.findViewById(R.id.dinersSpinner);
		this.order.setCustomerAmount(Integer.parseInt(dinersSpinner.getSelectedItem().toString()));

		for (OrderDetail orderDetail : this.order.getDetails()) {
			if (orderDetail.getId() == null) {
				orderDetail.setState(OrderStateHelper.REQUESTED.getState());
				orderDetail.setRequestDate(new Date());
			}
		}

		this.order.setDetails(this.order.getDetails());

		List<Table> tables = new ArrayList<Table>();
		tables.add(DataHolder.getCurrentTable());
		this.order.setTables(tables);

		new ConfirmOrderTask(null).execute(this.order);

		// Updatear el Registration ID
		/*
		 * Device device = new Device(); device.setId("00B0D086BBF7"); device.setRegistrationId("pepe"); device.setWaiter(null); new
		 * UpdateDeviceTask(null).execute(device);
		 */

		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Product selectedProduct = (Product) data.getSerializableExtra(ProductListActivity.EXTRA_PRODUCT);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(selectedProduct);
			orderDetail.setState(OrderStateHelper.NEW.getState());
			orderDetail.setAmount(1);
			this.order.getDetails().add(orderDetail);
			this.adapter.notifyDataSetChanged();
			this.updateTotal();
			this.hasChanged = true;
		}
	}

	public void updateTotal() {
		double total = 0;
		double dinnerServiceTotal = 0;

		if (this.order.getDetails() != null) {
			for (OrderDetail orderDetail : this.order.getDetails()) {
				total += (orderDetail.getProduct().getPrice() * orderDetail.getAmount());
			}
		}

		// Se agrega el servicio de mesa
		dinnerServiceTotal = this.getDinnerServiceTotal();
		TextView dinnerServiceTotalText = (TextView) this.findViewById(R.id.dinnerServiceTotalTextView);
		dinnerServiceTotalText.setText(Formatter.getPriceFormat(dinnerServiceTotal));

		total += dinnerServiceTotal;

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
			super.onBackPressed();
		} else {
			super.onBackPressed();
		}

	}

	private void openConfirmExit() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("Existen cambios sin guardar, ¿desea continuar?");
		alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
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

}
