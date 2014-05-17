package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.SubcategoryListAdapter;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;

public class SubcategoryListActivity extends Activity {

	public static final String EXTRA_SUBCATEGORY = "com.fiuba.diner.activities.SUBCATEGORY";

	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.category = (Category) this.getIntent().getSerializableExtra(CategoryListActivity.EXTRA_CATEGORY);

		this.setTitle(this.category.getDescription());
		this.setContentView(R.layout.activity_subcategory_list);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.subcategoryListView);

		SubcategoryListAdapter adapter = new SubcategoryListAdapter(this, this.category.getSubcategories());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Subcategory subcategory = (Subcategory) parent.getItemAtPosition(position);

				Intent intent = new Intent(SubcategoryListActivity.this, ProductListActivity.class);
				intent.putExtra(EXTRA_SUBCATEGORY, subcategory);
				SubcategoryListActivity.this.startActivityForResult(intent, 1);
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
		// TODO desloguear
	}

}
