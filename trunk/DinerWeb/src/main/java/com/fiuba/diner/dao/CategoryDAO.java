package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Category;

public interface CategoryDAO {

	Category get(Integer id);

	List<Category> getAll();

	void save(Category category);

	void delete(Integer categoryId);

}
