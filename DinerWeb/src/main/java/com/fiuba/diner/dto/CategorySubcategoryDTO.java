package com.fiuba.diner.dto;

import java.io.Serializable;

public class CategorySubcategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer categoryId;
	private Integer subcategoryId;

	private String categoryDescription;
	private String subcategoryDescription;

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubcategoryId() {
		return this.subcategoryId;
	}

	public void setSubcategoryId(Integer subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getCategoryDescription() {
		return this.categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getSubcategoryDescription() {
		return this.subcategoryDescription;
	}

	public void setSubcategoryDescription(String subcategoryDescription) {
		this.subcategoryDescription = subcategoryDescription;
	}
}
