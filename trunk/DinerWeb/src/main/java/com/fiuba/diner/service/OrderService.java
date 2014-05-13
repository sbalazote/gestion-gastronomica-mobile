package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;

public interface OrderService {

	Order get(Integer id);

	List<Order> getAll();

	void save(Order order);

	void delete(Integer orderId);

	Order getOrder(Integer tableId);

	List<OrderDetailDTO> getRequestedOrders(boolean kitchen);

	OrderDetail changeOrderDetailState(Integer orderDetailId);

}