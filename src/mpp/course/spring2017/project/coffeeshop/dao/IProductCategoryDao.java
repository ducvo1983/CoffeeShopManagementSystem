package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.ProductCatelogy;

public interface IProductCategoryDao {
	public List<ProductCatelogy> getAllProductCategories();
	public ProductCatelogy getProductCategory(int categoryID);
}
