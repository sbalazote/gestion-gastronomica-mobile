package com.fiuba.diner.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.fiuba.diner.helper.OrderDetailStateHelper;
import com.fiuba.diner.helper.OrderStateHelper;
import com.fiuba.diner.helper.PaymentMediaStateHelper;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.service.OrderService;
import com.fiuba.diner.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context-test.xml" })
public class OrderServiceTest {
	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Test
	public void save() {
		Order order = new Order();
		Integer sizeBefore = this.orderService.getAll().size();

		order.setBillingDate(new Date());
		order.setCustomerAmount(1);
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setAmount(1);
		orderDetail.setComment("");
		orderDetail.setDeliveryDate(new Date());
		orderDetail.setPreparationEndDate(new Date());
		orderDetail.setPreparationStartDate(new Date());
		orderDetail.setProduct(this.productService.get(1));
		orderDetail.setRequestDate(new Date());
		orderDetail.setState(OrderDetailStateHelper.NEW.getState());
		details.add(orderDetail);
		order.setDetails(details);
		order.setPaymentMedia(PaymentMediaStateHelper.TARJETA_DE_CREDITO.getState());
		order.setState(OrderStateHelper.ABIERTA.getState());
		order.setTotal(Double.valueOf("100"));

		this.orderService.save(order);
		Assert.isTrue(this.orderService.getAll().size() == (sizeBefore + 1));

	}
}
