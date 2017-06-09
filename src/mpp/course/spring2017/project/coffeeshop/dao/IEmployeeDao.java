package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Employee;

public interface IEmployeeDao {
	public boolean newEmployee(Employee e);
	public List<Employee> getAllEmployees();
	public boolean updateEmployee(Employee emp);
	public boolean deleteEmployee(Employee emp);
	public Employee findEmployee(int ID);
}
