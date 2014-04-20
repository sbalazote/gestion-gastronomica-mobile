package com.fiuba.diner.dto;

import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Table;

public class OrderDetailDTO {

	private OrderDetail detail;
	private Table table;

	public OrderDetail getDetail() {
		return this.detail;
	}

	public void setDetail(OrderDetail detail) {
		this.detail = detail;
	}

	public Table getTable() {
		return this.table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}
