package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.CategoryDTO;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public String addCategory(ModelMap modelMap) throws Exception {
		return "addCategory";
	}

	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public @ResponseBody
	void saveCategory(@RequestBody CategoryDTO categoryDTO) throws Exception {
		this.categoryService.save(this.buildModel(categoryDTO));
	}

	private Category buildModel(CategoryDTO categoryDTO) {
		Category category = new Category();
		if (categoryDTO.getId() != null) {
			category.setId(categoryDTO.getId());
		}

		category.setDescription(categoryDTO.getDescription());
		category.setActive(categoryDTO.isActive());
		return category;
	}

	@RequestMapping(value = "/getCategories", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> getCategories() throws IOException {
		return this.categoryService.getAll();
	}

	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
	public @ResponseBody
	void deleteCategory(@RequestParam Integer categoryId) throws Exception {
		this.categoryService.delete(categoryId);
	}

	@RequestMapping(value = "/updateCategory", method = RequestMethod.GET)
	public String updateCategory(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));

		Category category = this.categoryService.get(id);
		modelMap.put("id", category.getId());
		modelMap.put("description", category.getDescription());
		modelMap.put("active", category.getActive());
		return "updateCategory";
	}
}
