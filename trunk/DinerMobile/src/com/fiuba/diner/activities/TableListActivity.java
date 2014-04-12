package com.fiuba.diner.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.TableListAdapter;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.tasks.SetUpTask;

public class TableListActivity extends Activity implements Caller<Void> {

	public static final String EXTRA_TITLE = "com.fiuba.diner.activities.TITLE";
	public static final String TABLE_ID = "com.fiuba.diner.activities.TABLEID";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new SetUpTask(this).execute();
	}

	private void showView() {
		this.setContentView(R.layout.activity_table_list);
		this.setTitle(R.string.hardcodedWaiter);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.tableListView);

		TableListAdapter adapter = new TableListAdapter(this, DataHolder.getTables());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Table table = (Table) parent.getItemAtPosition(position);
				if (TableStateHelper.AVAILABLE.getState().getId().equals(table.getState().getId())) {
					this.openDialog(view, table);
				} else {
					Intent intent = new Intent(TableListActivity.this, OrderActivity.class);
					intent.putExtra(EXTRA_TITLE, "Mesa: " + String.valueOf(position + 1));
					intent.putExtra(TABLE_ID, String.valueOf(position + 1));
					TableListActivity.this.startActivity(intent);
				}
			}

			private void openDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage("¿Confirma la apertura de la mesa " + table.getId() + "?");
				alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						table.setState(TableStateHelper.TAKEN.getState());
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

		});
	}

	@Override
	public void afterCall(Void result) {
		this.showView();
	}

}
