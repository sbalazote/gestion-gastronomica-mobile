package com.fiuba.diner.adapters;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.expandablelist.OrderProductDetail;
import com.fiuba.diner.model.Product;

public class OrderProductListAdapter extends BaseExpandableListAdapter {

	public Activity activity;
	public LayoutInflater inflater;
	private final List<Product> groups;
	private final Map<Integer, View> childViews;

	public OrderProductListAdapter(Activity activity, List<Product> groups) {
		this.activity = activity;
		this.inflater = activity.getLayoutInflater();
		this.groups = groups;
		this.childViews = new HashMap<Integer, View>();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.groups.get(groupPosition).getDetails().get(childPosition);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View childView, ViewGroup parent) {
		if (childView == null) {
			childView = this.inflater.inflate(R.layout.order_product_detail_list_item, null);
		}

		OrderProductDetail detail = (OrderProductDetail) this.getChild(groupPosition, 0);
		EditText productAmountEditText = (EditText) childView.findViewById(R.id.productAmountEditText);
		EditText productCommentEditText = (EditText) childView.findViewById(R.id.productCommentEditText);
		productAmountEditText.setText(String.valueOf(detail.getAmount()));
		productCommentEditText.setText(detail.getComment());
		detail.setViewId(childView.findViewById(R.id.deleteProductButton).getId());

		this.childViews.put(groupPosition, childView);
		return childView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.groups.get(groupPosition).getDetails().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.groups.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.order_product_list_item, null);
		}
		TextView productTextView = (TextView) convertView.findViewById(R.id.productTextView);
		TextView productPriceTextView = (TextView) convertView.findViewById(R.id.productPriceTextView);

		Product group = (Product) this.getGroup(groupPosition);
		productTextView.setText(group.getDescription());

		DecimalFormat formatter = new DecimalFormat("#.00");
		productPriceTextView.setText("$" + formatter.format(group.getPrice()));
		productPriceTextView.setTextColor(Color.rgb(48, 128, 20));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		OrderProductDetail detail = (OrderProductDetail) this.getChild(groupPosition, 0);
		View childView = this.childViews.get(groupPosition);

		EditText productAmountEditText = (EditText) childView.findViewById(R.id.productAmountEditText);
		EditText productCommentEditText = (EditText) childView.findViewById(R.id.productCommentEditText);
		detail.setAmount(Integer.valueOf(productAmountEditText.getText().toString()));
		detail.setComment(productCommentEditText.getText().toString());
	}

}
