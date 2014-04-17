package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.model.Order;

public interface OrderService {

	Order get(Integer id);

	List<Order> getAll();

	void save(Order order);

	void delete(Integer orderId);

	Order getOrder(Integer tableId);

	List<Order> getRequestedOrders();

}
