package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.tasks.RegisterGcmTask;
import com.fiuba.diner.tasks.SetUpTask;

public class HomeActivity extends Activity implements Caller<Void> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.hardcodedWaiter);
		new SetUpTask(this).execute();
		new RegisterGcmTask(this).execute();
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
