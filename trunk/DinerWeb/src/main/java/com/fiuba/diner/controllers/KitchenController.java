package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.service.OrderService;

@Controller
public class KitchenController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/kitchen", method = RequestMethod.GET)
	public String kitchen(ModelMap modelMap) throws Exception {
		return "kitchen";
	}

	@RequestMapping(value = "/getKitchenOrders", method = RequestMethod.GET)
	@ResponseBody
	public List<OrderDetailDTO> getKitchenOrders() throws IOException {
		List<OrderDetailDTO> details = this.orderService.getRequestedOrders(true);
		return details;
	}

}
