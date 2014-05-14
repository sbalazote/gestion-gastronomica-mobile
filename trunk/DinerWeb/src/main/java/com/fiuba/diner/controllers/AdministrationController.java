package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.constant.PaymentMediaState;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;
import com.fiuba.diner.service.TableService;

@Controller
public class AdministrationController {

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ParameterService parameterService;

	@RequestMapping(value = "/categoryAdministration", method = RequestMethod.GET)
	public String categoryAdministration(ModelMap modelMap) throws Exception {
		return "categoryAdministration";
	}

	@RequestMapping(value = "/entitySaved", method = RequestMethod.GET)
	public String error(ModelMap modelMap) throws Exception {
		return "entitySaved";
	}

	@RequestMapping(value = "/deleteConfirmation", method = RequestMethod.GET)
	public String deleteConfirmation(ModelMap modelMap) throws Exception {
		return "deleteConfirmation";
	}

	@RequestMapping(value = "/subcategoryAdministration", method = RequestMethod.GET)
	public String subcategoryAdministration(ModelMap modelMap) throws Exception {
		return "subcategoryAdministration";
	}

	@RequestMapping(value = "/productAdministration", method = RequestMethod.GET)
	public String productAdministration(ModelMap modelMap) throws Exception {
		return "productAdministration";
	}

	@RequestMapping(value = "/tableAdministration", method = RequestMethod.GET)
	public String tableAdministration(ModelMap modelMap) throws Exception {
		modelMap.put("restaurantName", this.parameterService.get(1).getRestaurantName());
		return "tableAdministration";
	}

	@RequestMapping(value = "/getTablesWithClosedOrder", method = RequestMethod.GET)
	@ResponseBody
	public List<Table> getTablesWithClosedOrder() throws IOException {
		return this.tableService.getTablesWithClosedOrder();
	}

	@RequestMapping(value = "/getOrderById", method = RequestMethod.GET)
	@ResponseBody
	public Order getOrderById(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));
		Order order = this.orderService.getOrder(id);
		return order;
	}

	@RequestMapping(value = "/savePaymentMedia", method = RequestMethod.POST)
	public @ResponseBody
	Order savePaymentMedia(@RequestParam Integer tableId, Integer paymentMediaId, Double change, Double total) throws Exception {
		Order order = this.orderService.getOrder(tableId);
		if (paymentMediaId.equals(PaymentMediaState.EFECTIVO.getState().getId())) {
			order.setPaymentMedia(PaymentMediaState.EFECTIVO.getState());
			order.setTotal(total);
			order.setChange(change);
		} else {
			if (paymentMediaId.equals(PaymentMediaState.TARJETA_DE_CREDITO.getState().getId())) {
				order.setPaymentMedia(PaymentMediaState.TARJETA_DE_CREDITO.getState());
			} else {
				order.setPaymentMedia(PaymentMediaState.TARJETA_DE_DEBITO.getState());
			}
		}
		this.orderService.save(order);
		return order;
	}
}