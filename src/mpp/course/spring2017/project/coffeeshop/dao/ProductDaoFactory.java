package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Product;

public class ProductDaoFactory {
	private static ProductDaoFactory instance = new ProductDaoFactory();
	private IProductDao impl = new ProductDaoImpl();
	public static ProductDaoFactory getInstance() { return instance; }
	
	public List<Product> getProducts(int categoryID) {
		return impl.getProducts(categoryID);
	}
}
