package com.fiuba.diner.model;

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
import javax.persistence.OneToMany;

@Entity
@javax.persistence.Table(name = "`order`")
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "customer_amount", nullable = false)
	private Integer customerAmount;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	private List<OrderDetail> details;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "order_table", joinColumns = @JoinColumn(name = "order_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "table_id", nullable = false))
	private List<Table> tables;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	private OrderState state;

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

	public List<Table> getTables() {
		return this.tables;
	}

}
