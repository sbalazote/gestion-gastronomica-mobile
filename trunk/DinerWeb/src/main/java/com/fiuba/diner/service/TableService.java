package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Table;

public interface TableService {

	Table get(Integer id);

	List<Table> getAll();

	void save(Table table);

}
