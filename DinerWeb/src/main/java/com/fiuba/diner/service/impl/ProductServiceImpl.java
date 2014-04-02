package com.fiuba.diner.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.ProductDAO;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Override
	public Product get(Integer id) {
		return this.productDAO.get(id);
	}

	@Override
	public void save(Product product) {
		this.productDAO.save(product);
		logger.info("Se han guardado los cambios exitosamente. Id de Producto: " + product.getId());
	}

	@Override
	public void delete(Integer productId) {
		this.productDAO.delete(productId);
	}

}
