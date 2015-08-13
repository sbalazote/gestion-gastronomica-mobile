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
import com.fiuba.diner.model.Subcategory;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.SubcategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class SubcategoryServiceTest {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubcategoryService subcategoryService;

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

		Subcategory savedSubcategory = this.subcategoryService.get(subcategory.getId());
		Assert.isTrue(subcategory.getDescription().equals(savedSubcategory.getDescription()));

		this.categoryService.delete(category.getId());
	}
}
