package com.fiuba.diner.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.Table(name = "`table`")
public class Table {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	private TableState state;

	@ManyToOne
	@JoinColumn(name = "waiter_id")
	private Waiter waiter;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "order_table", joinColumns = @JoinColumn(name = "table_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "order_id", nullable = false))
	private final List<Order> orders = new ArrayList<Order>();

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

	public List<Order> getOrders() {
		return this.orders;
	}

}
