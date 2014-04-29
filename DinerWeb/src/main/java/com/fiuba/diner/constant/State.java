package com.fiuba.diner.constant;

public enum State {

	NUEVO(1), SOLICITADO(2), EN_PREPARACION(3), PREPARADO(4), ENTREGADO(5);

	private Integer id;

	private State(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public static State fromId(Integer id) {
		for (State state : State.values()) {
			if (state.getId().equals(id)) {
				return state;
			}
		}
		return null;
	}

}
