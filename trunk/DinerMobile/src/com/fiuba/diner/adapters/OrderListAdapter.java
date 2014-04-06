package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;

import com.fiuba.diner.R;
import com.fiuba.diner.model.OrderListItem;

public class OrderListAdapter extends ArrayAdapter<OrderListItem> {

	private final Context context;
	private final List<OrderListItem> orderList;

	public OrderListAdapter(Context context, List<OrderListItem> orderList) {
		super(context, R.layout.order_list_item, orderList);
		this.context = context;
		this.orderList = orderList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.order_list_item, parent, false);

		EditText amountEditText = (EditText) rowView.findViewById(R.id.amountEditText);
		amountEditText.setText(this.orderList.get(position).getAmount());
		
		TextView productTextView = (TextView) rowView.findViewById(R.id.productTextView);
		productTextView.setText(this.orderList.get(position).getProduct().getDescription());
		
		TextView productPriceTextView = (TextView) rowView.findViewById(R.id.productPriceTextView);
		productPriceTextView.setText(this.orderList.get(position).getPrice().toString());

		return rowView;
	}
}
