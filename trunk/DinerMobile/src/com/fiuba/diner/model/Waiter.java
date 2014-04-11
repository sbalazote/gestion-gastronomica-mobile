package com.fiuba.diner.model;

import java.io.Serializable;

public class Waiter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String surname;

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

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}
