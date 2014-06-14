package com.fiuba.diner.model;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean valid;
	private String message;
	// private Integer userId;
	// private String userName;
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

	/* public Integer getUserId() { return this.userId; }
	 * 
	 * public void setUserId(Integer userId) { this.userId = userId; }
	 * 
	 * public String getUserName() { return this.userName; }
	 * 
	 * public void setUserName(String userName) { this.userName = userName; } */
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
