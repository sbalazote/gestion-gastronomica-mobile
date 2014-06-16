package com.fiuba.diner.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.fiuba.diner.model.Role;
import com.fiuba.diner.model.User;
import com.fiuba.diner.service.RoleService;
import com.fiuba.diner.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class UserServiceTest {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Test
	public void save() {
		User user = new User();
		Integer sizeBefore = this.userService.getAll().size();

		user.setActive(true);
		Random rm = new Random();

		user.setName("Test " + rm.nextDouble());
		user.setPassword("12346567");
		List<Role> roles = new ArrayList<Role>();
		roles.add(this.roleService.get(1));
		user.setRoles(roles);
		this.userService.save(user);
		Assert.isTrue(this.userService.getAll().size() == (sizeBefore + 1));

	}
}
