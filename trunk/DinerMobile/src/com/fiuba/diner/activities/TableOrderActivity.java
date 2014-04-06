package com.fiuba.diner.activities;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.ProductListAdapter;
import com.fiuba.diner.fragments.WaiterFragment;
import com.fiuba.diner.fragments.OrderFragment;
import com.fiuba.diner.model.Product;

public class TableOrderActivity extends ActionBarActivity {

	private List<Product> products;
	private ProductListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_order);

		// Se carga el nombre del mozo en el fragment
		 ((WaiterFragment)getSupportFragmentManager().findFragmentById(R.id.FrgWaiter)).setWaiterName("JOSE");
		 
		// Se carga la mesa en el fragment
		 ((OrderFragment)getSupportFragmentManager().findFragmentById(R.id.FrgOrder)).setTable("Mesa 8");	
		 
		this.products = new ArrayList<Product>();
		ListView listView = (ListView) this.findViewById(R.id.orderListView);

		this.adapter = new ProductListAdapter(this, this.products);
		listView.setAdapter(this.adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			this.adapter.notifyDataSetChanged();
		}
	}

}
