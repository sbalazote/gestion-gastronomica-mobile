package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Parameter;

public interface ParameterDAO {

	Parameter get(Integer id);

	List<Parameter> getAll();

	void save(Parameter parameter);

	void delete(Integer parameterId);

}
