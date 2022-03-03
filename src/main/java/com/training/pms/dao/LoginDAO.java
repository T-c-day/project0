package com.training.pms.dao;

import com.training.pms.model.BankAccount;
import com.training.pms.model.Employee;
import com.training.pms.model.Login;

public interface LoginDAO {
	
	public boolean validateCustomer(String username, String password);
	public boolean loginExists(String username);
	public boolean validateEmployee(String username, String password);
	public boolean addCustomer(BankAccount customer);
	boolean addCustomer();
	public boolean checkStatus(String username);
	
}

