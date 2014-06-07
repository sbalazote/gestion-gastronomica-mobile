package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.CouponDAO;
import com.fiuba.diner.model.Coupon;
import com.fiuba.diner.service.CouponService;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

	private static final Logger logger = Logger.getLogger(CouponServiceImpl.class);

	@Autowired
	private CouponDAO couponDAO;

	@Override
	public Coupon get(Integer id) {
		return this.couponDAO.get(id);
	}

	@Override
	public List<Coupon> getAll() {
		return this.couponDAO.getAll();
	}

	@Override
	public void save(Coupon coupon) {
		this.couponDAO.save(coupon);
		logger.info("Se han guardado los cambios exitosamente. Id de Cupon: " + coupon.getId());
	}

	@Override
	public void delete(Integer couponId) {
		this.couponDAO.delete(couponId);
	}
}
