package com.fiuba.diner.helper;

import java.util.List;

import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Table;

public class DataHolder {

	private static List<Category> categories;
	private static List<Table> tables;
	private static Order actualOrder;
	private static Table actualTable;

	public static List<Category> getCategories() {
		return categories;
	}

	public static void setCategories(List<Category> categories) {
		DataHolder.categories = categories;
	}

	public static List<Table> getTables() {
		return tables;
	}

	public static Order getActualOrder() {
		return actualOrder;
	}

	public static void setActualOrder(Order actualOrder) {
		DataHolder.actualOrder = actualOrder;
	}

	public static void setTables(List<Table> tables) {
		DataHolder.tables = tables;
	}

	public static Table getActualTable() {
		return actualTable;
	}

	public static void setActualTable(Table actualTable) {
		DataHolder.actualTable = actualTable;
	}

}
