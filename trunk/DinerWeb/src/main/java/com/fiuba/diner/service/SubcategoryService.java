package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Subcategory;

public interface SubcategoryService {

	Subcategory get(Integer id);

	void save(Subcategory subcategory);

	void delete(Integer subcategoryId);

	List<Subcategory> getAll();
}
