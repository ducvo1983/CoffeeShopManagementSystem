package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;

public class CustomerOrderDaoFactory {
	private static CustomerOrderDaoFactory instance = new CustomerOrderDaoFactory();
	private ICustomerOrderDao impl = new CustomerOrderDaoImpl();
	public static CustomerOrderDaoFactory getInstance() { return instance; }
	
	public List<CustomerOrder> getAllCustomerOrders() {
		return impl.getAllCustomerOrders();
	}
	
	public boolean insertNewCustomerOrder(CustomerOrder o) {
		return impl.insertNewCustomerOrder(o);
	}
	public boolean updateCustomerOrder(CustomerOrder o) {
		return impl.updateCustomerOrder(o);
	}
	public boolean deleteCustomerOrder(CustomerOrder o) {
		return impl.deleteCustomerOrder(o);
	}
	public CustomerOrder findCustomerOrder(String orderNo) {
		return impl.findCustomerOrder(orderNo);
	}
	public List<CustomerOrder> getActiveCustomerOrders() {
		return impl.getActiveCustomerOrders();
	}
}
