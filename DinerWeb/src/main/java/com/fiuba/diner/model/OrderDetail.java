package com.fiuba.diner.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.Table(name = "order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "comment")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	private OrderDetailState state;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "preparation_start_date")
	private Date preparationStartDate;

	@Column(name = "preparation_end_date")
	private Date preparationEndDate;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "billing_date")
	private Date billingDate;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public OrderDetailState getState() {
		return this.state;
	}

	public void setState(OrderDetailState state) {
		this.state = state;
	}

	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getPreparationStartDate() {
		return this.preparationStartDate;
	}

	public void setPreparationStartDate(Date preparationStartDate) {
		this.preparationStartDate = preparationStartDate;
	}

	public Date getPreparationEndDate() {
		return this.preparationEndDate;
	}

	public void setPreparationEndDate(Date preparationEndDate) {
		this.preparationEndDate = preparationEndDate;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getBillingDate() {
		return this.billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

}
