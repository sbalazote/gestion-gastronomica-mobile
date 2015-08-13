package com.fiuba.diner.tasks;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;

public class CloseOrderTask extends AsyncTask<Integer, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final Caller<Void> caller;

	public CloseOrderTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Integer... params) {
		try {
			this.connectionHelper.post("orders/" + params[0] + "/close", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataHolder.setCurrentOrder(null);
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
