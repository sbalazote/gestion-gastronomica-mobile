package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Coupon;

public class GetCouponTask extends AsyncTask<String, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public GetCouponTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(String... params) {
		String response;
		Coupon coupon = null;
		try {
			response = this.connectionHelper.get("coupon/" + params[0]);
			coupon = this.mapper.readValue(response, new TypeReference<Coupon>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataHolder.setCoupon(coupon);
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
