package com.fiuba.diner.model;

import java.io.Serializable;

public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Waiter waiter;
	private String registrationId;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Waiter getWaiter() {
		return this.waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public String getRegistrationId() {
		return this.registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

}
