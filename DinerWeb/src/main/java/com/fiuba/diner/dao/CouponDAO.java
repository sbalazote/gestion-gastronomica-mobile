package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Coupon;

public interface CouponDAO {

	Coupon get(Integer id);

	List<Coupon> getAll();

	void save(Coupon coupon);

	void delete(Integer couponId);

	Coupon validate(Integer id, String currentDate);

}
