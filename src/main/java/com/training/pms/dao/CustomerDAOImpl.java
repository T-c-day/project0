package com.training.pms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import com.training.pms.model.BankAccount;
import com.training.pms.utility.DBConnection;

public class CustomerDAOImpl implements CustomerDAO {

	// connects to our database using the DBConnection class
	Connection con = DBConnection.getConnection();

	public CustomerDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deposit(BankAccount customer) {
		con = DBConnection.getConnection();
		PreparedStatement statement;
		int balance = 0;

		try {
			statement = con.prepareStatement("update customer set balance = balance + ? where userId = ?");

			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter amount to deposit: ");
			int amount = scan.nextInt();

			System.out.println("Please enter your User ID: ");
			int id = scan.nextInt();

			statement.setInt(1, amount);
			statement.setInt(2, id);
			statement.executeUpdate();

			statement = con.prepareStatement("select balance from customer where userId = ?");
			statement.setInt(1, id);

			ResultSet res = statement.executeQuery();
			if (res.next()) {
				balance = res.getInt(1);
				System.out.println("Your new balance is: " + balance);
			}

			statement = con.prepareStatement(
					"insert into transactions(userId,amount,transactionDate,transactionType) values (?,?,?,?)");
			java.util.Date date = new java.util.Date();
			java.sql.Date sqldate = new java.sql.Date(date.getTime());

			statement.setInt(1, id);
			statement.setInt(2, amount);
			statement.setDate(3, sqldate);
			statement.setString(4, "deposit");
			statement.executeUpdate();

			res.close();
			con.close();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public int withdrawal(BankAccount customer) {
		con = DBConnection.getConnection();
		PreparedStatement statement;
		int balance = 0;

		try {
			statement = con.prepareStatement("update customer set balance = balance - ? where userId = ?");

			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter amount to withdrawal: ");
			int amount = scan.nextInt();

			System.out.println("Please enter your User ID: ");
			int id = scan.nextInt();

			statement.setInt(1, amount);
			statement.setInt(2, id);
			statement.executeUpdate();

			statement = con.prepareStatement("select balance from customer where userId = ?");
			statement.setInt(1, id);

			ResultSet res = statement.executeQuery();
			if (res.next()) {
				balance = res.getInt(1);
				System.out.println("Your new balance is: " + balance);
			}

			statement = con.prepareStatement("insert into transactions(userId,amount,transactionDate,transactionType) values (?,?,?,?)");
			java.util.Date date = new java.util.Date();
			java.sql.Date sqldate = new java.sql.Date(date.getTime());

			statement.setInt(1, id);
			statement.setInt(2, amount);
			statement.setDate(3, sqldate);
			statement.setString(4, "withdrawal");
			statement.executeUpdate();

			res.close();
			con.close();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public boolean userExists(String username) {
		con = DBConnection.getConnection();
		boolean userExists = false;
		PreparedStatement stat;

		try {
			stat = con.prepareStatement("select * from login where username = ?");
			stat.setString(1, username);

			ResultSet res = stat.executeQuery();
			userExists = res.next();

			con.close();
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}

	@Override
	public boolean userIdExists(int userId) {
		con = DBConnection.getConnection();
		boolean userExists = false;
		PreparedStatement stat;

		try {
			stat = con.prepareStatement("select * from customer where userId = ?");
			stat.setInt(1, userId);

			ResultSet res = stat.executeQuery();
			userExists = res.next();

			con.close();
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}

	@Override
	public boolean transfer(int userId, int amount) {
		con = DBConnection.getConnection();
		PreparedStatement stat;

		System.out.println("Please enter the user id of the person you would like to transfer to: ");
		Scanner scan = new Scanner(System.in);
		int receiver = scan.nextInt();

		System.out.println("Please enter your user id: ");
		int sender = scan.nextInt();

		System.out.println("Please enter the amount you would like to transfer: ");
		amount = scan.nextInt();

		try {
			// take out the money from sender and update balance
			stat = con.prepareStatement("update customer set balance = balance - ? where userId= ?");
			stat.setInt(1, amount);
			stat.setInt(2, sender);
			stat.executeUpdate();

			// update transaction table
			stat = con.prepareStatement("insert into transactions(userId,amount,transactionDate,transactionType) values (?,?,?,?)");
			java.util.Date date = new java.util.Date();
			java.sql.Date sqldate = new java.sql.Date(date.getTime());

			stat.setInt(1, sender);
			stat.setInt(2, amount);
			stat.setDate(3, sqldate);
			stat.setString(4, "transfer");
			stat.executeUpdate();

			// give money to receiver
			stat = con.prepareStatement("update customer set balance = balance + ? where userId= ?");
			stat.setInt(1, amount);
			stat.setInt(2, receiver);
			stat.executeUpdate();

			stat = con.prepareStatement("select balance from customer where userId = ?");
			stat.setInt(1, sender);

			ResultSet res = stat.executeQuery();
			if (res.next()) {
				int balance = res.getInt(1);
				System.out.println("Your new balance is: " + balance);
			}

			// update transactions table
			stat = con.prepareStatement("insert into transactions(userId,amount,transactionDate,transactionType) values (?,?,?,?)");
			java.util.Date date2 = new java.util.Date();
			java.sql.Date sqldate2 = new java.sql.Date(date2.getTime());

			stat.setInt(1, receiver);
			stat.setInt(2, amount);
			stat.setDate(3, sqldate);
			stat.setString(4, "transfer");
			stat.executeUpdate();

			con.close();
			stat.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;

	}

	@Override
	public int showBalance(int userId) {
		con = DBConnection.getConnection();
		PreparedStatement stat;
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please enter your userId:");
		userId = scanner.nextInt();
		
		try {
			stat = con.prepareStatement("select balance from customer where userId=?");
			stat.setInt(1, userId);
			
			ResultSet res = stat.executeQuery();
			if (res.next()) {
				int balance = res.getInt(1);
				System.out.println("Your balance is: " + balance);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}

}
