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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.OrderProductListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.OrderStateHelper;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.tasks.ConfirmOrderParam;
import com.fiuba.diner.tasks.ConfirmOrderTask;
import com.fiuba.diner.tasks.ObtainOrderTask;

public class OrderActivity extends Activity {

	private List<OrderDetail> products;
	private BigDecimal total = BigDecimal.valueOf(0);
	private OrderProductListAdapter adapter;
	private ExpandableListView listView;
	private int lastExpandedPosition = -1;
	private Integer tableId;
	private Order order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order);

		TextView tableTextView = (TextView) this.findViewById(R.id.orderTableTextView);
		tableTextView.setText(this.getIntent().getStringExtra(TableListActivity.EXTRA_TITLE));
		this.tableId = Integer.parseInt(this.getIntent().getStringExtra(TableListActivity.TABLE_ID));

		this.products = new ArrayList<OrderDetail>();
		this.listView = (ExpandableListView) this.findViewById(R.id.orderListView);
		this.adapter = new OrderProductListAdapter(this, this.products);
		this.listView.setAdapter(this.adapter);

		// aca obtengo la orden si existe.
		ObtainOrderTask obtainOrderTask = new ObtainOrderTask(null);

		try {
			obtainOrderTask.execute(this.tableId).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (DataHolder.getActualOrder() != null) {
			this.order = DataHolder.getActualOrder();
			EditText dinersEditText = (EditText) this.findViewById(R.id.dinersEditText);
			dinersEditText.setText(String.valueOf(this.order.getCustomerAmount()));

			for (OrderDetail orderDetail : this.order.getDetails()) {
				this.products.add(orderDetail);
				this.adapter.notifyDataSetChanged();
			}
		} else {
			this.order = new Order();
		}

		this.listView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				if (OrderActivity.this.lastExpandedPosition != -1 && groupPosition != OrderActivity.this.lastExpandedPosition) {
					OrderActivity.this.listView.collapseGroup(OrderActivity.this.lastExpandedPosition);
				}
				OrderActivity.this.lastExpandedPosition = groupPosition;
			}
		});

	}

	public void addProduct(View view) {
		Intent intent = new Intent(this, CategoryListActivity.class);
		this.startActivityForResult(intent, 1);
	}

	public void deleteProduct(View view) {
		int position = this.listView.getPositionForView(view) - 1;

		if (this.products.get(position).getState() != null) {
			if (this.products.get(position).getState().getId().equals(OrderStateHelper.REQUESTED.getState().getId())
					|| this.products.get(position).getState().getId().equals(OrderStateHelper.NEW.getState().getId())) {
				this.delete(position);
			} else {
				this.openDialog(view);
			}
		} else {
			this.delete(position);
		}
	}

	private void delete(Integer position) {

		OrderDetail orderDetail = this.products.get(position);
		this.products.remove(orderDetail);
		this.updateTotal();

		this.adapter.notifyDataSetChanged();

		int count = this.adapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			this.listView.collapseGroup(i);
		}
		this.lastExpandedPosition = -1;
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

		for (OrderDetail orderDetail : this.products) {
			if (orderDetail.getId() == null) {
				this.order.addDetail(orderDetail);
				Product product = orderDetail.getProduct();
				orderDetail.setAmount(product.getDetails().get(0).getAmount());
				orderDetail.setComment(product.getDetails().get(0).getComment());
				orderDetail.setState(OrderStateHelper.REQUESTED.getState());
			}
		}

		List<Table> tables = new ArrayList<Table>();
		tables.add(DataHolder.getActualTable());
		this.order.setTable(tables);

		ConfirmOrderParam confirmOrderParam = new ConfirmOrderParam();
		confirmOrderParam.setOrder(this.order);
		confirmOrderParam.setTableId(this.tableId);

		new ConfirmOrderTask(null).execute(confirmOrderParam);
		System.out.println(this.order.getId());
		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Product selectedProduct = (Product) data.getSerializableExtra(ProductListActivity.EXTRA_PRODUCT);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(selectedProduct);
			this.products.add(orderDetail);
			this.adapter.notifyDataSetChanged();
			this.updateTotal();
		}
	}

	public void updateTotal() {
		double total = 0;
		for (OrderDetail orderDetail : this.products) {
			total += (orderDetail.getProduct().getPrice() * orderDetail.getProduct().getDetails().get(0).getAmount());
		}

		this.total = BigDecimal.valueOf(total);
		DecimalFormat formatter = new DecimalFormat("0.00");
		TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
		totalTextView.setText("$" + formatter.format(this.total));
	}

}
