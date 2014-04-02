package com.fiuba.diner.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.SubcategoryDAO;
import com.fiuba.diner.model.Subcategory;
import com.fiuba.diner.service.SubcategoryService;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

	private static final Logger logger = Logger.getLogger(SubcategoryServiceImpl.class);

	@Autowired
	private SubcategoryDAO subcategoryDAO;

	@Override
	public Subcategory get(Integer id) {
		return this.subcategoryDAO.get(id);
	}

	@Override
	public void save(Subcategory subcategory) {
		this.subcategoryDAO.save(subcategory);
		logger.info("Se han guardado los cambios exitosamente. Id de Subcategoryo: " + subcategory.getId());
	}

	@Override
	public void delete(Integer subcategoryId) {
		this.subcategoryDAO.delete(subcategoryId);
	}

}
