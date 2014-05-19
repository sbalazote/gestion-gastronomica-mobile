package com.fiuba.diner.model;

import java.io.Serializable;

public class Parameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String restaurantName;
	private Double dinnerServicePrice;
	private Boolean dinnerServiceActive;
	private String address;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRestaurantName() {
		return this.restaurantName;
	}

	public void setRestaurantName(String restaurant_name) {
		this.restaurantName = restaurant_name;
	}

	public Double getDinnerServicePrice() {
		return this.dinnerServicePrice;
	}

	public void setDinnerServicePrice(Double dinner_service_price) {
		this.dinnerServicePrice = dinner_service_price;
	}

	public Boolean getDinnerServiceActive() {
		return this.dinnerServiceActive;
	}

	public void setDinnerServiceActive(Boolean dinner_service_active) {
		this.dinnerServiceActive = dinner_service_active;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
