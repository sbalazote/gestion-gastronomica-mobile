package com.fiuba.diner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fiuba.diner.service.SubcategoryService;

@Controller
public class SubcategoryController {
	@Autowired
	private SubcategoryService subcategoryService;

	@RequestMapping(value = "/addSubcategory", method = RequestMethod.GET)
	public String addSubcategory(ModelMap modelMap) throws Exception {
		return "addSubcategory";
	}

}
