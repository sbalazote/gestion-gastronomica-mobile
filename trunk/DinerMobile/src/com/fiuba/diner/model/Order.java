package com.fiuba.diner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer customerAmount;
	private List<OrderDetail> details;
	private List<Table> tables;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerAmount() {
		return this.customerAmount;
	}

	public void setCustomerAmount(Integer customerAmount) {
		this.customerAmount = customerAmount;
	}

	public List<OrderDetail> getDetails() {
		return this.details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public void addDetail(OrderDetail detail) {
		if (this.details == null) {
			this.details = new ArrayList<OrderDetail>();
		}
		this.details.add(detail);
	}

	public List<Table> getTable() {
		return this.tables;
	}

	public void setTable(List<Table> table) {
		this.tables = table;
	}

}
