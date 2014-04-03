package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.model.Category;
import com.fiuba.diner.service.CategoryService;

@Controller
public class ProductController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> getCategories() throws IOException {
		return this.categoryService.getAll();
	}

}
