package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Employee;

public class EmployeeDaoFactory {
	private static EmployeeDaoFactory instance = new EmployeeDaoFactory();
	private EmployeeDaoFactory() {}
	public static EmployeeDaoFactory getInstance() {
		return instance;
	}
	
	private IEmployeeDao impl = new EmployeeDaoImpl();
	
	public boolean newEmployee(Employee e) {
		return impl.newEmployee(e);
	}
	
	public List<Employee> getAllEmployees() {
		return impl.getAllEmployees();
	}
	
	public boolean deleteEmployee(Employee emp) {
		return impl.deleteEmployee(emp);
	}
	
	public boolean updateEmployee(Employee emp) {
		return impl.updateEmployee(emp);
	}
}
