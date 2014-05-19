package com.fiuba.diner.dao;

import java.util.List;

import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.OrderDetailState;

public interface OrderDAO {

	Order get(Integer id);

	List<Order> getAll();

	void save(Order order);

	void delete(Integer orderId);

	Order getOrderByTable(Integer tableId);

	List<OrderDetailDTO> getRequestedOrders(boolean kitchen);

	void saveOrderDetail(OrderDetail orderDetail);

	OrderDetail getOrderDetail(Integer orderDetailId);

	OrderDetailState getOrderDetailState(Integer stateId);
}
