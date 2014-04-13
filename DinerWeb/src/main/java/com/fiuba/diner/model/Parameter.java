package com.fiuba.diner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parameter")
public class Parameter {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "restaurant_name", nullable = false)
	private String restaurant_name;

	@Column(name = "dinner_service_price", nullable = false)
	private Double dinner_service_price;

	@Column(name = "dinner_service_active", nullable = false)
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
