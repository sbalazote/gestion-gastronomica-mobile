package com.fiuba.diner.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.dao.CouponDAO;
import com.fiuba.diner.model.Coupon;

@Repository
public class CouponDAOHibernateImpl implements CouponDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Coupon get(Integer id) {
		return (Coupon) this.sessionFactory.getCurrentSession().get(Coupon.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coupon> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Coupon.class).list();
	}

	@Override
	public void save(Coupon coupon) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(coupon);
	}

	@Override
	public void delete(Integer couponId) {
		Coupon coupon = this.get(couponId);
		this.sessionFactory.getCurrentSession().delete(coupon);
	}
}
