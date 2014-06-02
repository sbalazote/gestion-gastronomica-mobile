package com.fiuba.diner.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.SalesReportDTO;
import com.fiuba.diner.service.OrderService;

@Controller
public class AdministrationController {

	@Autowired
	private OrderService orderService;

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
		return "tableAdministration";
	}

	@RequestMapping(value = "/reportsAdministration", method = RequestMethod.GET)
	public String reportsAdministration(ModelMap modelMap) throws Exception {
		return "reportsAdministration";
	}

	@RequestMapping(value = "/getBilledOrdersBetweenDates", method = RequestMethod.POST)
	public @ResponseBody
	List<SalesReportDTO> getBilledOrdersBetweenDates(@RequestParam String from, @RequestParam String to) throws Exception {
		return this.orderService.getBilledOrdersBetweenDates(from, to);
	}

}