package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Parameter;

public class SetUpTask extends AsyncTask<String, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public SetUpTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(String... params) {
		try {
			// Se obtienen los parametros de configuracion
			String response = this.connectionHelper.get("parameters");
			System.out.println(response);
			Parameter parameter = this.mapper.readValue(response, Parameter.class);

			// Me traigo un waiter
			// response = this.connectionHelper.get("waiters/" + DataHolder.getCurrentWaiter().getId());
			// System.out.println(response);
			// Waiter waiter = this.mapper.readValue(response, Waiter.class);

			DataHolder.setParameter(parameter);
			// DataHolder.setCurrentWaiter(waiter);

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
