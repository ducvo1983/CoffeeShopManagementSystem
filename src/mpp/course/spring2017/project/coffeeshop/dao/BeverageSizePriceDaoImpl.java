package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.Employee;

class BeverageSizePriceDaoImpl implements IBeverageSizePriceDao {

	@Override
	public boolean newBeverageSizePrice(BeverageSizePrice s) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			ss.save(s);
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
	public List<BeverageSizePrice> getAllBeverageSizePrices() {
		List<BeverageSizePrice> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from BeverageSizePrice", BeverageSizePrice.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}

	@Override
	public boolean updateBeverageSizePrice(BeverageSizePrice bs) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			
			ss.update(bs);
			
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
	public boolean deleteBeverageSizePrice(BeverageSizePrice bs) {
		boolean flag = false;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			ss.delete(bs);			
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
	public BeverageSizePrice findBeverageSizePrice(int pID, int sizeID) {
		BeverageSizePrice bs = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {	
			ss.beginTransaction();
			bs = ss.createQuery("from BeverageSizePrice where PRODUCT_ID = " + pID + " AND SIZE_ID = " + sizeID, BeverageSizePrice.class).getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return bs;
	}

	@Override
	public List<BeverageSizePrice> getBeverageSizePrices(int pID) {
		List<BeverageSizePrice> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from BeverageSizePrice where PRODUCT_ID = " + pID, BeverageSizePrice.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}
}
