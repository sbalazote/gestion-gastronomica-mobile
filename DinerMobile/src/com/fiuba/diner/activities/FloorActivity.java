package com.fiuba.diner.activities;

import static com.fiuba.diner.constant.Constants.HARDCODED_WAITER_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.TableImageAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.TableLayout;

public class FloorActivity extends Activity {

	public final String LOG_OUT = "event_logout";

	private BaseAdapter adapter;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.floor);
		this.session = new SessionManager(this.getApplicationContext());
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));

		GridView gridview = (GridView) this.findViewById(R.id.floorGridView);
		gridview.setNumColumns(DataHolder.getCurrentFloor().getWidth());

		this.adapter = new TableImageAdapter(this);
		gridview.setAdapter(this.adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TableLayout tableLayout = DataHolder.getCurrentTableLayout()[position];
				if (tableLayout != null) {
					Table table = tableLayout.getTable();
					DataHolder.setCurrentTable(table);

					if (TableStateHelper.AVAILABLE.getState().getId().equals(table.getState().getId())) {
						this.openDialog(view, table);

					} else if (table.getWaiter() == null || HARDCODED_WAITER_ID.equals(table.getWaiter().getId())) {
						Intent intent = new Intent(FloorActivity.this, OrderActivity.class);
						FloorActivity.this.startActivity(intent);

					} else {
						this.notAvailableDialog(view, table);
					}
				}
			}

			private void openDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FloorActivity.this);
				alertDialogBuilder.setMessage("�Confirma la apertura de la mesa " + table.getId() + "?");
				alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						table.setState(TableStateHelper.OPEN.getState());
						// table.setWaiter(DataHolder.getCurrentWaiter());
						FloorActivity.this.adapter.notifyDataSetChanged();
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
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FloorActivity.this);
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

	// handler for received Intents for logout event
	private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// do your code snippet here.
			FloorActivity.this.finish();
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
}
