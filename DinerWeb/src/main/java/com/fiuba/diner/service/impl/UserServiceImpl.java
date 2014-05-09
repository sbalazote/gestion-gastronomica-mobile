package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.UserDAO;
import com.fiuba.diner.model.User;
import com.fiuba.diner.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Override
	public void save(User user) {
		this.userDAO.save(user);
		logger.info("Se han guardado los cambios exitosamente. Id de Usuario: " + user.getId());
	}

	@Override
	public User get(Integer id) {
		return this.userDAO.get(id);
	}

	@Override
	public User getByName(String name) {
		return this.userDAO.getByName(name);
	}

	@Override
	public Boolean exists(String name) {
		return this.userDAO.exists(name);
	}

	@Override
	public void delete(Integer userId) {
		this.userDAO.delete(userId);
	}

	@Override
	public List<User> getAll() {
		return this.userDAO.getAll();
	}

}
