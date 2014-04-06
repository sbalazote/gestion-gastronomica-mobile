package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.model.Category;

public class CategoryListAdapter extends ArrayAdapter<Category> {

	private final Context context;
	private final List<Category> categories;

	public CategoryListAdapter(Context context, List<Category> categories) {
		super(context, R.layout.category_list_item, categories);
		this.context = context;
		this.categories = categories;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.category_list_item, parent, false);

		TextView categoryTextView = (TextView) rowView.findViewById(R.id.categoryTextView);

		categoryTextView.setText(this.categories.get(position).getDescription());

		return rowView;
	}

}
