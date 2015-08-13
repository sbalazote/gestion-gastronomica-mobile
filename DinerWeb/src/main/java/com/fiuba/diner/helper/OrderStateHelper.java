package com.fiuba.diner.helper;

import com.fiuba.diner.model.OrderState;

public enum OrderStateHelper {
	ABIERTA(1, "Abierta"), CERRADA(2, "Cerrada"), FACTURADA(3, "Facturada");

	private OrderState orderState;

	private OrderStateHelper(Integer id, String description) {
		this.orderState = new OrderState();
		this.orderState.setId(id);
		this.orderState.setDescription(description);
	}

	public OrderState getState() {
		return this.orderState;
	}
}