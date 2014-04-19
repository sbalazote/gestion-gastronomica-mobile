package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Waiter;

public interface WaiterDAO {

	Waiter get(Integer id);

	List<Waiter> getAll();

	void save(Waiter waiter);

}
