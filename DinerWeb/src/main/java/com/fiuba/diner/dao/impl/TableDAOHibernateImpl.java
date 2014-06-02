package com.fiuba.diner.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.TableDAO;
import com.fiuba.diner.helper.TableStateHelper;
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
		List<Table> tables = this.sessionFactory.getCurrentSession().createQuery("from Table where active = true order by id").list();
		Set<Table> excludedTables = new HashSet<Table>();
		for (Table table : tables) {
			if (table.getAttachedTables() != null) {
				excludedTables.addAll(table.getAttachedTables());
			}
		}
		tables.removeAll(excludedTables);
		return tables;
	}

	@Override
	public void save(Table table) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(table);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Table> getAvailableTables() {
		List<Table> tables = this.sessionFactory.getCurrentSession()
				.createQuery("from Table where active = true and state.id = " + TableStateHelper.AVAILABLE.getState().getId() + " order by id").list();
		Set<Table> excludedTables = new HashSet<Table>();
		for (Table table : tables) {
			if (table.getAttachedTables() != null && !table.getAttachedTables().isEmpty()) {
				excludedTables.addAll(table.getAttachedTables());
				excludedTables.add(table);
			}
		}
		tables.removeAll(excludedTables);
		return tables;
	}
}
