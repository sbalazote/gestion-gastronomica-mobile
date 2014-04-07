package com.fiuba.diner.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.ProductService;
import com.fiuba.diner.service.SubcategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class ProductServiceTest {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubcategoryService subcategoryService;

	@Autowired
	private ProductService productService;

	@Test
	public void save() {
		Category category = new Category();

		category.setDescription("Test Category");
		category.setActive(true);
		this.categoryService.save(category);
		Category savedCategory = this.categoryService.get(category.getId());

		Subcategory subcategory = new Subcategory();
		subcategory.setActive(true);
		subcategory.setDescription("Test Subcategory description");

		List<Subcategory> subcategories = new ArrayList<Subcategory>();
		subcategories.add(subcategory);

		savedCategory.setSubcategories(subcategories);
		this.categoryService.save(savedCategory);

		Product product = new Product();
		product.setActive(true);
		product.setDescription("Test Product description");
		product.setPrice(Double.valueOf("4.0"));

		List<Product> products = new ArrayList<Product>();

		products.add(product);

		subcategory.setProducts(products);

		this.subcategoryService.save(subcategory);
		Product savedProduct = this.productService.get(product.getId());

		Assert.isTrue(product.getDescription().equals(savedProduct.getDescription()));

		this.categoryService.delete(category.getId());
	}

}
