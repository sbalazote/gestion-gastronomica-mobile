package com.fiuba.diner.activities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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

public class OrderActivity extends Activity {

	private BigDecimal total = BigDecimal.valueOf(0);
	private OrderListAdapter adapter;
	private ListView listView;
	private Order order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order);

		TextView tableTextView = (TextView) this.findViewById(R.id.orderTableTextView);
		tableTextView.setText(this.getIntent().getStringExtra(TableListActivity.EXTRA_TITLE));

		// aca obtengo la orden si existe.
		ObtainOrderTask obtainOrderTask = new ObtainOrderTask(null);

		try {
			obtainOrderTask.execute(DataHolder.getActualTable().getId()).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (DataHolder.getActualOrder() != null) {
			this.order = DataHolder.getActualOrder();
			EditText dinersEditText = (EditText) this.findViewById(R.id.dinersEditText);
			dinersEditText.setText(String.valueOf(this.order.getCustomerAmount()));

		} else {
			this.order = new Order();
		}

		this.listView = (ListView) this.findViewById(R.id.orderListView);
		if (this.order.getDetails() == null) {
			this.order.setDetails(new ArrayList<OrderDetail>());
		}
		this.adapter = new OrderListAdapter(this, this.order.getDetails());
		this.listView.setAdapter(this.adapter);

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
			} else {
				this.openDialog(view);
			}
		} else {
			this.delete(position, view);
		}
	}

	private void delete(Integer position, View view) {
		OrderDetail orderDetail = this.order.getDetails().get(position);
		this.order.getDetails().remove(orderDetail);
		this.updateTotal();
		this.adapter.notifyDataSetChanged();
	}

	private void openDialog(final View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderActivity.this);
		alertDialogBuilder.setMessage("No puede eliminarse el pedido ya ha sido Solicitado");
		alertDialogBuilder.setNeutralButton("OK", null);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void cancelOrder(View view) throws Throwable {
		this.finish();
	}

	public void confirmOrder(View view) throws Throwable {
		EditText dinersEditText = (EditText) this.findViewById(R.id.dinersEditText);
		// TODO: cambiar. Validar que no pueda ser < 1
		if (!dinersEditText.getText().toString().isEmpty()) {
			this.order.setCustomerAmount(Integer.valueOf(dinersEditText.getText().toString()));
		} else {
			this.order.setCustomerAmount(1);
		}

		for (OrderDetail orderDetail : this.order.getDetails()) {
			if (orderDetail.getId() == null) {
				orderDetail.setState(OrderStateHelper.REQUESTED.getState());
				orderDetail.setComment("");
				orderDetail.setAmount(1);
			}
		}

		this.order.setDetails(this.order.getDetails());

		List<Table> tables = new ArrayList<Table>();
		tables.add(DataHolder.getActualTable());
		this.order.setTables(tables);

		new ConfirmOrderTask(null).execute(this.order);
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
		}
	}

	public void updateTotal() {
		double total = 0;
		for (OrderDetail orderDetail : this.order.getDetails()) {
			total += (orderDetail.getProduct().getPrice() * orderDetail.getAmount());
		}

		this.total = BigDecimal.valueOf(total);
		DecimalFormat formatter = new DecimalFormat("0.00");
		TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
		totalTextView.setText("$" + formatter.format(this.total));
	}

}
