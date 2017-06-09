package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Account;

public interface IAccountDao {
	public boolean newAccount(Account ac);
	public List<Account> getAllAccounts();
	public boolean updateAccount(Account ac);
	public boolean deleteAccount(Account ac);
	public Account findAccount(String userName);
}
