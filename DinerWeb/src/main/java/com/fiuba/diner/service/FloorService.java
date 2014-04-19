package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Floor;

public interface FloorService {

	Floor get(Integer id);

	List<Floor> getAll();

	void save(Floor floor);

}
