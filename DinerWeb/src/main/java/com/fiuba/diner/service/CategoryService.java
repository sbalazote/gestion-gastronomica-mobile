package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Category;

public interface CategoryService {

	Category get(Integer id);

	List<Category> getAll();

	void save(Category category);

	void delete(Integer categoryId);
}
