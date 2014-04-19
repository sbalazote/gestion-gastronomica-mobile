package com.fiuba.diner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.adapters.FloorListAdapter;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.util.TableLayoutUtils;

public class FloorListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.floor_list);
		this.populateList();
	}

	private void populateList() {
		ListView listview = (ListView) this.findViewById(R.id.floorListView);

		FloorListAdapter adapter = new FloorListAdapter(this, DataHolder.getFloors());
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Floor floor = (Floor) parent.getItemAtPosition(position);
				DataHolder.setCurrentFloor(floor);
				DataHolder.setCurrentTableLayout(TableLayoutUtils.convertLayout(floor));
				Intent intent = new Intent(FloorListActivity.this, FloorActivity.class);
				FloorListActivity.this.startActivity(intent);
			}

		});
	}

}
