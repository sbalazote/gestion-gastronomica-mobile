package com.fiuba.diner.activities;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.fiuba.diner.adapters.ProductListAdapter;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;

public class ProductListActivity extends Activity {

	public static final String EXTRA_PRODUCT = "com.fiuba.diner.activities.PRODUCT";
	public final String LOG_OUT = "event_logout";

	private Subcategory subcategory;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.subcategory = (Subcategory) this.getIntent().getSerializableExtra(SubcategoryListActivity.EXTRA_SUBCATEGORY);

		this.setTitle(this.subcategory.getDescription());
		this.setContentView(R.layout.activity_product_list);
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
			ProductListActivity.this.finish();
		}
	};

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.productListView);

		ProductListAdapter adapter = new ProductListAdapter(this, this.subcategory.getProducts());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Product product = (Product) parent.getItemAtPosition(position);
				if (product.getStock()) {
					Intent returnIntent = new Intent();
					returnIntent.putExtra(ProductListActivity.EXTRA_PRODUCT, product);
					ProductListActivity.this.setResult(RESULT_OK, returnIntent);
					ProductListActivity.this.finish();
				} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProductListActivity.this);
					alertDialogBuilder.setMessage("No hay stock del producto seleccionado.");
					alertDialogBuilder.setNeutralButton("Aceptar", null);
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			}
		});
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
