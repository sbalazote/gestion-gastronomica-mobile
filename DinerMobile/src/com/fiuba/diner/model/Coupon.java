package com.fiuba.diner.model;

import java.io.Serializable;
import java.util.Date;

public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Double percentage;
	private Date expirationDate;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
