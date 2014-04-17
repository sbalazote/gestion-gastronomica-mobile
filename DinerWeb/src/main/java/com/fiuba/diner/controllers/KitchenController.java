package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.model.Order;
import com.fiuba.diner.service.OrderService;

@Controller
public class KitchenController {
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/ordersReception", method = RequestMethod.GET)
	public String ordersReception(ModelMap modelMap) throws Exception {
		return "ordersReception";
	}

	@RequestMapping(value = "/getOrders", method = RequestMethod.GET)
	@ResponseBody
	public List<Order> getOrders() throws IOException {
		return this.orderService.getRequestedOrders();
	}
}
