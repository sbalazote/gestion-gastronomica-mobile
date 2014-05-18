package com.fiuba.diner.activities;

import static com.fiuba.diner.constant.Constants.HARDCODED_WAITER_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.TableListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;

public class TableListActivity extends Activity {

	public final String LOG_OUT = "event_logout";

	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_table_list);
		this.session = new SessionManager(this.getApplicationContext());
		this.populateList();
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));
	}

	// handler for received Intents for logout event
	private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// do your code snippet here.
			TableListActivity.this.finish();
		}
	};

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.tableListView);

		TableListAdapter adapter = new TableListAdapter(this, DataHolder.getTables());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Table table = (Table) parent.getItemAtPosition(position);
				DataHolder.setCurrentTable(table);

				// Si la mesa esta disponible la abro
				if (TableStateHelper.AVAILABLE.getState().getId().equals(table.getState().getId())) {
					this.openDialog(view, table);

					// Si ya esta abierta y es mia, sigo
				} else if (table.getWaiter() == null || HARDCODED_WAITER_ID.equals(table.getWaiter().getId())) {
					Intent intent = new Intent(TableListActivity.this, OrderActivity.class);
					TableListActivity.this.startActivity(intent);

					// Si ya esta abierta pero no es mia, muestro mensaje
				} else {
					this.notAvailableDialog(view, table);
				}
			}

			private void openDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage("�Confirma la apertura de la mesa " + table.getId() + "?");
				alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						table.setState(TableStateHelper.OPEN.getState());
						// table.setWaiter(DataHolder.getCurrentWaiter());
						TextView stateTextView = (TextView) view.findViewById(R.id.stateTextView);
						stateTextView.setText(table.getState().getDescription());
						stateTextView.setTextColor(Color.BLUE);
					}
				});
				alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}

			private void notAvailableDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage("La mesa no est� disponible");
				alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
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
