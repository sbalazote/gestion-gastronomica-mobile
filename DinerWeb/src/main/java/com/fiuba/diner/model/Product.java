package com.fiuba.diner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "celiac_allowed", nullable = false)
	private Boolean celiacAllowed;

	@Column(name = "kitchen", nullable = false)
	private Boolean kitchen;

	@Column(name = "active", nullable = false)
	private Boolean active;

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

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getCeliacAllowed() {
		return this.celiacAllowed;
	}

	public void setCeliacAllowed(Boolean celiacAllowed) {
		this.celiacAllowed = celiacAllowed;
	}

	public Boolean getKitchen() {
		return this.kitchen;
	}

	public void setKitchen(Boolean kitchen) {
		this.kitchen = kitchen;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
