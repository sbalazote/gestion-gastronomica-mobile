package com.fiuba.diner.dto;

import java.io.Serializable;
import java.util.List;

public class ProductRankingReportFiltersDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String from;
	private String to;
	private List<String> categorySubcategoryFilter;

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<String> getCategorySubcategoryFilter() {
		return this.categorySubcategoryFilter;
	}

	public void setCategorySubcategoryFilter(List<String> categorySubcategoryFilter) {
		this.categorySubcategoryFilter = categorySubcategoryFilter;
	}

}
