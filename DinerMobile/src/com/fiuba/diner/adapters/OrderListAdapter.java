package com.fiuba.diner.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.activities.OrderActivity;
import com.fiuba.diner.helper.OrderDetailStateHelper;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.util.Formatter;

public class OrderListAdapter extends ArrayAdapter<OrderDetail> {

	private final Context context;
	private final List<OrderDetail> orderDetails;

	static class ViewHolder {

		Context context;
		TextView productPrice;
		OrderDetail orderDetail;
	}

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
		productPriceTextView.setText(Formatter.getPriceFormat(this.orderDetails.get(position).getProduct().getPrice()
				* this.orderDetails.get(position).getAmount()));

		OrderDetail orderDetail = this.orderDetails.get(position);

		Spinner productAmountSpinner = (Spinner) rowView.findViewById(R.id.productAmountSpinner);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.orderDetail = this.orderDetails.get(position);
		viewHolder.productPrice = productPriceTextView;
		viewHolder.context = this.context;
		productAmountSpinner.setTag(viewHolder);
		this.populateProductAmountSpinner(productAmountSpinner, orderDetail.getAmount());

		ImageButton confirmDeliveryButton = (ImageButton) rowView.findViewById(R.id.confirmDeliveryButton);
		ImageButton deleteProductButton = (ImageButton) rowView.findViewById(R.id.deleteProductButton);

		EditText productCommentEditText = (EditText) rowView.findViewById(R.id.productCommentEditText);
		productCommentEditText.setText(orderDetail.getComment());
		productCommentEditText.setTag(this.orderDetails.get(position));
		productCommentEditText.setFocusable(true);
		productCommentEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_UP) {
					EditText comment = (EditText) v;
					OrderDetail orderDetail = (OrderDetail) comment.getTag();
					orderDetail.setComment(comment.getText().toString());
					return true;
				}
				return false;
			}
		});

		TextView productState = (TextView) rowView.findViewById(R.id.orderStateTextView);
		if (this.orderDetails.get(position) != null) {
			Integer stateId = this.orderDetails.get(position).getState().getId();

			if (!stateId.equals(OrderDetailStateHelper.NEW.getState().getId())) {
				productState.setText(String.valueOf(this.orderDetails.get(position).getState().getDescription()));
				if (stateId.equals(OrderDetailStateHelper.PREPARED.getState().getId())) {
					deleteProductButton.setVisibility(ImageButton.GONE);
					confirmDeliveryButton.setVisibility(ImageButton.VISIBLE);
					productCommentEditText.setEnabled(false);
					productAmountSpinner.setEnabled(false);
					productState.setTextColor(Color.rgb(34, 139, 34));
				} else {
					if (stateId.equals(OrderDetailStateHelper.REQUESTED.getState().getId())) {
						deleteProductButton.setVisibility(ImageButton.VISIBLE);
						confirmDeliveryButton.setVisibility(ImageButton.GONE);
						productState.setTextColor(Color.BLUE);
					} else {
						deleteProductButton.setVisibility(ImageButton.GONE);
						confirmDeliveryButton.setVisibility(ImageButton.GONE);
						productCommentEditText.setEnabled(false);
						productAmountSpinner.setEnabled(false);
						if (stateId.equals(OrderDetailStateHelper.DELIVERED.getState().getId())) {
							productState.setTextColor(Color.RED);
						} else {
							productState.setTextColor(Color.rgb(255, 140, 0));
						}
					}
				}
			} else {
				productState.setText(OrderDetailStateHelper.NEW.getState().getDescription());
				deleteProductButton.setVisibility(ImageButton.VISIBLE);
				confirmDeliveryButton.setVisibility(ImageButton.GONE);
				productState.setTextColor(Color.BLACK);
			}
		}

		return rowView;
	}

	public void populateProductAmountSpinner(Spinner productAmountSpinner, int productAmount) {
		List<String> list = new ArrayList<String>();

		// Se cargan los valores del combo
		for (int i = com.fiuba.diner.constant.Constants.MIN_PRODUCTS; i <= com.fiuba.diner.constant.Constants.MAX_PRODUCTS; i++) {
			list.add(String.valueOf(i));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		productAmountSpinner.setAdapter(dataAdapter);

		// Se setea el valor que trae la orden
		int spinnerPosition = dataAdapter.getPosition(String.valueOf(productAmount));
		productAmountSpinner.setSelection(spinnerPosition);

		// Se define el evento cuando cambia el valor del combo
		productAmountSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				Spinner spinnerAmount = (Spinner) parentView;
				ViewHolder viewHolder = (ViewHolder) spinnerAmount.getTag();
				viewHolder.orderDetail.setAmount(Integer.parseInt(parentView.getItemAtPosition(position).toString()));
				viewHolder.productPrice.setText(Formatter.getPriceFormat(viewHolder.orderDetail.getProduct().getPrice() * viewHolder.orderDetail.getAmount()));
				OrderActivity orderActivity = (OrderActivity) viewHolder.context;
				orderActivity.updateTotal();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}
		});
	}
}
