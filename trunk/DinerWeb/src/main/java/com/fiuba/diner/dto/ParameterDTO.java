package com.fiuba.diner.dto;

import java.io.Serializable;

public class ParameterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String restaurant_name;
	private Double dinner_service_price;
	private Boolean dinner_service_active;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRestaurantName() {
		return this.restaurant_name;
	}

	public void setRestaurantName(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	public Double getDinnerServicePrice() {
		return this.dinner_service_price;
	}

	public void setDinnerServicePrice(Double dinner_service_price) {
		this.dinner_service_price = dinner_service_price;
	}

	public Boolean getDinnerServiceActive() {
		return this.dinner_service_active;
	}

	public void setDinnerServiceActive(Boolean dinner_service_active) {
		this.dinner_service_active = dinner_service_active;
	}
}
