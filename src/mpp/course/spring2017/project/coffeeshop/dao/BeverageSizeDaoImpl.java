package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;

import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;
import mpp.course.spring2017.project.coffeeshop.model.Employee;

class BeverageSizeDaoImpl implements IBeverageSizeDao {

	@Override
	public boolean newBeverageSize(BeverageSize s) {
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
	public List<BeverageSize> getAllBeverageSizes() {
		List<BeverageSize> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from BeverageSize", BeverageSize.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}

	@Override
	public boolean updateBeverageSize(BeverageSize bs) {
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
	public boolean deleteBeverageSize(BeverageSize bs) {
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
	public BeverageSize findBeverageSize(int ID) {
		BeverageSize bs = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {	
			ss.beginTransaction();
			bs = ss.createQuery("from BeverageSize where ID = " + ID, BeverageSize.class).getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return bs;
	}

}
