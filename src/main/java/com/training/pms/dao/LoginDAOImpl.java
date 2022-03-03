package com.training.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.training.pms.model.BankAccount;
import com.training.pms.model.Employee;
import com.training.pms.model.Login;
import com.training.pms.utility.DBConnection;

public class LoginDAOImpl implements LoginDAO {

	// connects to our database using the DBConnection class
	Connection connection = DBConnection.getConnection();

	@Override
	public boolean validateCustomer(String user, String pass) {
		boolean userValid = false;
		PreparedStatement statement;
		Scanner scanner=new Scanner(System.in);
		
		try {
			statement = connection.prepareStatement("select * from customer where username = ? and password = ? and status= 'Y' ");
			// take input from user to input username
			System.out.println("Please enter your Username: ");
			String username = scanner.next();

			// enter password
			System.out.println("Please enter your Password: ");
			String password = scanner.next();
			
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet res = statement.executeQuery();
			userValid = res.next();
			
		if (userValid) {
			System.out.println("Customer Login successful");
			System.out.println("Welcome " + username);
		}else {
			System.out.println("Account awaiting approval. Please check back later");
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userValid;
	}

	@Override
	public boolean loginExists(String username) {
		boolean userExists = false;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("select * from customer where username = ?");
			statement.setString(1, username);

			ResultSet res = statement.executeQuery();
			userExists = res.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}

	@Override
	public boolean validateEmployee(String username, String password) {
		boolean userValid = false;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("select * from customer where username = ? and password = ? ");
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet res = statement.executeQuery();
			userValid = res.next();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userValid;
	}

	@Override
	public boolean addCustomer(BankAccount customer) {
		// System.out.println("Registering Account: " + customer);

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("insert into customer values(default,?,?,?,?,default,default)");
			Scanner scan = new Scanner(System.in);

			System.out.println("Please enter your name: ");
			String fname = scan.next();

			System.out.println("Please enter a username: ");
			String username = scan.next();

			System.out.println("Please enter a new password: ");
			String password = scan.next();

			System.out.println("Please enter a starting balance: ");
			double accountBalance = scan.nextDouble();

			statement.setString(1, fname);
			statement.setString(2, username);
			statement.setString(3, password);
			statement.setDouble(4, accountBalance);

			statement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	@Override
	public boolean checkStatus(String username) {
		boolean statusValid = false;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("select status from customer where username = ? and status = 'N'");
			statement.setString(1, username);

			ResultSet res = statement.executeQuery();
			statusValid = res.next();
			System.out.println("Account is awaiting approval");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statusValid;
	}

	@Override
	public boolean addCustomer() {
		// TODO Auto-generated method stub
		return false;
	}

}
