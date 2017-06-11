package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.Product;

public class ProductDaoFactory {
	private static ProductDaoFactory instance = new ProductDaoFactory();
	private IProductDao impl = new ProductDaoImpl();
	public static ProductDaoFactory getInstance() { return instance; }
	
	public List<Product> getProducts(int categoryID) {
		return impl.getProducts(categoryID);
	}
	
	public boolean newProduct(Product p) {
		return impl.newProduct(p);
	}
	
	public boolean newProductWithSizePrice(Product p, List<BeverageSizePrice> listSizePrice) {
		return impl.newProductWithSizePrice(p, listSizePrice);
	}
	
	public boolean updateProduct(Product p) {
		return impl.updateProduct(p);
	}
	
	public boolean updateProductWithSizePrice(Product p, List<BeverageSizePrice> listSizePrice) {
		return impl.updateProductWithSizePrice(p, listSizePrice);
	}
	
	public boolean deleteProduct(Product p) {
		return impl.deleteProduct(p);
	}
}
