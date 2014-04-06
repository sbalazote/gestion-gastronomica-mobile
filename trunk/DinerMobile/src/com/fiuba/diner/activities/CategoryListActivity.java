package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.CategoryListAdapter;
import com.fiuba.diner.helper.ProductProviderHelper;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Product;

public class CategoryListActivity extends Activity {

	public static final String EXTRA_CATEGORY = "com.fiuba.diner.activities.CATEGORY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_category_list);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.categoryListView);

		CategoryListAdapter adapter = new CategoryListAdapter(this, ProductProviderHelper.getCategories());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category category = (Category) parent.getItemAtPosition(position);

				Intent intent = new Intent(CategoryListActivity.this, SubcategoryListActivity.class);
				intent.putExtra(EXTRA_CATEGORY, category);
				CategoryListActivity.this.startActivityForResult(intent, 1);
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Product selectedProduct = (Product) data.getSerializableExtra(ProductListActivity.EXTRA_PRODUCT);
			Intent returnIntent = new Intent();
			returnIntent.putExtra(ProductListActivity.EXTRA_PRODUCT, selectedProduct);
			this.setResult(RESULT_OK, returnIntent);
			this.finish();
		}
	}

}
