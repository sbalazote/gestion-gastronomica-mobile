package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.CategoryListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Product;

public class CategoryListActivity extends Activity {

	public static final String EXTRA_CATEGORY = "com.fiuba.diner.activities.CATEGORY";
	public final String LOG_OUT = "event_logout";

	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_category_list);
		this.session = new SessionManager(this.getApplicationContext());
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));
		this.populateList();
	}

	// handler for received Intents for logout event
	private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// do your code snippet here.
			CategoryListActivity.this.finish();
		}
	};

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.categoryListView);

		CategoryListAdapter adapter = new CategoryListAdapter(this, DataHolder.getCategories());
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
		this.session.logoutUser();
		Intent intent = new Intent(this.LOG_OUT);
		// send the broadcast to all activities who are listening
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

}
