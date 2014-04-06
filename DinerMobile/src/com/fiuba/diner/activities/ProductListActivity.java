package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.ProductListAdapter;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;

public class ProductListActivity extends Activity {

	public static final String EXTRA_PRODUCT = "com.fiuba.diner.activities.PRODUCT";

	private Subcategory subcategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.subcategory = (Subcategory) this.getIntent().getSerializableExtra(SubcategoryListActivity.EXTRA_SUBCATEGORY);

		this.setTitle(this.subcategory.getDescription());
		this.setContentView(R.layout.activity_product_list);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.productListView);

		ProductListAdapter adapter = new ProductListAdapter(this, this.subcategory.getProducts());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Product product = (Product) parent.getItemAtPosition(position);

				Intent returnIntent = new Intent();
				returnIntent.putExtra(ProductListActivity.EXTRA_PRODUCT, product);
				ProductListActivity.this.setResult(RESULT_OK, returnIntent);
				ProductListActivity.this.finish();
			}

		});
	}

}
