package com.fiuba.diner.activities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask.Status;
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
import com.fiuba.diner.tasks.SetUpTask;

public class OrderActivity extends Activity {

	private List<Product> products;
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
		
		this.products = new ArrayList<Product>();
		this.listView = (ExpandableListView) this.findViewById(R.id.orderListView);
		this.adapter = new OrderProductListAdapter(this, this.products);
		this.listView.setAdapter(this.adapter);
		
		//aca obtengo la orden si existe.
		ObtainOrderTask obtainOrderTask = new ObtainOrderTask(null);
		
		try {
			obtainOrderTask.execute(tableId).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if(DataHolder.getActualOrder() != null){
			this.order = DataHolder.getActualOrder();
			for(OrderDetail orderDetail: this.order.getDetails()){
				this.products.add(orderDetail.getProduct());
				this.adapter.notifyDataSetChanged();
			}
		}else{
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

		Product product = this.products.get(position);
		product.getDetails().clear();
		this.products.remove(position);

		this.updateTotal();

		this.adapter.notifyDataSetChanged();

		int count = this.adapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			this.listView.collapseGroup(i);
		}
		this.lastExpandedPosition = -1;
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

		for (Product product : this.products) {
			OrderDetail detail = new OrderDetail();
			detail.setAmount(product.getDetails().get(0).getAmount());
			detail.setComment(product.getDetails().get(0).getComment());
			detail.setProduct(product);
			detail.setState(OrderStateHelper.REQUESTED.getState());
			//detail.setRequestDate(new Date());
			this.order.addDetail(detail);
		}
		
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
			this.products.add(selectedProduct);
			this.adapter.notifyDataSetChanged();
			this.updateTotal();
		}
	}

	public void updateTotal() {
		double total = 0;
		for (Product product : this.products) {
			total += (product.getPrice() * product.getDetails().get(0).getAmount());
		}

		this.total = BigDecimal.valueOf(total);
		DecimalFormat formatter = new DecimalFormat("0.00");
		TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
		totalTextView.setText("$" + formatter.format(this.total));
	}

}
