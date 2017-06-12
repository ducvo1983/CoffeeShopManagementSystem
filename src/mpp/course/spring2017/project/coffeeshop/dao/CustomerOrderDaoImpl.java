package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;

import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;

class CustomerOrderDaoImpl implements ICustomerOrderDao {

	@Override
	public boolean insertNewCustomerOrder(CustomerOrder e) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			ss.save(e);
			ss.getTransaction().commit();
			flag = true;
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		} finally {
			ss.close();
		}
		
		return flag;
	}

	@Override
	public List<CustomerOrder> getAllCustomerOrders() {
		List<CustomerOrder> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from CustomerOrder", CustomerOrder.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}

	@Override
	public CustomerOrder findCustomerOrder(String orderNo) {
		CustomerOrder o = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {	
			ss.beginTransaction();
			o = ss.createQuery("from CustomerOrder where ORDER_NO = '" + orderNo + "'", CustomerOrder.class).getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return o;
	}
	
	@Override
	public boolean updateCustomerOrder(CustomerOrder o) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			
			ss.update(o);
			
			ss.getTransaction().commit();
			flag = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}

		return flag;
	}

	@Override
	public boolean deleteCustomerOrder(CustomerOrder o) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			ss.delete(o);			
			ss.getTransaction().commit();
			flag = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}

		return flag;
	}

	@Override
	public List<CustomerOrder> getActiveCustomerOrders() {
		List<CustomerOrder> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from CustomerOrder where STATUS = 'New' OR STATUS = 'Processing' OR STATUS = 'Token is alarm'", CustomerOrder.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}
}
