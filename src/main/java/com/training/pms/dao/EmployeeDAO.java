package com.training.pms.dao;

import com.training.pms.model.BankAccount;
import com.training.pms.model.Employee;

public interface EmployeeDAO {
	
	//public void searchById(Employee employee);
	public boolean showTransactions(Employee employee);
	public boolean approveAccount(BankAccount customer);
	public BankAccount searchById(int userId);
}
