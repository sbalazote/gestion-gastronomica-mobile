package com.fiuba.diner.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiuba.diner.dto.RoleDTO;
import com.fiuba.diner.dto.UserDTO;
import com.fiuba.diner.helper.EncryptionHelper;
import com.fiuba.diner.model.Role;
import com.fiuba.diner.model.User;
import com.fiuba.diner.service.RoleService;
import com.fiuba.diner.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUser(ModelMap modelMap) throws Exception {
		modelMap.put("roles", this.roleService.getAll());
		return "addUser";
	}

	@RequestMapping(value = "/userAdministration", method = RequestMethod.GET)
	public String userAdministration(ModelMap modelMap) throws Exception {
		return "userAdministration";
	}

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUsers() throws IOException {
		return this.userService.getAll();
	}

	@RequestMapping(value = "/existsUser", method = RequestMethod.GET)
	public @ResponseBody
	Boolean existsUser(@RequestParam String name) throws Exception {
		return this.userService.exists(name);
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public @ResponseBody
	User saveUser(@RequestBody UserDTO userDTO) throws Exception {
		User user = this.buildModel(userDTO);
		this.userService.save(user);
		return user;
	}

	private User buildModel(UserDTO userDTO) {
		User user;
		if (userDTO.getId() != null) {
			user = this.userService.get(userDTO.getId());
			user.setActive(userDTO.isActive());
			if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
				user.setPassword(EncryptionHelper.generateHash(userDTO.getPassword()));
			}
		} else {
			user = new User();
			user.setName(userDTO.getName());
			user.setPassword(EncryptionHelper.generateHash(userDTO.getPassword()));
			user.setActive(userDTO.isActive());
		}
		List<Role> roles = new ArrayList<Role>();
		for (Integer roleId : userDTO.getRoles()) {
			Role role = this.roleService.get(roleId);
			roles.add(role);
		}
		user.setRoles(roles);
		return user;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.GET)
	public String updateUser(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {

		Integer userId = Integer.valueOf(parameters.get("id"));
		User user = this.userService.get(userId);
		modelMap.put("roles", this.getSelectedRoles(user));
		this.setValues(userId, modelMap);

		return "updateUser";
	}

	public void setValues(Integer userId, ModelMap modelMap) {
		User user = this.userService.get(userId);

		modelMap.put("id", user.getId());
		modelMap.put("name", user.getName());
		modelMap.put("password", user.getPassword());
		modelMap.put("active", user.isActive());
	}

	@RequestMapping(value = "/readUser", method = RequestMethod.GET)
	public String readUser(ModelMap modelMap, @RequestParam Map<String, String> parameters) throws Exception {
		Integer userId = Integer.valueOf(parameters.get("id"));
		this.setValues(userId, modelMap);

		return "readUser";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public @ResponseBody
	void deleteUser(@RequestParam Integer userId, Authentication authentication) throws Exception {
		String username = authentication.getPrincipal().toString();
		User user = this.userService.get(userId);
		if (user.getName().equals(username)) {
			throw new RuntimeException("Can't delete logged user");
		} else {
			this.userService.delete(userId);
		}
	}

	private List<RoleDTO> getSelectedRoles(User user) {
		List<Role> selectedRoles = user.getRoles();
		List<Role> allRoles = this.roleService.getAll();

		List<RoleDTO> rolesDTO = new ArrayList<RoleDTO>();

		for (Role role : allRoles) {
			if (selectedRoles.contains(role)) {
				rolesDTO.add(this.newRoleDTO(role, true));
			} else {
				rolesDTO.add(this.newRoleDTO(role, false));
			}
		}
		return rolesDTO;
	}

	private RoleDTO newRoleDTO(Role role, boolean selected) {
		RoleDTO roleDTO = new RoleDTO();

		roleDTO.setId(role.getId());
		roleDTO.setDescription(role.getDescription());
		roleDTO.setSelected(selected);

		return roleDTO;
	}
}
