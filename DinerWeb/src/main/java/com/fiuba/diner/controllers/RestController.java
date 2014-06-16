package com.fiuba.diner.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.fiuba.diner.helper.CipherHelper;
import com.fiuba.diner.helper.EncryptionHelper;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Coupon;
import com.fiuba.diner.model.Device;
import com.fiuba.diner.model.LoginRequest;
import com.fiuba.diner.model.LoginResponse;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.Parameter;
import com.fiuba.diner.model.Role;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.model.User;
import com.fiuba.diner.service.CategoryService;
import com.fiuba.diner.service.CouponService;
import com.fiuba.diner.service.DeviceService;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ParameterService;
import com.fiuba.diner.service.TableService;
import com.fiuba.diner.service.UserService;

@Controller
@RequestMapping(value = "/rest")
public class RestController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TableService tableService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private UserService userService;
	@Autowired
	private CouponService couponService;

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

	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	@ResponseBody
	public String devices(@RequestBody Device device) throws IOException {
		this.deviceService.updateRegistrationId(device);
		return device.getId();
	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	@ResponseBody
	public Integer postOrder(@RequestBody Order order) throws IOException {
		User user = order.getTables().get(0).getUser();
		if (user == null || !user.isActive() || !this.isWaiter(user)) {
			return 0;
		}
		this.orderService.update(order);
		return order.getId();
	}

	private boolean isWaiter(User user) {
		if (user.getRoles() == null) {
			return false;
		}
		for (Role role : user.getRoles()) {
			if (role.getId() == 3) {
				return true;
			}
		}
		return false;
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

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ResponseBody
	public LoginResponse userLogin(@RequestBody LoginRequest loginRequest) throws Exception {
		LoginResponse loginResponse = null;
		Boolean loginValid = false;
		String message = "";
		String password = loginRequest.getUserPassword();
		User userLogin = null;

		User user = this.userService.getByName(loginRequest.getUserName());
		if (user != null && password != null) {

			String hashedPassword = EncryptionHelper.generateHash(password);

			if (hashedPassword.equals(user.getPassword())) {
				if (user.isActive()) {
					Iterator<Role> it = user.getRoles().iterator();
					while (it.hasNext()) {
						Role role = it.next();
						if (role.getCode().equals("WAITER")) {
							// Se busca el device
							Device device = this.deviceService.get(loginRequest.getMobileId());
							if (device != null) {
								// El movil ya existe --> se actualiza el usuario
								device.setUser(user);
								this.deviceService.updateUserId(device);

							} else {
								// El movil es nuevo --> se crea en la tabla device
								device = new Device();
								device.setId(loginRequest.getMobileId());
								device.setRegistrationId("");
								device.setUser(user);
								this.deviceService.save(device);
							}

							loginValid = true;
							userLogin = user;

						} else {
							message = "El usuario ingresado no es mozo.";
						}
					}
				} else {
					message = "Usuario inactivo.";
				}
			} else {
				message = "Usuario / contrase�a incorrecta.";
			}

		} else {
			message = "Usuario / contrase�a incorrecta.";
		}

		loginResponse = new LoginResponse();
		loginResponse.setValid(loginValid);
		loginResponse.setMessage(message);
		loginResponse.setUser(userLogin);

		return loginResponse;
	}

	@RequestMapping(value = "/changeLockStateTable", method = RequestMethod.POST)
	@ResponseBody
	public void changeLockTable(@RequestBody Table table) throws IOException {
		this.tableService.save(table);
	}

	@RequestMapping(value = "/coupon/{encryptedId}", method = RequestMethod.GET)
	@ResponseBody
	public Coupon getCoupon(@PathVariable String encryptedId) throws Exception {
		String decryptedQRCodeText = CipherHelper.decrypt(encryptedId);
		Integer decryptedId = Integer.parseInt(decryptedQRCodeText);
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Coupon coupon = this.couponService.validate(decryptedId, currentDate);
		return coupon;
	}
}
