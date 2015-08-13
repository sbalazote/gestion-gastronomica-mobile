package com.fiuba.diner.model;

import java.io.Serializable;

public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private User user;
	private String registrationId;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRegistrationId() {
		return this.registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
