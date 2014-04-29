package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.dao.OrderDAO;
import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
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
	public Order getOrder(Integer tableId) {
		return this.orderDAO.getOrder(tableId);
	}

	@Override
	public List<OrderDetailDTO> getRequestedOrders(boolean kitchen) {
		return this.orderDAO.getRequestedOrders(kitchen);
	}

	@Override
	public OrderDetail changeState(Integer id) {
		return this.orderDAO.changeState(id);
	}
}
