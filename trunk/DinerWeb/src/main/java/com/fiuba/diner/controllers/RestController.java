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

import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;
import com.fiuba.diner.service.TableService;

@Controller
public class RestController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TableService tableService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> getCategories() throws IOException {
		return this.categoryService.getAll();
	}

	@RequestMapping(value = "/tables", method = RequestMethod.GET)
	@ResponseBody
	public List<Table> getTables() throws IOException {
		return this.tableService.getAll();
	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	@ResponseBody
	public Integer postOrder(@RequestBody Order order) throws IOException {
		this.orderService.save(order);
		return order.getId();
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@ResponseBody
	public Order getOrder(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));
		Order order = this.tableService.getOrder(id);
		return order;
	}

	@RequestMapping(value = "/parameters", method = RequestMethod.GET)
	@ResponseBody
	public Parameter getParameter(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = 1; // Integer.valueOf(parameters.get("id"));
		return this.parameterService.get(id);
	}

}
