package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.ProductCatelogy;

public class ProductCategoryDaoFactory {
	private static ProductCategoryDaoFactory instance = new ProductCategoryDaoFactory();
	private IProductCategoryDao impl = new ProductCategoryDaoImpl();
	public static ProductCategoryDaoFactory getInstance() { return instance; }
	
	public List<ProductCatelogy> getAllProductCategories() {
		return impl.getAllProductCategories();
	}
	
	public ProductCatelogy getProductCategory(int categoryID) {
		return impl.getProductCategory(categoryID);
	}
}
