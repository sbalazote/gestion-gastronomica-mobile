package com.fiuba.diner.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.constant.State;
import com.fiuba.diner.dao.OrderDAO;
import com.fiuba.diner.dto.OrderDetailDTO;
import com.fiuba.diner.dto.SalesReportDTO;
import com.fiuba.diner.model.Order;
import com.fiuba.diner.model.OrderDetail;
import com.fiuba.diner.model.OrderDetailState;
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
		this.sessionFactory.getCurrentSession().merge(order);
	}

	@Override
	public void delete(Integer orderId) {
		Order order = this.get(orderId);
		this.sessionFactory.getCurrentSession().delete(order);
	}

	@Override
	public Order getOrderByTable(Integer tableId) {
		try {
			String sentence = "select o from Order as o inner join o.tables as t inner join o.details as d where t.id = :tableId and o.state.id <> 3";
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
					+ " and d.state.id <> " + State.ENTREGADO.getId() + " order by d.id desc";
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

	@Override
	public OrderDetail getOrderDetail(Integer orderDetailId) {
		Query query;
		query = this.sessionFactory.getCurrentSession().createQuery("select o From OrderDetail as o where o.id = " + orderDetailId);

		return (OrderDetail) query.list().get(0);
	}

	@Override
	public void saveOrderDetail(OrderDetail orderDetail) {
		this.sessionFactory.getCurrentSession().merge(orderDetail);
	}

	@Override
	public OrderDetailState getOrderDetailState(Integer stateId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select o from OrderDetailState as o where o.id =" + stateId);
		return (OrderDetailState) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesReportDTO> getBilledOrdersBetweenDates(String from, String to) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/yyyy");
		Date dateFromFormated = null;
		Date dateToFormated = null;
		try {
			dateFromFormated = dateFormatter.parse(from.toString());
			dateToFormated = dateFormatter.parse(to.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Query query = this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"select billingDate as billingDate, sum(total) as dayTotal from Order where billingDate >= :from and billingDate <= :to and state.id = 3 group by billingDate order by billingDate asc");
		query.setParameter("from", dateFromFormated);
		query.setParameter("to", dateToFormated);

		List<Object> list = query.list();
		List<SalesReportDTO> orders = new ArrayList<SalesReportDTO>();
		System.out.println("cantidad de ordenes:" + list.size());
		System.out.println("fecha desde:" + from);
		System.out.println("fecha hasta:" + to);
		for (Object object : list) {
			Object[] array = (Object[]) object;
			SalesReportDTO dto = new SalesReportDTO();
			dto.setBillingDate((Date) array[0]);
			dto.setDayTotal((Double) array[1]);
			orders.add(dto);
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersBetweenDates(String from, String to) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateFromFormated = null;
		Date dateToFormated = null;
		try {
			dateFromFormated = dateFormatter.parse(from.toString());
			dateToFormated = dateFormatter.parse(to.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Query query = this.sessionFactory.getCurrentSession().createQuery("from Order where billingDate >= :from and billingDate <= :to and state.id = 3");
		query.setParameter("from", dateFromFormated);
		query.setParameter("to", dateToFormated);

		return query.list();
	}
}
