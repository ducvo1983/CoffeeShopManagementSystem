package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import org.hibernate.Session;

import mpp.course.spring2017.project.coffeeshop.model.Token;

class TokenDaoImpl implements ITokenDao {

	@Override
	public boolean newToken(Token s) {
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
	public List<Token> getAllTokens() {
		List<Token> result = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {
			ss.beginTransaction();
			result = ss.createQuery("from Token", Token.class).getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return result;
	}

	@Override
	public boolean updateToken(Token bs) {
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
	public boolean deleteToken(Token bs) {
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
	public Token findToken(int ID) {
		Token bs = null;
		Session ss=HibernateFactory.getInstance().openSession();
		
		try {	
			ss.beginTransaction();
			bs = ss.createQuery("from Token where ID = " + ID, Token.class).getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			ss.close();
		}
		
		return bs;
	}

}
