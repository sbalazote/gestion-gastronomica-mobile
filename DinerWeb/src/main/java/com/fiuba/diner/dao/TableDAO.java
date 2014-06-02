package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Table;

public interface TableDAO {

	Table get(Integer id);

	List<Table> getAll();

	List<Table> getAllTables();

	void save(Table table);

	List<Table> getAvailableTables();

	void delete(Integer tableId);
}
