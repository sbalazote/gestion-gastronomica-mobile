package com.fiuba.diner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiuba.diner.constant.State;
import com.fiuba.diner.dao.TableDAO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Table;
import com.fiuba.diner.service.TableService;

@Service
@Transactional
public class TableServiceImpl implements TableService {

	private static final Logger logger = Logger.getLogger(TableServiceImpl.class);

	@Autowired
	private TableDAO tableDAO;

	@Override
	public Table get(Integer id) {
		return this.tableDAO.get(id);
	}

	@Override
	public List<Table> getAll() {
		return this.tableDAO.getAll();
	}

	@Override
	public void save(Table table) {
		this.tableDAO.save(table);
		logger.info("Se han guardado los cambios exitosamente. Id de Mesa: " + table.getId());
	}

	@Override
	public Order getOrder(Integer tableId) {
		List<Order> orders = this.tableDAO.getOrder(tableId);
		Order order = null;
		for (Order orderFromList : orders) {
			for (OrderDetail orderDetail : orderFromList.getDetails()) {
				if (!orderDetail.getState().equals(State.FACTURADO)) {
					order = orderFromList;
				}
			}
		}
		return order;
	}
}
