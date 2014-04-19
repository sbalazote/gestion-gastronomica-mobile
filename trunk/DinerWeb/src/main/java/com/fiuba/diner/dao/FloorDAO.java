package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Floor;

public interface FloorDAO {

	Floor get(Integer id);

	List<Floor> getAll();

	void save(Floor floor);

}
