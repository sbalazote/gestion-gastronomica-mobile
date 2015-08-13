package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.User;

public interface UserService {

	void save(User user);

	User get(Integer id);

	User getByName(String name);

	Boolean exists(String name);

	void delete(Integer userId);

	List<User> getAll();

}