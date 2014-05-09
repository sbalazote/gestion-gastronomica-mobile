package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.UserDAO;
import com.fiuba.diner.model.User;

@Repository
public class UserDAOHibernateImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(User user) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public User get(Integer id) {
		return (User) this.sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public User getByName(String name) {
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery("from User where name = :name");
			query.setParameter("name", name);
			return (User) query.list().get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public Boolean exists(String name) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from User where name = :name");
		query.setParameter("name", name);
		return !query.list().isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(User.class).list();
	}

	@Override
	public void delete(Integer userId) {
		User user = this.get(userId);
		this.sessionFactory.getCurrentSession().delete(user);
	}

}