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
		this.categoryService.save(this.getCategory(subcategoryDTO));
	}

	private Category getCategory(SubcategoryDTO subcategoryDTO) {
		Subcategory subcategory = new Subcategory();

		subcategory.setDescription(subcategoryDTO.getDescription());
		subcategory.setActive(subcategoryDTO.isActive());

		Category category = this.categoryService.get(subcategoryDTO.getCategoryId());
		if (subcategoryDTO.getId() != null) {
			subcategory.setId(subcategoryDTO.getId());
		}

		if (subcategoryDTO.getCategoryId().equals(subcategoryDTO.getOldCategoryId())) {
			for (Subcategory subcat : category.getSubcategories()) {
				if (subcat.getId().equals(subcategory.getId())) {
					subcat.setDescription(subcategoryDTO.getDescription());
					subcat.setActive(subcategoryDTO.isActive());
				}
			}
		} else {
			category.getSubcategories().add(subcategory);
		}

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

	@RequestMapping(value = "/updateSubcategory", method = RequestMethod.GET)
	public String updateSubcategory(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));
		Integer categoryId = Integer.valueOf(parameters.get("categoryId"));

		modelMap.put("categories", this.categoryService.getAll());

		Subcategory subcategory = this.subcategoryService.get(id);

		modelMap.put("id", subcategory.getId());
		modelMap.put("description", subcategory.getDescription());
		modelMap.put("categoryId", categoryId);
		modelMap.put("active", subcategory.getActive());

		return "updateSubcategory";
	}

	@RequestMapping(value = "/getSubcategories", method = RequestMethod.GET)
	@ResponseBody
	public List<Subcategory> getSubcategories() throws IOException {
		return this.subcategoryService.getAll();
	}
}
