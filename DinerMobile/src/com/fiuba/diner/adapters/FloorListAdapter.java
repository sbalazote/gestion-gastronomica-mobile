package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.model.Floor;

public class FloorListAdapter extends ArrayAdapter<Floor> {

	private final Context context;
	private final List<Floor> floors;

	public FloorListAdapter(Context context, List<Floor> floors) {
		super(context, R.layout.floor_list_item, floors);
		this.context = context;
		this.floors = floors;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.floor_list_item, parent, false);

		TextView descriptionTextView = (TextView) rowView.findViewById(R.id.floorNumberTextView);

		Floor floor = this.floors.get(position);
		descriptionTextView.setText(String.valueOf(floor.getFloor()));

		return rowView;
	}

}
