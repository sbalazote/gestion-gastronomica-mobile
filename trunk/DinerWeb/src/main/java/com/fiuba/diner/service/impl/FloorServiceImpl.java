package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.FloorDAO;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.service.FloorService;

@Service
@Transactional
public class FloorServiceImpl implements FloorService {

	private static final Logger logger = Logger.getLogger(FloorServiceImpl.class);

	@Autowired
	private FloorDAO floorDAO;

	@Override
	public Floor get(Integer id) {
		return this.floorDAO.get(id);
	}

	@Override
	public List<Floor> getAll() {
		return this.floorDAO.getAll();
	}

	@Override
	public void save(Floor floor) {
		this.floorDAO.save(floor);
		logger.info("Se han guardado los cambios exitosamente. Id de Piso: " + floor.getId());
	}

}
