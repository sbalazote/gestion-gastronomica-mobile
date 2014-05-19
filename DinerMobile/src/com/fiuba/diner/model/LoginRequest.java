package com.fiuba.diner.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String userPassword;
	private String mobileId;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMobileId() {
		return this.mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

}
