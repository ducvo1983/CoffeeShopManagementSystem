package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.OrderLine;

public interface IOrderLineDao {
	public boolean newOrderLine(OrderLine o);
	public List<OrderLine> getAllOrderLines();
	public List<OrderLine> getOrderLines(String orderNo);
	public List<OrderLine> getOrderLines(int productID);
	public boolean updateOrderLine(OrderLine o);
	public boolean deleteOrderLine(OrderLine o);
	public boolean deleteOrderLines(String orderNo);
	public OrderLine findOrderLine(String orderNo, int productID);
}
