package com.fiuba.diner.dto;

import java.io.Serializable;

public class ProductRankingReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productDescription;
	private Integer numberOfTimesServed;
	private String category;
	private String subcategory;

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getNumberOfTimesServed() {
		return this.numberOfTimesServed;
	}

	public void setNumberOfTimesServed(Integer numberOfTimesServed) {
		this.numberOfTimesServed = numberOfTimesServed;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return this.subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

}
