package com.fiuba.diner.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String password;
	private boolean active;
	private List<Role> roles;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
