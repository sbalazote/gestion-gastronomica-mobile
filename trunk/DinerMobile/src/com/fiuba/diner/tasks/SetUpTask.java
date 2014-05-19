package com.fiuba.diner.tasks;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.AsyncTask;

import com.fiuba.diner.constant.Constants;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Waiter;

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

			// Me traigo los floors
			response = this.connectionHelper.get("floors");
			System.out.println(response);
			List<Floor> floors = this.mapper.readValue(response, new TypeReference<List<Floor>>() {
			});

			// Me traigo un waiter (por ahora hardcodeado)
			response = this.connectionHelper.get("waiters/" + Constants.HARDCODED_WAITER_ID);
			System.out.println(response);
			Waiter waiter = this.mapper.readValue(response, Waiter.class);

			DataHolder.setParameter(parameter);
			DataHolder.setFloors(floors);
			DataHolder.setCurrentWaiter(waiter);

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
