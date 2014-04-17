package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.model.Order;

public interface OrderDAO {

	Order get(Integer id);

	List<Order> getAll();

	void save(Order order);

	void delete(Integer orderId);

	Order getOrder(Integer tableId);

	List<Order> getRequestedOrders();
}
