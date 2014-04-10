package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.CategoryDAO;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public Category get(Integer id) {
		return this.categoryDAO.get(id);
	}

	@Override
	public List<Category> getAll() {
		return this.categoryDAO.getAll();
	}

	@Override
	public void save(Category category) {
		this.categoryDAO.save(category);
		logger.info("Se han guardado los cambios exitosamente. Id de Categoría: " + category.getId());
	}

	@Override
	public void delete(Integer categoryId) {
		this.categoryDAO.delete(categoryId);
	}

}
