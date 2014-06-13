package com.fiuba.diner.dto;

import java.io.Serializable;

public class CouponDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Double percentage;
	private String startingDate;
	private String expirationDate;

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

	public String getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStartingDate() {
		return this.startingDate;
	}

	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}

}
