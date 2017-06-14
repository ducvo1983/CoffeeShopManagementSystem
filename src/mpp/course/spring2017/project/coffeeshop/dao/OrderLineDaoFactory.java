package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.OrderLine;

public class OrderLineDaoFactory {
	private static OrderLineDaoFactory instance = new OrderLineDaoFactory();
	private IOrderLineDao impl = new OrderLineDaoImpl();
	public static OrderLineDaoFactory getInstance() { return instance; }
	
	public List<OrderLine> getAllOrderLines() {
		return impl.getAllOrderLines();
	}
	public List<OrderLine> getOrderLines(String orderNo) {
		return impl.getOrderLines(orderNo);
	}
	public boolean newOrderLine(OrderLine o) {
		return impl.newOrderLine(o);
	}
	public boolean updateOrderLine(OrderLine o) {
		return impl.updateOrderLine(o);
	}
	public boolean deleteOrderLine(OrderLine o) {
		return impl.deleteOrderLine(o);
	}
	public OrderLine findOrderLine(String orderNo, int productID) {
		return impl.findOrderLine(orderNo, productID);
	}
	public List<OrderLine> getOrderLines(int productID) {
		return impl.getOrderLines(productID);
	}
	public boolean deleteOrderLines(String orderNo) {
		return impl.deleteOrderLines(orderNo);
	}
}
