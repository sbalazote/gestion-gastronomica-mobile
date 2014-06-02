package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.DataHolder;
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
		ImageView tableimageView = (ImageView) rowView.findViewById(R.id.imageView1);

		Table table = this.tables.get(position);
		descriptionTextView.setText(String.valueOf(table.getId()));
		stateTextView.setText(table.getState().getDescription());

		if (table.getState().getId().equals(TableStateHelper.OPEN.getState().getId())) {
			if (table.getWaiter() == null || DataHolder.getCurrentWaiter().getId().equals(table.getWaiter().getId())) {
				stateTextView.setTextColor(Color.BLUE);
				tableimageView.setImageResource(R.drawable.blue_table);
			} else {
				stateTextView.setTextColor(Color.RED);
				stateTextView.setText("No disponible");
				tableimageView.setImageResource(R.drawable.red_table);
			}
		} else if (table.getState().getId().equals(TableStateHelper.CLOSED.getState().getId())) {
			stateTextView.setTextColor(Color.rgb(255, 140, 0));
			stateTextView.setText("Cerrada");
			tableimageView.setImageResource(R.drawable.orange_table);
		}

		return rowView;
	}

}
