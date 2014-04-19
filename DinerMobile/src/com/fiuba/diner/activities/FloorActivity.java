package com.fiuba.diner.activities;

import static com.fiuba.diner.constant.Constants.HARDCODED_WAITER_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.TableImageAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.TableLayout;

public class FloorActivity extends Activity {

	private BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.floor);

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
				alertDialogBuilder.setMessage("¿Confirma la apertura de la mesa " + table.getId() + "?");
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
				alertDialogBuilder.setMessage("La mesa no está disponible");
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
}
