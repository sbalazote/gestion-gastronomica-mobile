package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.gcm.GCMServer;
import com.fiuba.diner.helper.OrderDetailStateHelper;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.service.OrderService;

@Controller
public class BarController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/bar", method = RequestMethod.GET)
	public String bar(ModelMap modelMap) throws Exception {
		return "bar";
	}

	@RequestMapping(value = "/getBarOrders", method = RequestMethod.GET)
	@ResponseBody
	public List<OrderDetailDTO> getBarOrders() throws IOException {
		List<OrderDetailDTO> details = this.orderService.getRequestedOrders(false);
		return details;
	}

	@RequestMapping(value = "/barProductAdministration", method = RequestMethod.GET)
	public String barProductAdministration(ModelMap modelMap) throws Exception {
		return "barProductAdministration";
	}

	@RequestMapping(value = "/changeBarOrderDetailState", method = RequestMethod.GET)
	@ResponseBody
	public OrderDetail changeOrderDetailState(@RequestParam Integer orderDetailId) throws Exception {
		OrderDetail orderDetail = this.orderService.changeOrderDetailState(orderDetailId);
		if (orderDetail.getState().equals(OrderDetailStateHelper.PREPARED.getState())) {
			GCMServer.sendNotification("Se encuentra para retirar el pedido: " + orderDetail.getProduct().getDescription());
		}
		return orderDetail;
	}
}
