package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;

public interface ICustomerOrderDao {
	public boolean newCustomerOrder(CustomerOrder o);
	public List<CustomerOrder> getAllCustomerOrders();
	public boolean updateCustomerOrder(CustomerOrder o);
	public boolean deleteCustomerOrder(CustomerOrder o);
	public CustomerOrder findCustomerOrder(String orderNo);
}
