package com.fiuba.diner.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.Table(name = "`table`")
public class Table {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	private TableState state;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "locked", nullable = false)
	private Boolean locked;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "table_union", joinColumns = @JoinColumn(name = "original_table_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "attached_table_id", nullable = false))
	private List<Table> attachedTables;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TableState getState() {
		return this.state;
	}

	public void setState(TableState state) {
		this.state = state;
	}

	public User getUser() {
		return this.user;
	}

	public void setWaiter(User user) {
		this.user = user;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public List<Table> getAttachedTables() {
		return this.attachedTables;
	}

	public void setAttachedTables(List<Table> attachedTables) {
		this.attachedTables = attachedTables;
	}

}
