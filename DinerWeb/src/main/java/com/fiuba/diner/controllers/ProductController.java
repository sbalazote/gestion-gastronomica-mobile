package com.fiuba.diner.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fiuba.diner.service.CategoryService;
import com.google.gson.Gson;

@Controller
public class ProductController {

	private static final Gson gson = new Gson();

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/getCategories", method = RequestMethod.GET)
	public void getCategories(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.writeResponse(response, this.categoryService.getAll());
	}

	private void writeResponse(HttpServletResponse response, Object json) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(json));
	}

}
