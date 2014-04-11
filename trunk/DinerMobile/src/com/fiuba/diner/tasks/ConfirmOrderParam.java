package com.fiuba.diner.tasks;

import com.fiuba.diner.model.Order;

public class ConfirmOrderParam {
	private Order order;
	private Integer tableId;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	
	
}
