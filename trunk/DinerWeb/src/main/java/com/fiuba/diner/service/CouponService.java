package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Coupon;

public interface CouponService {
	Coupon get(Integer id);

	List<Coupon> getAll();

	void save(Coupon coupon);

	void delete(Integer couponId);
}
