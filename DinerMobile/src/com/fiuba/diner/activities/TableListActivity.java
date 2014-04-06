package com.fiuba.diner.activities;

import static com.fiuba.diner.constant.Constants.AVAILABLE_STATE;
import static com.fiuba.diner.constant.Constants.OPEN_STATE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.TableListAdapter;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ProductProviderHelper;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.tasks.GetCategoriesTask;

public class TableListActivity extends Activity implements Caller<List<Category>> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GetCategoriesTask(this).execute();
	}

	private void showView() {
		this.setContentView(R.layout.activity_table_list);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.tableListView);

		List<Table> tables = new ArrayList<Table>();
		for (int i = 1; i < 21; ++i) {
			Table table = new Table();
			table.setId(i);
			table.setDescription(String.valueOf(i));
			table.setState(AVAILABLE_STATE);
			tables.add(table);
		}

		TableListAdapter adapter = new TableListAdapter(this, tables);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Table table = (Table) parent.getItemAtPosition(position);
				if (AVAILABLE_STATE.equals(table.getState())) {
					this.openDialog(view, table);
				} else {
					Intent intent = new Intent(TableListActivity.this, OrderActivity.class);
					TableListActivity.this.startActivity(intent);
				}
			}

			private void openDialog(final View view, final Table table) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TableListActivity.this);
				alertDialogBuilder.setMessage("¿Confirma la apertura de la mesa " + table.getDescription() + "?");
				alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						table.setState(OPEN_STATE);
						TextView stateTextView = (TextView) view.findViewById(R.id.stateTextView);
						stateTextView.setText(OPEN_STATE);
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
	public void afterCall(List<Category> result) {
		ProductProviderHelper.setCategories(result);
		this.showView();
	}

}
