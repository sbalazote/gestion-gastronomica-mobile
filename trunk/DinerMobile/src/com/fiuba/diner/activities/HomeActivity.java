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

import com.fiuba.diner.R;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.tasks.RegisterGcmTask;
import com.fiuba.diner.tasks.SetUpTask;

public class HomeActivity extends Activity implements Caller<Void> {

	public final String LOG_OUT = "event_logout";

	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.hardcodedWaiter);
		this.session = new SessionManager(this.getApplicationContext());
		new SetUpTask(this).execute();
		new RegisterGcmTask(this).execute();
		this.session.checkLogin();
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));
	}

	// handler for received Intents for logout event
	private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// do your code snippet here.
			HomeActivity.this.finish();
		}
	};

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

	@Override
	public void afterCall(Void result) {
		this.setView();
	}

	private void setView() {
		this.setContentView(R.layout.home);
	}

	public void showTables(View view) {
		Intent intent = new Intent(this, FloorListActivity.class);
		this.startActivity(intent);
	}

	public void listTables(View view) {
		Intent intent = new Intent(this, TableListActivity.class);
		this.startActivity(intent);
	}

}
