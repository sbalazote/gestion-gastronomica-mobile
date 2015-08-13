package com.fiuba.diner.dao.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.fiuba.diner.model.Category;
import com.fiuba.diner.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class CategoryServiceTest {
	@Autowired
	private CategoryService categoryService;

	@Test
	public void save() {
		Category category = new Category();
		category.setActive(true);
		category.setDescription("Test description");
		this.categoryService.save(category);

		Category savedCategory = this.categoryService.get(category.getId());
		Assert.isTrue(category.getDescription().equals(savedCategory.getDescription()));

		this.categoryService.delete(category.getId());
	}

	@Test
	public void getAll() {
		Integer sizeBefore = this.categoryService.getAll().size();

		Category category1 = new Category();
		category1.setActive(true);
		category1.setDescription("Test description");
		this.categoryService.save(category1);
		Category category2 = new Category();
		category2.setActive(true);
		category2.setDescription("Test description 2");
		this.categoryService.save(category2);

		List<Category> categories = this.categoryService.getAll();

		Assert.isTrue(categories.size() == (sizeBefore + 2));

		// for (Category agent : categories) {
		// this.categoryService.delete(agent.getId());
		// }
	}

}
