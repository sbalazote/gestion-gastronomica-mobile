package com.fiuba.diner.constant;

import com.fiuba.diner.model.PaymentMedia;

public enum PaymentMediaState {
	TARJETA_DE_DEBITO(1, "Tarjeta de Debito"), TARJETA_DE_CREDITO(2, "Tarjeta de Credito"), EFECTIVO(3, "Efectivo");

	private PaymentMedia paymentMedia;

	private PaymentMediaState(Integer id, String description) {
		this.paymentMedia = new PaymentMedia();
		this.paymentMedia.setId(id);
		this.paymentMedia.setDescription(description);
	}

	public PaymentMedia getState() {
		return this.paymentMedia;
	}
}