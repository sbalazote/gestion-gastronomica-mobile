package com.fiuba.diner.tasks;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.util.ComparatorByID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class SetUpTask extends AsyncTask<String, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();;
	private final Caller<Void> caller;

	public SetUpTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(String... params) {
		try {
			// Me traigo las categories
			String response = this.connectionHelper.get("categories");
			System.out.println(response);
			Type type = (new TypeToken<List<Category>>() {
			}).getType();
			List<Category> categories = this.gson.fromJson(response, type);

			// Me traigo las mesas
			response = this.connectionHelper.get("tables");
			System.out.println(response);
			type = (new TypeToken<List<Table>>() {
			}).getType();
			List<Table> tables = this.gson.fromJson(response, type);
			Collections.sort(tables, new ComparatorByID());

			DataHolder.setCategories(categories);
			DataHolder.setTables(tables);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.caller.afterCall(result);
	}

}
