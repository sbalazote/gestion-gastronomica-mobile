package com.fiuba.diner.adapters.expandablelist;

import java.io.Serializable;

public class OrderProductDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer amount;
	private String comment;
	private int viewId;

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getViewId() {
		return this.viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

}
