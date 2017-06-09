package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Token;

public interface ITokenDao {
	public boolean newToken(Token s);
	public List<Token> getAllTokens();
	public boolean updateToken(Token s);
	public boolean deleteToken(Token s);
	public Token findToken(int ID);
}
