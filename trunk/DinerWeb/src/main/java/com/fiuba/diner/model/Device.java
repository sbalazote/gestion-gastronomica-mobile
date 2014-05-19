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
	@JoinColumn(name = "waiter_id")
	private Waiter waiter;

	@Column(name = "registration_id", nullable = false)
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
