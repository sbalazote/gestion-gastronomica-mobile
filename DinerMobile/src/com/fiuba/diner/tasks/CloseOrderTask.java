package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Order;

public class CloseOrderTask extends AsyncTask<Integer, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public CloseOrderTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Integer... params) {
		String response;
		Order order = null;
		try {
			response = this.connectionHelper.post("orders/" + params[0] + "/close", null);
			System.out.println(response);
			order = this.mapper.readValue(response, new TypeReference<Order>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataHolder.setCurrentOrder(order);

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
