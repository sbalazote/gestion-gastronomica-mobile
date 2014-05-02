package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
