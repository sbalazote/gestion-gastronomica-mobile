package com.fiuba.diner.helper;

import com.fiuba.diner.model.TableState;

public enum TableStateHelper {

	AVAILABLE(1, "Disponible"), OPEN(2, "Abierta");

	private TableState state;

	private TableStateHelper(Integer id, String description) {
		this.state = new TableState();
		this.state.setId(id);
		this.state.setDescription(description);
	}

	public TableState getState() {
		return this.state;
	}

}
