package com.fiuba.diner.util;

import java.util.Comparator;

import com.fiuba.diner.model.Table;

public class ComparatorByID implements Comparator<Table> {

	@Override
	public int compare(Table t1, Table t2) {
		return t1.getId().compareTo(t2.getId());
	}
}
