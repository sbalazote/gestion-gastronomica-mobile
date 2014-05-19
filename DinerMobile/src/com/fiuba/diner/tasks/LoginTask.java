package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.AsyncTask;

import com.fiuba.diner.activities.Login;
import com.fiuba.diner.helper.ConnectionHelper;
// import com.fiuba.diner.model.User;
import com.fiuba.diner.model.LoginRequest;
import com.fiuba.diner.model.LoginResponse;

public class LoginTask extends AsyncTask<LoginRequest, Void, Boolean> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Login login;

	public LoginTask(Login login) {
		this.login = login;
	}

	@Override
	protected Boolean doInBackground(LoginRequest... params) {
		String result = null;
		LoginResponse loginResponse = null;

		try {
			result = this.connectionHelper.post("waiterLogin", this.mapper.writeValueAsString(params[0]));
			loginResponse = this.mapper.readValue(result, new TypeReference<LoginResponse>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.login.setLoginResponse(loginResponse);

		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (this.login != null) {
			this.login.afterCall(result);
		}
	}

}
