package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Waiter;

public interface WaiterService {

	Waiter get(Integer id);

	List<Waiter> getAll();

	void save(Waiter waiter);

}
