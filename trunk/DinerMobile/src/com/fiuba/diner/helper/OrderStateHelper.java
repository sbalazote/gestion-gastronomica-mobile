package com.fiuba.diner.helper;

import com.fiuba.diner.model.OrderState;

public enum OrderStateHelper {

	OPEN(1, "Abierta"), CLOSED(2, "Cerrada"), BILL(3, "Facturada");

	private OrderState state;

	private OrderStateHelper(Integer id, String description) {
		this.state = new OrderState();
		this.state.setId(id);
		this.state.setDescription(description);
	}

	public OrderState getState() {
		return this.state;
	}

}