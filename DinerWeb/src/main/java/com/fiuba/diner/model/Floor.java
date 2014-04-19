package com.fiuba.diner.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@javax.persistence.Table(name = "`floor`")
public class Floor {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "floor", nullable = false)
	private Integer floor;

	@Column(name = "length", nullable = false)
	private Integer length;

	@Column(name = "width", nullable = false)
	private Integer width;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "floor_id", referencedColumnName = "id", nullable = false)
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
