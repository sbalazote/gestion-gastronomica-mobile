package com.fiuba.diner.tasks;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.AsyncTask;

import com.fiuba.diner.helper.Caller;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Category;

public class GetCategoriesTask extends AsyncTask<String, Void, Void> {

	private final ConnectionHelper connectionHelper = new ConnectionHelper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final Caller<Void> caller;

	public GetCategoriesTask(Caller<Void> caller) {
		this.caller = caller;
	}

	@Override
	protected Void doInBackground(String... params) {
		try {
			// Me traigo las categories
			String response = this.connectionHelper.get("categories");
			System.out.println(response);
			List<Category> categories = this.mapper.readValue(response, new TypeReference<List<Category>>() {
			});
			DataHolder.setCategories(categories);

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
