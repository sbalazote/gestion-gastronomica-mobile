package com.fiuba.diner.dao.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CategoryServiceTest.class, CouponServiceTest.class, OrderServiceTest.class, ProductServiceTest.class, SubcategoryServiceTest.class,
		TableServiceTest.class, UserServiceTest.class })
public class JUnitTestSuite {
}