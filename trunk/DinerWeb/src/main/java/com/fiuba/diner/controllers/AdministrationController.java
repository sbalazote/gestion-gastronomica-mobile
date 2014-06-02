package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.fiuba.diner.dto.SalesReportDTO;
import com.fiuba.diner.dto.TableAttachmentDTO;
import com.fiuba.diner.dto.TableDTO;
import com.fiuba.diner.helper.OrderStateHelper;
import com.fiuba.diner.helper.PaymentMediaStateHelper;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.Waiter;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;
import com.fiuba.diner.service.TableService;
import com.fiuba.diner.service.WaiterService;

@Controller
public class AdministrationController {

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private WaiterService waiterService;

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
		modelMap.put("restaurantAddress", this.parameterService.get(1).getAddress());
		return "tableAdministration";
	}

	@RequestMapping(value = "/reportsAdministration", method = RequestMethod.GET)
	public String reportsAdministration(ModelMap modelMap) throws Exception {
		return "reportsAdministration";
	}

	@RequestMapping(value = "/getOrderByTable", method = RequestMethod.GET)
	@ResponseBody
	public Order getOrderByTable(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = Integer.valueOf(parameters.get("id"));
		Order order = this.orderService.getOrderByTable(id);
		return order;
	}

	@RequestMapping(value = "/getBilledOrdersBetweenDates", method = RequestMethod.POST)
	public @ResponseBody
	List<SalesReportDTO> getBilledOrdersBetweenDates(@RequestParam String from, @RequestParam String to) throws Exception {
		return this.orderService.getBilledOrdersBetweenDates(from, to);
	}

	@RequestMapping(value = "/savePaymentMedia", method = RequestMethod.POST)
	public @ResponseBody
	Order savePaymentMedia(@RequestParam Integer tableId, Integer paymentMediaId, Double change, Double total) throws Exception {
		Order order = this.orderService.getOrderByTable(tableId);
		order.setTotal(total);
		if (paymentMediaId.equals(PaymentMediaStateHelper.EFECTIVO.getState().getId())) {
			order.setPaymentMedia(PaymentMediaStateHelper.EFECTIVO.getState());
			order.setChange(change);
		} else {
			if (paymentMediaId.equals(PaymentMediaStateHelper.TARJETA_DE_CREDITO.getState().getId())) {
				order.setPaymentMedia(PaymentMediaStateHelper.TARJETA_DE_CREDITO.getState());
			} else {
				order.setPaymentMedia(PaymentMediaStateHelper.TARJETA_DE_DEBITO.getState());
			}
		}
		order.setState(OrderStateHelper.FACTURADA.getState());
		order.setBillingDate(new Date());
		Table table = this.tableService.get(order.getTables().get(0).getId());
		table.setWaiter(null);
		table.setState(TableStateHelper.AVAILABLE.getState());
		this.tableService.save(table);
		this.orderService.save(order);
		return order;
	}

	@RequestMapping(value = "/getPaparameters", method = RequestMethod.GET)
	@ResponseBody
	public Parameter getParameter(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = 1; // Integer.valueOf(parameters.get("id"));
		return this.parameterService.get(id);
	}

	@RequestMapping(value = "/getTables", method = RequestMethod.GET)
	@ResponseBody
	public List<Table> getTables() throws IOException {
		List<Table> tables = this.tableService.getAll();
		return tables;
	}

	@RequestMapping(value = "/getWaiters", method = RequestMethod.GET)
	@ResponseBody
	public List<Waiter> getWaiters() throws IOException {
		return this.waiterService.getAll();
	}

	@RequestMapping(value = "/getAttachedTables", method = RequestMethod.GET)
	@ResponseBody
	public List<TableDTO> getAttachedTables(@RequestParam Map<String, String> parameters) throws Exception {

		Integer tableId = Integer.valueOf(parameters.get("tableId"));
		Table table = this.tableService.get(tableId);

		return this.getSelectedTables(table);
	}

	@RequestMapping(value = "/saveAttachedTables", method = RequestMethod.POST)
	@ResponseBody
	public void saveAttachedTables(@RequestBody TableAttachmentDTO requestBody) throws Exception {

		Table table = this.tableService.get(requestBody.getTableId());
		if (requestBody.getAttachedTables() == null && requestBody.getAttachedTables().isEmpty()) {
			table.setAttachedTables(null);
		} else {
			List<Table> attachedTables = new ArrayList<Table>();
			for (Integer tableId : requestBody.getAttachedTables()) {
				attachedTables.add(this.tableService.get(tableId));
			}
			table.setAttachedTables(attachedTables);
		}
		this.tableService.save(table);
	}

	@RequestMapping(value = "/splitAttachedTables", method = RequestMethod.POST)
	@ResponseBody
	public void splitAttachedTables(@RequestParam Map<String, String> parameters) throws Exception {

		Integer tableId = Integer.valueOf(parameters.get("tableId"));
		Table table = this.tableService.get(tableId);
		table.setAttachedTables(null);
		this.tableService.save(table);
	}

	private List<TableDTO> getSelectedTables(Table table) {
		List<Table> attachedTables = table.getAttachedTables();
		List<Table> availableTables = this.tableService.getAvailableTables();
		availableTables.remove(table);

		List<TableDTO> tablesDTO = new ArrayList<TableDTO>();

		for (Table aTable : availableTables) {
			if (attachedTables.contains(aTable)) {
				tablesDTO.add(this.newTableDTO(aTable, true));
			} else {
				tablesDTO.add(this.newTableDTO(aTable, false));
			}
		}
		return tablesDTO;
	}

	private TableDTO newTableDTO(Table table, boolean selected) {
		TableDTO tableDTO = new TableDTO();

		tableDTO.setId(table.getId());
		tableDTO.setSelected(selected);

		return tableDTO;
	}

}