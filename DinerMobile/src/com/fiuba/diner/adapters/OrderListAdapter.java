package com.fiuba.diner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.OrderStateHelper;
import com.fiuba.diner.model.OrderDetail;

public class OrderListAdapter extends ArrayAdapter<OrderDetail> {

	private final Context context;
	private final List<OrderDetail> orderDetails;

	public OrderListAdapter(Context context, List<OrderDetail> orderDetails) {
		super(context, R.layout.order_product_detail_list_item, orderDetails);
		this.context = context;
		this.orderDetails = orderDetails;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.order_product_detail_list_item, parent, false);

		TextView productTextView = (TextView) rowView.findViewById(R.id.productTextView);
		productTextView.setText(this.orderDetails.get(position).getProduct().getDescription());

		TextView productPriceTextView = (TextView) rowView.findViewById(R.id.productPriceTextView);
		productPriceTextView.setText(this.orderDetails.get(position).getProduct().getPrice().toString());

		OrderDetail orderDetail = this.orderDetails.get(position);
		EditText productAmountEditText = (EditText) rowView.findViewById(R.id.productAmountEditText);
		EditText productCommentEditText = (EditText) rowView.findViewById(R.id.productCommentEditText);
		productAmountEditText.setText(String.valueOf(orderDetail.getAmount()));
		productCommentEditText.setText(orderDetail.getComment());
		TextView productState = (TextView) rowView.findViewById(R.id.stateTextView);
		if (this.orderDetails.get(position) != null) {
			if (this.orderDetails.get(position).getState() != null) {
				productState.setText(String.valueOf(this.orderDetails.get(position).getState().getDescription()));
				productAmountEditText.setEnabled(false);
				productCommentEditText.setEnabled(false);
			} else {
				productState.setText(OrderStateHelper.NEW.getState().getDescription());
				productAmountEditText.setEnabled(true);
				productCommentEditText.setEnabled(true);
			}
		}

		return rowView;
	}
}
