package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.User;

public interface UserDAO {

	void save(User user);

	User get(Integer id);

	User getByName(String name);

	Boolean exists(String name);

	List<User> getAll();

	void delete(Integer userId);

}
