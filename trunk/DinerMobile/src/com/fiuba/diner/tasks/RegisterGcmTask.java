package com.fiuba.diner.tasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;

import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.helper.SharedPreferencesHelper;
import com.fiuba.diner.model.Device;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterGcmTask extends AsyncTask<String, Integer, String> {

	private final Context context;

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

			// Updatear el Registration ID
			Device device = new Device();
			device.setId(com.fiuba.diner.constant.Constants.MAC);
			device.setRegistrationId(registrationId);
			device.setWaiter(null);
			new UpdateDeviceTask(null).execute(device);

			// Guardamos los datos del registro
			SharedPreferencesHelper.setRegistrationId(this.context, DataHolder.getCurrentWaiter().getId().toString(), registrationId);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
