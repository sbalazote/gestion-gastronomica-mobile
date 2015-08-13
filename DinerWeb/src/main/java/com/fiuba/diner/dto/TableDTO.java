package com.fiuba.diner.dto;

import java.io.Serializable;

public class TableDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private boolean active;
	private boolean selected;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
