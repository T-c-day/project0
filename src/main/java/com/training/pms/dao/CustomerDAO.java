package com.training.pms.dao;

import com.training.pms.model.BankAccount;

public interface CustomerDAO {
	
	public int deposit(BankAccount customer);
	public int withdrawal(BankAccount customer);
	public boolean userExists(String username);
	public boolean userIdExists(int userId);
	public boolean transfer(int userId, int amount);
	public int showBalance(int userId);
	
}
