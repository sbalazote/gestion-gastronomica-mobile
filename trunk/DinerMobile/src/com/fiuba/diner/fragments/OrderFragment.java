package com.fiuba.diner.fragments;

import com.fiuba.diner.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OrderFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
 
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
	
	public void setTable(String table) {
		TextView tableTextView = (TextView)getView().findViewById(R.id.tableTextView);
		tableTextView.setText(table);
    }
}
