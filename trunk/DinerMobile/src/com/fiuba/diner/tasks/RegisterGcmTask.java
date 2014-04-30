package com.fiuba.diner.tasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;

import com.fiuba.diner.helper.SharedPreferencesHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterGcmTask extends AsyncTask<String, Integer, String> {

	private final Context context;

	// private final ConnectionHelper connectionHelper = new ConnectionHelper();

	public RegisterGcmTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		String registrationId;
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.context);

		try {

			// Se hace el registro en los servidores de GCM
			registrationId = gcm.register(com.fiuba.diner.constant.Constants.PROJECT_ID);

			// Se hace el registro en nuestro servidor
			// String response = this.connectionHelper.get("register/user=" + params[0] + "?regId=" + registrationId);
			String response = "OK";

			// Guardamos los datos del registro
			if (response == "OK") {
				SharedPreferencesHelper.setRegistrationId(this.context, params[0], registrationId);
			} else {

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
