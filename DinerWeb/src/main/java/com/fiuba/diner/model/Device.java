package com.fiuba.diner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "registration_id", nullable = false)
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