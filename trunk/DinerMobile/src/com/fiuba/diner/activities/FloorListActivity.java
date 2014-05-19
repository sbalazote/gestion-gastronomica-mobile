package com.fiuba.diner.activities;

import java.util.concurrent.ExecutionException;

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
import com.fiuba.diner.adapters.FloorListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.tasks.GetTablesTask;
import com.fiuba.diner.util.TableLayoutUtils;

public class FloorListActivity extends Activity {

	public final String LOG_OUT = "event_logout";

	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.session = new SessionManager(this.getApplicationContext());
		this.setTitle("Mozo: " + this.session.getUserDetails().get("name"));
		this.setContentView(R.layout.floor_list);
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));
		this.populateList();
	}

	// handler for received Intents for logout event
	private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// do your code snippet here.
			FloorListActivity.this.finish();
		}
	};

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.floorListView);

		FloorListAdapter adapter = new FloorListAdapter(this, DataHolder.getFloors());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// Obtengo las mesas
				GetTablesTask getTablesTask = new GetTablesTask(null);
				try {
					getTablesTask.execute().get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

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
		this.session.logoutUser();
		Intent intent = new Intent(this.LOG_OUT);
		// send the broadcast to all activities who are listening
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

}
