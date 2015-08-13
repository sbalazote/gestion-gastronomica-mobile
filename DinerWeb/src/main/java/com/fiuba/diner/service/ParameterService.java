package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Parameter;

public interface ParameterService {

	Parameter get(Integer id);

	List<Parameter> getAll();

	void save(Parameter parameter);

	void delete(Integer parameterId);

}
