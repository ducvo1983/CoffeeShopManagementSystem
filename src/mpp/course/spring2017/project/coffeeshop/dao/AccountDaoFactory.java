package mpp.course.spring2017.project.coffeeshop.dao;

import java.util.List;

import mpp.course.spring2017.project.coffeeshop.model.Account;

public class AccountDaoFactory {
	private static AccountDaoFactory instance = new AccountDaoFactory();
	private IAccountDao impl = new AccountDaoImpl();
	public static AccountDaoFactory getInstance() { return instance; }
	
	public List<Account> getAllAccounts() {
		return impl.getAllAccounts();
	}
	public boolean newAccount(Account ac) {
		return impl.newAccount(ac);
	}
	public boolean updateAccount(Account ac) {
		return impl.updateAccount(ac);
	}
	public boolean deleteAccount(Account ac) {
		return impl.deleteAccount(ac);
	}
	public Account findAccount(String userName) {
		return impl.findAccount(userName);
	}
}
