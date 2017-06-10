package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Token;

public class TokenDaoFactory {
	private static TokenDaoFactory instance = new TokenDaoFactory();
	private TokenDaoFactory() {}
	public static TokenDaoFactory getInstance() {
		return instance;
	}
	
	private ITokenDao impl = new TokenDaoImpl();
	
	public List<Token> getAllTokens() {
		return impl.getAllTokens();
	}
}
