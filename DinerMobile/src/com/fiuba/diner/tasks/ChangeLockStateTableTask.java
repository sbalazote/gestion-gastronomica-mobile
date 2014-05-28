package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.Table;

public class ChangeLockStateTableTask extends AsyncTask<Table, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public ChangeLockStateTableTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Table... params) {
		try {
			this.connectionHelper.post("changeLockStateTable", this.mapper.writeValueAsString(params[0]));
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
