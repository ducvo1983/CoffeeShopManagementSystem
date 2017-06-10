package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;

import mpp.course.spring2017.project.coffeeshop.model.Employee;

class EmployeeDaoImpl implements IEmployeeDao {

	@Override
	public boolean newEmployee(Employee e) {
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
	public List<Employee> getAllEmployees() {
		List<Employee> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from Employee", Employee.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}

	@Override
	public Employee findEmployee(int ID) {
		Employee emp = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {	
			ss.beginTransaction();
			emp = ss.createQuery("from Employee where ID = " + ID, Employee.class).getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return emp;
	}
	
	@Override
	public boolean updateEmployee(Employee emp) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			ss.update(emp);			
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
	public boolean deleteEmployee(Employee emp) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			ss.delete(emp);			
			ss.getTransaction().commit();
			flag = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}

		return flag;
	}
}
