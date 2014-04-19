package com.fiuba.diner.adapters;

import static com.fiuba.diner.constant.Constants.HARDCODED_WAITER_ID;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.TableLayout;

public class TableImageAdapter extends BaseAdapter {

	private final Context context;
	private final Set<Integer> tableIds;

	public TableImageAdapter(Context context) {
		this.context = context;
		this.tableIds = new HashSet<Integer>();
	}

	@Override
	public int getCount() {
		return DataHolder.getCurrentFloor().getLength() * DataHolder.getCurrentFloor().getWidth();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View tableView = null;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			tableView = inflater.inflate(R.layout.table, parent, false);

			ImageView imageView = (ImageView) tableView.findViewById(R.id.floorGridImageView);
			imageView.setAdjustViewBounds(true);
			imageView.setPadding(0, 0, 0, 0);

			TextView textView = (TextView) tableView.findViewById(R.id.floorGridTextView);

			TableLayout tableLayout = DataHolder.getCurrentTableLayout()[position];

			if (tableLayout != null) {
				Table table = tableLayout.getTable();
				if (TableStateHelper.AVAILABLE.getState().getId().equals(table.getState().getId())) {
					imageView.setImageResource(R.drawable.available_table);
				} else if (table.getWaiter() == null || HARDCODED_WAITER_ID.equals(table.getWaiter().getId())) {
					imageView.setImageResource(R.drawable.open_table);
				} else {
					imageView.setImageResource(R.drawable.not_available_table);
				}
				if (!this.tableIds.contains(table.getId())) {
					textView.setText(String.valueOf(table.getId()));
					this.tableIds.add(table.getId());
				}
			} else {
				imageView.setImageResource(R.drawable.blank_space);
			}

		} else {

			// Actualizo estados de las mesas (colores)
			tableView = convertView;
			ImageView imageView = (ImageView) tableView.findViewById(R.id.floorGridImageView);
			TableLayout tableLayout = DataHolder.getCurrentTableLayout()[position];
			if (tableLayout != null) {
				Table table = tableLayout.getTable();
				if (TableStateHelper.AVAILABLE.getState().getId().equals(table.getState().getId())) {
					imageView.setImageResource(R.drawable.available_table);
				} else if (table.getWaiter() == null || HARDCODED_WAITER_ID.equals(table.getWaiter().getId())) {
					imageView.setImageResource(R.drawable.open_table);
				} else {
					imageView.setImageResource(R.drawable.not_available_table);
				}
			}
		}

		return tableView;
	}
}