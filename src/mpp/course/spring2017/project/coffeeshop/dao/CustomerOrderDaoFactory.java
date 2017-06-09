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
	
	public boolean newCustomerOrder(CustomerOrder o) {
		return impl.newCustomerOrder(o);
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
}
