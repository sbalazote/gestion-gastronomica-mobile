package com.fiuba.diner.helper;

import com.fiuba.diner.model.OrderDetailState;

public enum OrderDetailStateHelper {

	NEW(1, "Nuevo"), REQUESTED(2, "Solicitado"), IN_PROCESS(3, "En preparación"), PREPARED(4, "Preparado"), DELIVERED(5, "Entregado"), CANCELLED(9, "Anulado");

	private OrderDetailState state;

	private OrderDetailStateHelper(Integer id, String description) {
		this.state = new OrderDetailState();
		this.state.setId(id);
		this.state.setDescription(description);
	}

	public OrderDetailState getState() {
		return this.state;
	}

}
