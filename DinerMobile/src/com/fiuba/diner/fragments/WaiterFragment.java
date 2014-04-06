package com.fiuba.diner.fragments;

//import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
import android.widget.TextView;

import com.fiuba.diner.R;
//import com.fiuba.diner.adapters.WaiterAdapter;
//import com.fiuba.diner.model.Waiter;

public class WaiterFragment extends Fragment {
	
	//private TextView nameTextView;
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
 
        return inflater.inflate(R.layout.fragment_waiter, container, false);
    }
	
	public void setWaiterName(String waiterName) {
		TextView waiterNameTextView = (TextView)getView().findViewById(R.id.waiterNameTextView);
        waiterNameTextView.setText(waiterName);
    }
 
 //   @Override
 //   public void onActivityCreated(Bundle state) {
 //       super.onActivityCreated(state);
 //
 //       nameTextView = (TextView)getView().findViewById(R.id.nameWaiterTextView);
 //
 //       nameTextView.setAdapter(new WaiterAdapter(this));
 //   }
	
}
