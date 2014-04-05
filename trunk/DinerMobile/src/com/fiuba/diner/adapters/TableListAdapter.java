package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
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

		descriptionTextView.setText(this.tables.get(position).getDescription());
		stateTextView.setText(this.tables.get(position).getState());

		return rowView;
	}

}
