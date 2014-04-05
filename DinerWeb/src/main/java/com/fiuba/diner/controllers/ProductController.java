package com.fiuba.diner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fiuba.diner.service.CategoryService;

@Controller
public class ProductController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct(ModelMap modelMap) throws Exception {
		modelMap.put("categories", this.categoryService.getAll());
		return "addProduct";
	}

}
