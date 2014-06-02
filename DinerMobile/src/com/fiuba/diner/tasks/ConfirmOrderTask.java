package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.Order;

public class ConfirmOrderTask extends AsyncTask<Order, Void, Integer> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Integer> caller;

	public ConfirmOrderTask(Caller<Integer> caller) {
		this.caller = caller;
	}

	@Override
	protected Integer doInBackground(Order... params) {
		try {
			return Integer.valueOf(this.connectionHelper.post("orders", this.mapper.writeValueAsString(params[0])));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (this.caller != null) {
			this.caller.afterCall(result);
		}
	}

}
