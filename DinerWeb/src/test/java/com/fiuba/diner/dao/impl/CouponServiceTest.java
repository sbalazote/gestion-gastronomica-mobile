package com.fiuba.diner.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.fiuba.diner.model.Coupon;
import com.fiuba.diner.service.CouponService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class CouponServiceTest {

	@Autowired
	private CouponService couponService;

	@Test
	public void testNotvalidCoupon() {
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Coupon notValidCoupon = this.couponService.validate(new Integer(6), currentDate);
		Assert.isNull(notValidCoupon);
	}

	@Test
	public void testValidCoupon() {
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		Coupon validCoupon = this.couponService.validate(new Integer(2), currentDate);
		Assert.notNull(validCoupon);
	}
}
