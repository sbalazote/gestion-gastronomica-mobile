package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.helper.EncryptionHelper;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Device;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Role;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.User;
import com.fiuba.diner.model.Waiter;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.DeviceService;
import com.fiuba.diner.service.FloorService;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;
import com.fiuba.diner.service.TableService;
import com.fiuba.diner.service.UserService;
import com.fiuba.diner.service.WaiterService;

@Controller
@RequestMapping(value = "/rest")
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
	@Autowired
	private UserService userService;

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

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Order getOrder(@PathVariable Integer id) throws Exception {
		Order order = this.orderService.getOrderByTable(id);
		return order;
	}

	@RequestMapping(value = "/orders/{id}/close", method = RequestMethod.POST)
	@ResponseBody
	public void closeTable(@PathVariable Integer id) throws IOException {
		this.orderService.closeOrder(id);
	}

	@RequestMapping(value = "/parameters", method = RequestMethod.GET)
	@ResponseBody
	public Parameter getParameter(ModelMap modelMap) throws Exception {
		Integer id = 1;
		return this.parameterService.get(id);
	}

	@RequestMapping(value = "/waiterLogin", method = RequestMethod.POST)
	@ResponseBody
	public boolean waiterLogin(@RequestBody User userToValidate) throws Exception {
		String password = userToValidate.getPassword();
		User user = this.userService.getByName(userToValidate.getName());
		if (user != null && password != null) {

			String hashedPassword = EncryptionHelper.generateHash(password);

			if (hashedPassword.equals(user.getPassword())) {
				if (user.isActive()) {
					Iterator<Role> it = user.getRoles().iterator();
					while (it.hasNext()) {
						Role role = it.next();
						if (role.getCode().equals("WAITER")) {
							return true;
						}
					}
					return false;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} else {
			return false;
		}
	}
}
