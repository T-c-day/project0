package com.training.pms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.training.pms.model.BankAccount;
import com.training.pms.model.Employee;
import com.training.pms.utility.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO {

	// connects to our database using the DBConnection class
	Connection con = DBConnection.getConnection();
	Scanner scan = new Scanner(System.in);

	public EmployeeDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Override public void searchById(Employee employee) { con =
	 * DBConnection.getConnection(); PreparedStatement stat;
	 * 
	 * try { stat= con.prepareStatement("select * from customer where userId= ?");
	 * stat.setInt(1, employee.getUserId()); stat.execute();
	 * 
	 * 
	 * 
	 * System.out.println(stat); con.close(); stat.close(); }catch (SQLException e)
	 * { // TODO Auto-generated catch block e.printStackTrace();
	 * 
	 * } }
	 */
	@Override
	public boolean showTransactions(Employee employee) {
		System.out.println("Print all Transactions ");
		con = DBConnection.getConnection();
		Statement stat;

		try {
			stat = con.createStatement();

			ResultSet res = stat.executeQuery("select * from transactions");

			while (res.next()) {
				System.out.println( "User ID: " +res.getInt("userId")+ "  " + "Amount: "+ res.getDouble("amount")+ "  "
						+ "Transaction Date: " + res.getString("transactiondate")+ "  "
						+ "Transaction Type: "+ res.getString("transactionType"));
			}
			con.close();
			stat.close();
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return true;

	}

	@Override
	public boolean approveAccount(BankAccount customer) {

		con = DBConnection.getConnection();
		PreparedStatement statement;
		try {
			statement = con.prepareStatement("update customer set status= 'Y' where userid= ?");

			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter the userId you would like to approve: ");
			int approvalId = scan.nextInt();
			statement.setInt(1, approvalId);

			statement.executeUpdate();

			// System.out.println("Account has been approved");

			statement.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public BankAccount searchById(int userId) {
		BankAccount customer = new BankAccount();
		PreparedStatement stat;
		try {
			stat = con.prepareStatement("select userid, username, balance from customer where userId = ? ");
			stat.setInt(1, userId);

			ResultSet res = stat.executeQuery();
			res.next();
			customer.setUserId(res.getInt(1));
			customer.setUsername(res.getString(2));
			customer.setAccountBalance(res.getInt(3));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	/*
	 * @Override public boolean approveAccount(BankAccount customer) { boolean
	 * status = false; PreparedStatement statement; PreparedStatement update;
	 * 
	 * try { statement =
	 * con.prepareStatement("select * from customer where username = ?");
	 * statement.setString(1, customer.getUsername()); statement.execute();
	 * 
	 * 
	 * System.out.println("Do you approve this account: Y or N"); String response=
	 * scan.next();
	 * 
	 * if(response == "Y") { int rows = 0; update = con.
	 * prepareStatement("update status from customer where username= ? set status = 'Y'"
	 * ); update.setString(1, customer.getUsername()); rows =
	 * update.executeUpdate(); status = true;
	 * 
	 * 
	 * statement.close(); update.close(); con.close(); }
	 * 
	 * }catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return status; }
	 */

}
