package com.fiuba.diner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail_state")
public class OrderDetailState {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "description", nullable = false)
	private String description;

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

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof OrderDetailState)) {
			return false;
		}
		OrderDetailState state = (OrderDetailState) obj;
		if (this.id != null && state.getId() != null && this.id.equals(state.getId())) {
			return true;
		}
		return false;
	}

}
