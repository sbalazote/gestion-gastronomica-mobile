package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.Device;

public class UpdateDeviceTask extends AsyncTask<Device, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public UpdateDeviceTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Device... params) {
		try {
			String response = this.connectionHelper.post("devices", this.mapper.writeValueAsString(params[0]));
			System.out.println("respuestaaa" + response);
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