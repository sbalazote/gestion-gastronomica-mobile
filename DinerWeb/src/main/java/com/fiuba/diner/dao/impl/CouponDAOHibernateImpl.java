package com.fiuba.diner.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
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

	@SuppressWarnings("unchecked")
	@Override
	public Coupon validate(Integer id, String currentDate) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDateFormated = null;
		try {
			currentDateFormated = dateFormatter.parse(currentDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Query query = this.sessionFactory.getCurrentSession()
				.createQuery("from Coupon where :currentDate between startingDate and expirationDate and id = :id");
		query.setParameter("id", id);
		query.setParameter("currentDate", currentDateFormated);
		List<Coupon> list = query.list();
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
