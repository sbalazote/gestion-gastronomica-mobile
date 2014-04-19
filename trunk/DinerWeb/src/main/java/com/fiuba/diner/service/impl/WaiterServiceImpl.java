package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.WaiterDAO;
import com.fiuba.diner.model.Waiter;
import com.fiuba.diner.service.WaiterService;

@Service
@Transactional
public class WaiterServiceImpl implements WaiterService {

	private static final Logger logger = Logger.getLogger(WaiterServiceImpl.class);

	@Autowired
	private WaiterDAO waiterDAO;

	@Override
	public Waiter get(Integer id) {
		return this.waiterDAO.get(id);
	}

	@Override
	public List<Waiter> getAll() {
		return this.waiterDAO.getAll();
	}

	@Override
	public void save(Waiter waiter) {
		this.waiterDAO.save(waiter);
		logger.info("Se han guardado los cambios exitosamente. Id de Mozo: " + waiter.getId());
	}

}
