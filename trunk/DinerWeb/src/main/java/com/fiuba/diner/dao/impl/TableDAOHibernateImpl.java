package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.TableDAO;
import com.fiuba.diner.model.Table;

@Repository
public class TableDAOHibernateImpl implements TableDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Table get(Integer id) {
		return (Table) this.sessionFactory.getCurrentSession().get(Table.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Table> getAll() {
		return this.sessionFactory.getCurrentSession().createQuery("from Table where active = true order by id").list();
	}

	@Override
	public void save(Table table) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(table);
	}

}
