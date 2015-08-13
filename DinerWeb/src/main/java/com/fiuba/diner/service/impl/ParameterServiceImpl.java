package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.ParameterDAO;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.service.ParameterService;

@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

	private static final Logger logger = Logger.getLogger(ParameterServiceImpl.class);

	@Autowired
	private ParameterDAO parameterDAO;

	@Override
	public Parameter get(Integer id) {
		return this.parameterDAO.get(id);
	}

	@Override
	public List<Parameter> getAll() {
		return this.parameterDAO.getAll();
	}

	@Override
	public void save(Parameter parameter) {
		this.parameterDAO.save(parameter);
		logger.info("Se han guardado los cambios exitosamente. Id de Parametro: " + parameter.getId());
	}

	@Override
	public void delete(Integer parameterId) {
		this.parameterDAO.delete(parameterId);
	}
}
