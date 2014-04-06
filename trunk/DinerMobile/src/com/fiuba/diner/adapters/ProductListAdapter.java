package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.model.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {

	private final Context context;
	private final List<Product> products;

	public ProductListAdapter(Context context, List<Product> products) {
		super(context, R.layout.product_list_item, products);
		this.context = context;
		this.products = products;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.product_list_item, parent, false);

		TextView productTextView = (TextView) rowView.findViewById(R.id.productTextView);
		TextView productPriceTextView = (TextView) rowView.findViewById(R.id.productPriceTextView);

		Product product = this.products.get(position);
		productTextView.setText(product.getDescription());
		productPriceTextView.setText(String.valueOf(product.getPrice()));

		return rowView;
	}

}
