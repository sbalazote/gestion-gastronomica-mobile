package com.fiuba.diner.dto;

import java.io.Serializable;
import java.util.List;

public class TableAttachmentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tableId;
	private List<Integer> attachedTables;

	public Integer getTableId() {
		return this.tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public List<Integer> getAttachedTables() {
		return this.attachedTables;
	}

	public void setAttachedTables(List<Integer> attachedTables) {
		this.attachedTables = attachedTables;
	}

}
