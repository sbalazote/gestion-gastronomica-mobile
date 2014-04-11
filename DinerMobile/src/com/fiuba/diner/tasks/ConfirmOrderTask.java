package com.fiuba.diner.tasks;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfirmOrderTask extends AsyncTask<Order, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	private final Caller<Void> caller;

	public ConfirmOrderTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Order... params) {
		try {
			this.connectionHelper.post("orders", this.gson.toJson(params[0]));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (this.caller != null) {
			this.caller.afterCall(result);
		}
	}

}
