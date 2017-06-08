package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Role;

public class RoleDaoFactory {
	private static RoleDaoFactory instance = new RoleDaoFactory();
	private RoleDaoFactory() {}
	public static RoleDaoFactory getInstance() {
		return instance;
	}
	
	private IRoleDao impl = new RoleDaoImpl();
	
	public List<Role> getAllRoles() {
		return impl.getAllRoles();
	}
}
