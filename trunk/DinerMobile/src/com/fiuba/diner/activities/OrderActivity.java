package com.fiuba.diner.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.ProductListAdapter;
import com.fiuba.diner.model.Product;

public class OrderActivity extends Activity {

	private List<Product> products;
	private Double total = Double.valueOf(0);
	private ProductListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order);

		this.setTitle(this.getIntent().getStringExtra(TableListActivity.EXTRA_TITLE));

		this.products = new ArrayList<Product>();
		ListView listView = (ListView) this.findViewById(R.id.orderListView);

		this.adapter = new ProductListAdapter(this, this.products);
		listView.setAdapter(this.adapter);
	}

	public void addProduct(View view) {
		Intent intent = new Intent(this, CategoryListActivity.class);
		this.startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Product selectedProduct = (Product) data.getSerializableExtra(ProductListActivity.EXTRA_PRODUCT);
			this.products.add(selectedProduct);
			this.total = this.total + selectedProduct.getPrice();
			TextView totalTextView = (TextView) this.findViewById(R.id.orderTotalTextView);
			totalTextView.setText("$" + String.valueOf(this.total));
			this.adapter.notifyDataSetChanged();
		}
	}

}
