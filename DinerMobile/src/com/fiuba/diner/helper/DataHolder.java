package com.fiuba.diner.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Table;

public class DataHolder {

	private static List<Category> categories;
	private static List<Table> tables;
	private static Map<Integer,Integer> orderTableRelation;

	public static List<Category> getCategories() {
		return categories;
	}

	public static void setCategories(List<Category> categories) {
		DataHolder.categories = categories;
	}

	public static List<Table> getTables() {
		return tables;
	}

	public static void setTables(List<Table> tables) {
		DataHolder.tables = tables;
		orderTableRelation = new HashMap<Integer,Integer>();
		for(Table table : tables){
			orderTableRelation.put(table.getId(), 0);
		}
	}

	public static Map<Integer, Integer> getOrderTableRelation() {
		return orderTableRelation;
	}

	public static void setOrderTableRelation(
			Map<Integer, Integer> orderTableRelation) {
		DataHolder.orderTableRelation = orderTableRelation;
	}

}
