package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.model.Subcategory;

public class SubcategoryListAdapter extends ArrayAdapter<Subcategory> {

	private final Context context;
	private final List<Subcategory> subcategories;

	public SubcategoryListAdapter(Context context, List<Subcategory> subcategories) {
		super(context, R.layout.subcategory_list_item, subcategories);
		this.context = context;
		this.subcategories = subcategories;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.subcategory_list_item, parent, false);

		TextView subcategoryTextView = (TextView) rowView.findViewById(R.id.subcategoryTextView);

		subcategoryTextView.setText(this.subcategories.get(position).getDescription());

		return rowView;
	}

}
