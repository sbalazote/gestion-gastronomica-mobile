package com.fiuba.diner.helper;

import java.util.List;

import com.fiuba.diner.model.Category;

public class ProductProviderHelper {

	private static List<Category> categories;

	public static List<Category> getCategories() {
		return categories;
	}

	public static void setCategories(List<Category> categories) {
		ProductProviderHelper.categories = categories;
	}

}
