package com.fiuba.diner.model;

import java.io.Serializable;

public class Table implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private TableState state;
	private Waiter waiter;
	private Boolean active;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TableState getState() {
		return this.state;
	}

	public void setState(TableState state) {
		this.state = state;
	}

	public Waiter getWaiter() {
		return this.waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Table)) {
			return false;
		}
		Table that = (Table) o;
		if (this.id == null || that.id == null) {
			return false;
		}
		return this.id.equals(that.id);
	}

}
