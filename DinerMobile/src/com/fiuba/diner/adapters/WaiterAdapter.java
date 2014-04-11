package com.fiuba.diner.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fiuba.diner.R;
import com.fiuba.diner.model.Waiter;

public class WaiterAdapter extends ArrayAdapter<Waiter> {

	Activity context;
	Waiter waiter;

	WaiterAdapter(Fragment context, Waiter waiter) {
		super(context.getActivity(), R.layout.fragment_waiter);
		this.context = context.getActivity();
		this.waiter = waiter;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = this.context.getLayoutInflater();
		View item = inflater.inflate(R.layout.fragment_waiter, null);

		TextView nameWaiterTextView = (TextView) item.findViewById(R.id.waiterNameTextView);
		nameWaiterTextView.setText(this.waiter.getSurname());

		return (item);
	}
}
