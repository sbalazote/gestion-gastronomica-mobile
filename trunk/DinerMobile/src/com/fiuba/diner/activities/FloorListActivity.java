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
import com.fiuba.diner.adapters.FloorListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.util.TableLayoutUtils;

public class FloorListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.floor_list);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.floorListView);

		FloorListAdapter adapter = new FloorListAdapter(this, DataHolder.getFloors());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Floor floor = (Floor) parent.getItemAtPosition(position);
				DataHolder.setCurrentFloor(floor);
				DataHolder.setCurrentTableLayout(TableLayoutUtils.convertLayout(floor));
				Intent intent = new Intent(FloorListActivity.this, FloorActivity.class);
				FloorListActivity.this.startActivity(intent);
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
		// TODO desloguear
	}

}
