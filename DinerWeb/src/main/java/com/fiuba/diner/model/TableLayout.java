package com.fiuba.diner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.Table(name = "`table_layout`")
public class TableLayout {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "row", nullable = false)
	private Integer row;

	@Column(name = "column", nullable = false)
	private Integer column;

	@ManyToOne
	@JoinColumn(name = "table_id", nullable = false)
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
