package com.fiuba.diner.model;

import java.util.List;

public class Floor {

	private Integer id;
	private Integer floor;
	private Integer length;
	private Integer width;
	private Boolean active;
	private List<TableLayout> tableLayout;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFloor() {
		return this.floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<TableLayout> getTableLayout() {
		return this.tableLayout;
	}

	public void setTableLayout(List<TableLayout> tableLayout) {
		this.tableLayout = tableLayout;
	}

}
