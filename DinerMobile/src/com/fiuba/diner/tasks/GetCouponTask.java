package com.fiuba.diner.tasks;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Coupon;

public class GetCouponTask extends AsyncTask<Integer, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public GetCouponTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(Integer... params) {
		String response;
		Coupon coupon = null;
		try {
			response = this.connectionHelper.get("coupon/" + params[0]);
			System.out.println(response);
			coupon = this.mapper.readValue(response, new TypeReference<Coupon>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataHolder.setCoupon(coupon);
		System.out.println("Cupon: " + coupon.getDescription());

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
