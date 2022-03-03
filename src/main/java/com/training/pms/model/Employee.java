package com.training.pms.model;

import java.util.Objects;

public class Employee {
	
	private String fname;
	private String username;
	private String password;
	private double accountBalance;
	private String accountType;
	private int userId;


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	@Override
	public String toString() {
		return "Employee [fname=" + fname + ", username=" + username + ", password=" + password + ", accountBalance="
				+ accountBalance + ", accountType=" + accountType + ", userId=" + userId + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(accountBalance, accountType, fname, password, userId, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Double.doubleToLongBits(accountBalance) == Double.doubleToLongBits(other.accountBalance)
				&& Objects.equals(accountType, other.accountType) && Objects.equals(fname, other.fname)
				&& Objects.equals(password, other.password) && Objects.equals(userId, other.userId)
				&& Objects.equals(username, other.username);
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public double getAccountBalance() {
		return accountBalance;
	}


	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public Employee() {
		// TODO Auto-generated constructor stub
	}

}
