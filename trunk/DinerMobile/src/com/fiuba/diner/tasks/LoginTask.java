package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.User;

public class LoginTask extends AsyncTask<User, Void, Boolean> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Boolean> caller;

	public LoginTask(Caller<Boolean> caller) {
		this.caller = caller;
	}

	@Override
	protected Boolean doInBackground(User... params) {
		String result = null;
		try {
			result = this.connectionHelper.post("waiterLogin", this.mapper.writeValueAsString(params[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.parseBoolean(result);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (this.caller != null) {
			this.caller.afterCall(result);
		}
	}

}
