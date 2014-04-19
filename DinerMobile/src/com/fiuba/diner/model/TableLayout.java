package com.fiuba.diner.model;

public class TableLayout {

	private Integer id;
	private Integer row;
	private Integer column;
	private Table table;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRow() {
		return this.row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return this.column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Table getTable() {
		return this.table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}
