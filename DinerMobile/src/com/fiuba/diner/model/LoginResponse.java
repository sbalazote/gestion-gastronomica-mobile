package com.fiuba.diner.model;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean valid;
	private String message;
	private User user;

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
