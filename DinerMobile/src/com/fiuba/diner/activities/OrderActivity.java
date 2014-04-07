package com.fiuba.diner.activities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.OrderProductListAdapter;
import com.fiuba.diner.model.Product;

public class OrderActivity extends Activity {

	private List<Product> products;
	private BigDecimal total = BigDecimal.valueOf(0);
	private OrderProductListAdapter adapter;
	private ExpandableListView listView;
	private int lastExpandedPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order);

		TextView tableTextView = (TextView) this.findViewById(R.id.orderTableTextView);
		tableTextView.setText(this.getIntent().getStringExtra(TableListActivity.EXTRA_TITLE));

		this.products = new ArrayList<Product>();
		this.listView = (ExpandableListView) this.findViewById(R.id.orderListView);

		this.adapter = new OrderProductListAdapter(this, this.products);
		this.listView.setAdapter(this.adapter);

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
		this.total = this.total.subtract(BigDecimal.valueOf(product.getPrice()));

		DecimalFormat formatter = new DecimalFormat("0.00");
		TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
		totalTextView.setText("$" + formatter.format(this.total));

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
