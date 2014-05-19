package com.fiuba.diner.helper;

import java.util.List;

import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.TableLayout;
import com.fiuba.diner.model.Waiter;

public class DataHolder {

	private static List<Category> categories;
	private static List<Table> tables;
	private static List<Floor> floors;
	private static Order currentOrder;
	private static Table currentTable;
	private static Floor currentFloor;
	private static TableLayout[] currentTableLayout;
	private static Waiter currentWaiter;
	private static Parameter parameter;

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
	}

	public static List<Floor> getFloors() {
		return floors;
	}

	public static void setFloors(List<Floor> floors) {
		DataHolder.floors = floors;
	}

	public static Order getCurrentOrder() {
		return currentOrder;
	}

	public static void setCurrentOrder(Order currentOrder) {
		DataHolder.currentOrder = currentOrder;
	}

	public static Table getCurrentTable() {
		return currentTable;
	}

	public static void setCurrentTable(Table currentTable) {
		DataHolder.currentTable = currentTable;
	}

	public static Floor getCurrentFloor() {
		return currentFloor;
	}

	public static void setCurrentFloor(Floor currentFloor) {
		DataHolder.currentFloor = currentFloor;
	}

	public static TableLayout[] getCurrentTableLayout() {
		return currentTableLayout;
	}

	public static void setCurrentTableLayout(TableLayout[] currentTableLayout) {
		DataHolder.currentTableLayout = currentTableLayout;
	}

	public static Waiter getCurrentWaiter() {
		return currentWaiter;
	}

	public static void setCurrentWaiter(Waiter currentWaiter) {
		DataHolder.currentWaiter = currentWaiter;
	}

	public static Parameter getParameter() {
		return parameter;
	}

	public static void setParameter(Parameter parameter) {
		DataHolder.parameter = parameter;
	}

}