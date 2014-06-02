package com.fiuba.diner.activities;

import java.util.concurrent.ExecutionException;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.TableListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SessionManager;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.tasks.GetTablesTask;

public class TableListActivity extends Activity {

	public final String LOG_OUT = "event_logout";

	private SessionManager session;
	private TableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.session = new SessionManager(this.getApplicationContext());
		this.setTitle("Mozo: " + this.session.getUserDetails().get("name"));

		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(this.LOG_OUT));
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Obtengo las mesas
		GetTablesTask getTablesTask = new GetTablesTask(null);
		try {
			getTablesTask.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		this.setContentView(R.layout.activity_table_list);
		this.populateList();
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

		this.adapter = new TableListAdapter(this, DataHolder.getTables());
		listview.setAdapter(this.adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Table table = (Table) parent.getItemAtPosition(position);
				DataHolder.setCurrentTable(table);

				// Si la mesa esta disponible la abro
				if (TableStateHelper.AVAILABLE.getState().getId().equals(table.getState().getId())) {
					this.showConfirmationDialog(view, table);

				} else if (TableStateHelper.CLOSED.getState().getId().equals(table.getState().getId())) {
					this.showDialog(view, table, "La mesa está deshabilitada hasta que se procese el pago.");

					// Si ya esta abierta y es mia, sigo
				} else if (DataHolder.getCurrentWaiter().getId().equals(table.getWaiter().getId())) {
					if (table.getLocked() == false) {
						Intent intent = new Intent(TableListActivity.this, OrderActivity.class);
						TableListActivity.this.startActivity(intent);
					} else {
						this.showDialog(view, table, "La mesa no está disponible");
					}

					// Si ya esta abierta pero no es mia, muestro mensaje
				} else {
					if (!DataHolder.getCurrentWaiter().getId().equals(table.getWaiter().getId())) {
						if (table.getLocked() == false) {
							this.showOpenOthersTableConfirmationDialog(view, table);
						} else {
							this.showDialog(view, table, "La mesa no está disponible");
						}
					} else {
						this.showDialog(view, table, "La mesa no está disponible");
					}
				}
			}

			private void showConfirmationDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage("¿Confirma la apertura de la mesa " + table.getId() + "?");
				alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						table.setState(TableStateHelper.OPEN.getState());
						table.setWaiter(DataHolder.getCurrentWaiter());
						TextView stateTextView = (TextView) view.findViewById(R.id.stateTextView);
						ImageView tableimageView = (ImageView) view.findViewById(R.id.imageView1);
						stateTextView.setText(table.getState().getDescription());
						stateTextView.setTextColor(Color.BLUE);
						tableimageView.setImageResource(R.drawable.blue_table);
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

			private void showDialog(final View view, final Table table, final String message) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage(message);
				alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}

			private void showOpenOthersTableConfirmationDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage("¿Confirma que toma la mesa " + table.getId() + "?");
				alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(TableListActivity.this, OrderActivity.class);
						TableListActivity.this.startActivity(intent);
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
