package com.fiuba.diner.model;

import java.io.Serializable;
import java.util.Date;

public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer amount;
	private String comment;
	private Product product;
	private OrderDetailState state;
	private Date requestDate;
	private Date preparationStartDate;
	private Date preparationEndDate;
	private Date deliveryDate;
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
