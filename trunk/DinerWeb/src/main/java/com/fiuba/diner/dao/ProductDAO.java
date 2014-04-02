package com.fiuba.diner.dao;

import com.fiuba.diner.model.Product;

public interface ProductDAO {

	Product get(Integer id);

	void save(Product product);

	void delete(Integer productId);

}
