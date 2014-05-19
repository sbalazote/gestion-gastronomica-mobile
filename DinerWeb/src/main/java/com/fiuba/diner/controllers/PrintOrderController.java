package com.fiuba.diner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.helper.OrderPrinter;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;

@Controller
public class PrintOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = "/printOrder", method = RequestMethod.GET)
	public @ResponseBody
	void printOrder(@RequestParam Integer orderId) throws Exception {

		Order order = this.orderService.get(orderId);

		Parameter parameters = this.parameterService.get(1);

		// Obtengo usuario logueado.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		OrderPrinter orderPrinter = new OrderPrinter();
		orderPrinter.createPdf(parameters.getRestaurantName(), parameters.getAddress(), parameters.getDinnerServicePrice(), userName, "C:/orders/", order);
	}
}
