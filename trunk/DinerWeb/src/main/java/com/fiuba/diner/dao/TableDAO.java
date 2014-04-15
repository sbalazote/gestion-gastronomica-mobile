package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Table;

public interface TableDAO {

	Table get(Integer id);

	List<Table> getAll();

	void save(Table table);

}
