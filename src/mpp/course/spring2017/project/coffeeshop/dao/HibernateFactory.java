package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;
import mpp.course.spring2017.project.coffeeshop.model.Employee;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.model.Token;

public class HibernateFactory {
	private static HibernateFactory instance = new HibernateFactory();
	private HibernateFactory() {}
	public static HibernateFactory getInstance() {
		return instance;
	}
	
	final static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.configure() // configures settings from hibernate.cfg.xml
			.build();
	final static SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	
	public Session openSession() {
		return sf.openSession();
	}
	
	public void closeSession() {
		if (sf != null && sf.isOpen()) {
			sf.close();
		}
	}
	public static boolean newEmployee(Employee e) {
		try {			
			Session ss=sf.openSession();
			ss.beginTransaction();
			ss.save(e);
			ss.getTransaction().commit();
			ss.close();
			return true;
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
			System.out.println(ex.getLocalizedMessage());
			return false;
		}
	}
	
	public static List<Employee> getAllEmployees() {
		/*EntityManager entityManager = sf.createEntityManager();
		entityManager.getTransaction().begin();		
		List<Employee> result = entityManager.createQuery("from Employee where first_name = 'Tri'", Employee.class).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();*/
		
		Session ss=sf.openSession();
		ss.beginTransaction();
		List<Employee> result = ss.createQuery("from Employee", Employee.class).getResultList();
		ss.getTransaction().commit();
		return result;
	}
	
	public static Employee findEmployee(String firstName) {		
		Session ss=sf.openSession();
		ss.beginTransaction();
		Employee result = ss.createQuery("from Employee where firstName = '"+firstName+"'", Employee.class).getSingleResult();
		ss.getTransaction().commit();
		return result;
	}
	
	public static boolean newAccount(Account acc) {
		try {			
			Session ss=sf.openSession();
			ss.beginTransaction();
			ss.save(acc);
			ss.getTransaction().commit();
			ss.close();
			return true;
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
			System.out.println(ex.getLocalizedMessage());
			return false;
		}
	}
	
	public static Token findToken(int ID) {		
		Session ss=sf.openSession();
		ss.beginTransaction();
		Token result = ss.createQuery("from Token where ID = "+ID, Token.class).getSingleResult();
		ss.getTransaction().commit();
		return result;
	}
	
	public static Account findAccount(String userName) {		
		Session ss=sf.openSession();
		ss.beginTransaction();
		Account result = ss.createQuery("from Account where userName = '"+userName+"'", Account.class).getSingleResult();
		ss.getTransaction().commit();
		return result;
	}
	
	public static Product findProduct(int productID) {		
		Session ss=sf.openSession();
		ss.beginTransaction();
		Product result = ss.createQuery("from Product where ID = "+productID+"", Product.class).getSingleResult();
		ss.getTransaction().commit();
		return result;
	}
	
	public static boolean newOrder(CustomerOrder order) {
		try {
			Session ss=sf.openSession();
			ss.beginTransaction();
			ss.save(order);ss.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public static CustomerOrder findOrder(String orderNo) {		
		Session ss=sf.openSession();
		ss.beginTransaction();
		CustomerOrder result = ss.createQuery("from CustomerOrder where order_no = '"+orderNo+"'", CustomerOrder.class).getSingleResult();
		ss.getTransaction().commit();
		return result;
	}
}
