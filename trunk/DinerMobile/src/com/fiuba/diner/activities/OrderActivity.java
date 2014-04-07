package com.fiuba.diner.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.OrderProductListAdapter;
import com.fiuba.diner.model.Product;

public class OrderActivity extends Activity {

	private List<Product> products;
	private Double total = Double.valueOf(0);
	private OrderProductListAdapter adapter;
	ExpandableListView listView;

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
	}

	public void addProduct(View view) {
		Intent intent = new Intent(this, CategoryListActivity.class);
		this.startActivityForResult(intent, 1);
	}

	public void deleteProduct(View view) {
		Integer positionToDelete = null;
		for (int i = 0; i < this.products.size(); ++i) {
			if (view.getId() == this.products.get(i).getDetails().get(0).getViewId()) {
				positionToDelete = i;
				break;
			}
		}
		if (positionToDelete != null) {
			Product product = this.products.get(positionToDelete);
			product.getDetails().clear();
			this.products.remove(positionToDelete.intValue());
			this.total = this.total - product.getPrice();

			DecimalFormat formatter = new DecimalFormat("#.00");
			TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
			totalTextView.setText("$" + formatter.format(this.total));
			this.adapter.notifyDataSetChanged();
		}
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
			this.total = this.total + selectedProduct.getPrice();

			DecimalFormat formatter = new DecimalFormat("#.00");
			TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
			totalTextView.setText("$" + formatter.format(this.total));
			this.adapter.notifyDataSetChanged();
		}
	}

}
