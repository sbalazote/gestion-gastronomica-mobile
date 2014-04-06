package com.fiuba.diner.tasks;

import java.lang.reflect.Type;
import java.util.List;

import android.os.AsyncTask;

import com.fiuba.diner.constant.Constants;
import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetCategoriesTask extends AsyncTask<String, Void, List<Category>> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final Gson gson = new Gson();
	private final Caller<List<Category>> caller;

	public GetCategoriesTask(Caller<List<Category>> caller) {
		this.caller = caller;
	}

	@Override
	protected List<Category> doInBackground(String... params) {
		List<Category> categories = null;
		try {
			String response = this.connectionHelper.get(Constants.WEBAPP_HOST + "/diner/categories");
			System.out.println(response);
			Type type = (new TypeToken<List<Category>>() {
			}).getType();
			categories = this.gson.fromJson(response, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	protected void onPostExecute(List<Category> result) {
		super.onPostExecute(result);
		this.caller.afterCall(result);
	}

}
