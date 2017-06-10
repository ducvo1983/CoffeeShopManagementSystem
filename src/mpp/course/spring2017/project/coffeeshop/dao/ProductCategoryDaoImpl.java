package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;

import mpp.course.spring2017.project.coffeeshop.model.Employee;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.model.ProductCatelogy;

class ProductCategoryDaoImpl implements IProductCategoryDao {

	@Override
	public List<ProductCatelogy> getAllProductCategories() {
		List<ProductCatelogy> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from ProductCatelogy", ProductCatelogy.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}

	@Override
	public ProductCatelogy getProductCategory(int categoryID) {
		ProductCatelogy pc = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {	
			ss.beginTransaction();
			pc = ss.createQuery("from ProductCatelogy where ID = " + categoryID, ProductCatelogy.class).getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return pc;
	}
}
