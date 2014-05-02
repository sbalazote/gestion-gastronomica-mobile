package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.constant.State;
import com.fiuba.diner.gcm.GCMServer;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Device;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.Waiter;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.DeviceService;
import com.fiuba.diner.service.FloorService;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;
import com.fiuba.diner.service.TableService;
import com.fiuba.diner.service.WaiterService;

@Controller
public class RestController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TableService tableService;
	@Autowired
	private FloorService floorService;
	@Autowired
	private WaiterService waiterService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> getCategories() throws IOException {
		return this.categoryService.getAll();
	}

	@RequestMapping(value = "/waiters", method = RequestMethod.GET)
	@ResponseBody
	public List<Waiter> getWaiters() throws IOException {
		return this.waiterService.getAll();
	}

	@RequestMapping(value = "/waiters/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Waiter getWaiter(@PathVariable Integer id) throws IOException {
		return this.waiterService.get(id);
	}

	@RequestMapping(value = "/floors", method = RequestMethod.GET)
	@ResponseBody
	public List<Floor> getFloors() throws IOException {
		return this.floorService.getAll();
	}

	@RequestMapping(value = "/floors/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Floor getFloor(@PathVariable Integer id) throws IOException {
		return this.floorService.get(id);
	}

	@RequestMapping(value = "/tables", method = RequestMethod.GET)
	@ResponseBody
	public List<Table> getTables() throws IOException {
		return this.tableService.getAll();
	}

	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	@ResponseBody
	public String devices(@RequestBody Device device) throws IOException {
		this.deviceService.updateRegistrationId(device);
		return device.getId();
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
		Order order = this.orderService.getOrder(id);
		return order;
	}

	@RequestMapping(value = "/parameters", method = RequestMethod.GET)
	@ResponseBody
	public Parameter getParameter(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer id = 1; // Integer.valueOf(parameters.get("id"));
		return this.parameterService.get(id);
	}

	@RequestMapping(value = "/changeOrderDetailState", method = RequestMethod.POST)
	public @ResponseBody
	OrderDetail changeOrderDetailState(@RequestParam Integer orderDetailId) throws Exception {
		OrderDetail orderDetail = this.orderService.changeOrderDetailState(orderDetailId);
		if (orderDetail.getState().getId().equals(State.PREPARADO.getId())) {
			GCMServer.sendNotification("Se encuentra para retirar el pedido: " + orderDetail.getProduct().getDescription());
		}
		return orderDetail;
	}
}
