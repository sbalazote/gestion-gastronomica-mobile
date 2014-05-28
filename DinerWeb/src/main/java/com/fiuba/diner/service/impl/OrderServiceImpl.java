package com.fiuba.diner.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.constant.State;
import com.fiuba.diner.dao.OrderDAO;
import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.dto.SalesReportDTO;
import com.fiuba.diner.helper.OrderDetailStateHelper;
import com.fiuba.diner.helper.OrderStateHelper;
import com.fiuba.diner.helper.TableStateHelper;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.OrderDetailState;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDAO orderDAO;

	@Override
	public Order get(Integer id) {
		return this.orderDAO.get(id);
	}

	@Override
	public List<Order> getAll() {
		return this.orderDAO.getAll();
	}

	@Override
	public void save(Order order) {
		this.orderDAO.save(order);
		logger.info("Se han guardado los cambios exitosamente. Id de Pedido: " + order.getId());
	}

	@Override
	public void delete(Integer orderId) {
		this.orderDAO.delete(orderId);
	}

	@Override
	public Order getOrderByTable(Integer tableId) {
		return this.orderDAO.getOrderByTable(tableId);
	}

	@Override
	public List<OrderDetailDTO> getRequestedOrders(boolean kitchen) {
		return this.orderDAO.getRequestedOrders(kitchen);
	}

	@Override
	public OrderDetail changeOrderDetailState(Integer orderDetailId) {

		OrderDetail orderDetail = this.orderDAO.getOrderDetail(orderDetailId);

		Integer orderDetailStateTo = orderDetail.getState().getId() + 1;

		OrderDetailState orderDetailState = this.orderDAO.getOrderDetailState(orderDetailStateTo);

		orderDetail.setState(orderDetailState);

		if (orderDetailStateTo.equals(State.EN_PREPARACION.getId())) {
			orderDetail.setPreparationStartDate(new Date());
		}
		if (orderDetailStateTo.equals(State.PREPARADO.getId())) {
			orderDetail.setPreparationEndDate(new Date());
		}
		this.orderDAO.saveOrderDetail(orderDetail);

		return orderDetail;
	}

	@Override
	public void closeOrder(Integer orderId) {
		Order order = this.get(orderId);
		order.setState(OrderStateHelper.CERRADA.getState());
		for (OrderDetail detail : order.getDetails()) {
			detail.setState(OrderDetailStateHelper.DELIVERED.getState());
		}
		for (Table table : order.getTables()) {
			table.setLocked(false);
			table.setState(TableStateHelper.CLOSED.getState());
		}
		this.save(order);
	}

	@Override
	public List<SalesReportDTO> getBilledOrdersBetweenDates(String from, String to) {
		return this.orderDAO.getBilledOrdersBetweenDates(from, to);
	}
}
