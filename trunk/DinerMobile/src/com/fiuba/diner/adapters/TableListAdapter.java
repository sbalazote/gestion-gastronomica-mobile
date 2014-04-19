package com.fiuba.diner.adapters;

import static com.fiuba.diner.constant.Constants.HARDCODED_WAITER_ID;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;

public class TableListAdapter extends ArrayAdapter<Table> {

	private final Context context;
	private final List<Table> tables;

	public TableListAdapter(Context context, List<Table> tables) {
		super(context, R.layout.table_list_item, tables);
		this.context = context;
		this.tables = tables;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.table_list_item, parent, false);

		TextView descriptionTextView = (TextView) rowView.findViewById(R.id.descriptionTextView);
		TextView stateTextView = (TextView) rowView.findViewById(R.id.stateTextView);

		Table table = this.tables.get(position);
		descriptionTextView.setText(String.valueOf(table.getId()));
		stateTextView.setText(table.getState().getDescription());

		if (table.getState().getId().equals(TableStateHelper.OPEN.getState().getId())) {
			if (table.getWaiter() == null || HARDCODED_WAITER_ID.equals(table.getWaiter().getId())) {
				stateTextView.setTextColor(Color.BLUE);
			} else {
				stateTextView.setTextColor(Color.RED);
				stateTextView.setText("No disponible");
			}
		}

		return rowView;
	}

}
