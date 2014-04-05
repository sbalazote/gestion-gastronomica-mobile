package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Subcategory;

public interface SubcategoryDAO {

	Subcategory get(Integer id);

	void save(Subcategory subcategory);

	void delete(Integer subcategoryId);

	List<Subcategory> getAll();
}
