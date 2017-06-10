package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Product;

public interface IProductDao {
	public boolean newProduct(Product p);
	public List<Product> getAllProducts();
	public List<Product> getProducts(int categoryID);
	public boolean updateProduct(Product p);
	public boolean deleteProduct(Product p);
	public Product findProduct(int ID);
}
