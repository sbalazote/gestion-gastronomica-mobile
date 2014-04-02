package com.fiuba.diner.dao;

import com.fiuba.diner.model.Subcategory;

public interface SubcategoryDAO {

	Subcategory get(Integer id);

	void save(Subcategory subcategory);

	void delete(Integer subcategoryId);

}
