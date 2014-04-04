package com.fiuba.diner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.SubcategoryDTO;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Subcategory;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.SubcategoryService;

@Controller
public class SubcategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubcategoryService subcategoryService;

	@RequestMapping(value = "/addSubcategory", method = RequestMethod.GET)
	public String addSubcategory(ModelMap modelMap) throws Exception {
		modelMap.put("categories", this.categoryService.getAll());
		return "addSubcategory";
	}

	@RequestMapping(value = "/saveSubcategory", method = RequestMethod.POST)
	public @ResponseBody
	void saveSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) throws Exception {
		this.categoryService.save(this.buildModel(subcategoryDTO));
	}

	private Category buildModel(SubcategoryDTO subcategoryDTO) {
		Subcategory subcategory = new Subcategory();
		if (subcategoryDTO.getId() != null) {
			subcategory.setId(subcategoryDTO.getId());
		}

		subcategory.setDescription(subcategoryDTO.getDescription());
		Category category = this.categoryService.get(subcategoryDTO.getCategoryId());
		subcategory.setActive(subcategoryDTO.isActive());

		category.getSubcategories().add(subcategory);

		return category;
	}

	@RequestMapping(value = "/deleteSubcategory", method = RequestMethod.POST)
	public @ResponseBody
	void deleteSubcategory(@RequestParam Integer categoryId, Integer subcategoryId) throws Exception {
		Category category = this.categoryService.get(categoryId);
		Subcategory subcategoryToDelete = this.subcategoryService.get(subcategoryId);

		category.getSubcategories().remove(subcategoryToDelete);

		this.categoryService.save(category);
	}
}
