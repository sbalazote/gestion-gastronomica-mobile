package com.fiuba.diner.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.constant.State;
import com.fiuba.diner.dao.OrderDAO;
import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.Table;

@Repository
public class OrderDAOHibernateImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Order get(Integer id) {
		return (Order) this.sessionFactory.getCurrentSession().get(Order.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(Order.class).list();
	}

	@Override
	public void save(Order order) {
		// boolean found = false;
		// if (order.getId() != null) {
		// Order currentOrder = this.get(order.getId());
		// for (OrderDetail currentOrderDetail : currentOrder.getDetails()) {
		// found = false;
		// for (OrderDetail orderDetail : order.getDetails()) {
		// if (orderDetail.getId() != null) {
		// if (orderDetail.getId().equals(currentOrderDetail.getId())) {
		// found = true;
		// }
		// }
		// }
		// if (!found) {
		// this.sessionFactory.getCurrentSession().createQuery("delete from OrderDetail where id = " + currentOrderDetail.getId());
		// }
		// }
		// currentOrder.getDetails().clear();
		// for (OrderDetail orderDetail : order.getDetails()) {
		// currentOrder.getDetails().add(orderDetail);
		// }
		// currentOrder.setCustomerAmount(order.getCustomerAmount());
		// this.sessionFactory.getCurrentSession().saveOrUpdate(currentOrder);
		// } else {
		this.sessionFactory.getCurrentSession().merge(order);
		// }
	}

	@Override
	public void delete(Integer orderId) {
		Order order = this.get(orderId);
		this.sessionFactory.getCurrentSession().delete(order);
	}

	@Override
	public Order getOrder(Integer tableId) {
		try {
			String sentence = "select o from Order as o inner join o.tables as t inner join o.details as d where t.id = :tableId and d.state.id <>"
					+ State.FACTURADO.getId();
			Query query;
			query = this.sessionFactory.getCurrentSession().createQuery(sentence);
			query.setParameter("tableId", tableId);

			return (Order) query.list().get(0);

		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetailDTO> getRequestedOrders(boolean kitchen) {
		try {
			String sentence = "select d, t from Order as o inner join o.details as d inner join o.tables as t where d.product.kitchen = " + kitchen
					+ " and d.state.id <> " + State.FACTURADO.getId() + " order by d.id desc";
			Query query;
			query = this.sessionFactory.getCurrentSession().createQuery(sentence);

			List<Object> list = query.list();
			List<OrderDetailDTO> orders = new ArrayList<OrderDetailDTO>();
			for (Object object : list) {
				Object[] array = (Object[]) object;
				OrderDetailDTO dto = new OrderDetailDTO();
				dto.setDetail((OrderDetail) array[0]);
				dto.setTable((Table) array[1]);
				orders.add(dto);
			}
			return orders;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}
