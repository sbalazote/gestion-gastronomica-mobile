package com.fiuba.diner.service;

import java.util.List;

import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.dto.SalesReportDTO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;

public interface OrderService {

	Order get(Integer id);

	List<Order> getAll();

	void save(Order order);

	void update(Order order);

	void delete(Integer orderId);

	Order getOrderByTable(Integer tableId);

	List<OrderDetailDTO> getRequestedOrders(boolean kitchen);

	OrderDetail changeOrderDetailState(Integer orderDetailId);

	void closeOrder(Integer orderId);

	List<SalesReportDTO> getBilledOrdersBetweenDates(String from, String to);

	List<Order> getOrdersBetweenDates(String from, String to);

	void cancelOrderDetail(Integer id);

}
