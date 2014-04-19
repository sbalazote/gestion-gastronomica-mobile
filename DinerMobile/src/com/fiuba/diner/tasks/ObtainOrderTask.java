package com.fiuba.diner.tasks;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObtainOrderTask extends AsyncTask<Integer, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	private final Caller<Void> caller;

	public ObtainOrderTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Integer... params) {
		String response;
		try {
			response = this.connectionHelper.get("order?id=" + params[0]);
			System.out.println(response);
			Order order = this.gson.fromJson(response, Order.class);
			if (order != null) {
				System.out.println("Se recupero el numeor de orden " + order.getId());
			}
			DataHolder.setCurrentOrder(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
