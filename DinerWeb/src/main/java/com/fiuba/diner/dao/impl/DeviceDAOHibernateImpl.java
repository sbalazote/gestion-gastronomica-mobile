package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.DeviceDAO;
import com.fiuba.diner.model.Device;

@Repository
public class DeviceDAOHibernateImpl implements DeviceDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Device get(String id) {
		return (Device) this.sessionFactory.getCurrentSession().get(Device.class, id);
	}

	@Override
	public Device getByUser(Integer userId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from Device where user_id = :user_id");
		query.setParameter("user_id", userId);
		return (Device) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Device> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Device.class).list();
	}

	@Override
	public void save(Device device) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(device);
	}

	@Override
	public boolean updateRegistrationId(Device device) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("update Device set registration_id = :registrationId where id = :id");
		query.setParameter("registrationId", device.getRegistrationId());
		query.setParameter("id", device.getId());
		int numRowsUpdated = query.executeUpdate();
		if (numRowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUserId(Device device) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("update Device set user_id = :userId where id = :id");
		query.setParameter("userId", device.getUser().getId());
		query.setParameter("id", device.getId());
		int numRowsUpdated = query.executeUpdate();
		if (numRowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
}
