package com.training.pms;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.training.pms.dao.CustomerDAO;
import com.training.pms.dao.CustomerDAOImpl;
import com.training.pms.dao.EmployeeDAO;
import com.training.pms.dao.EmployeeDAOImpl;
import com.training.pms.dao.LoginDAO;
import com.training.pms.dao.LoginDAOImpl;
import com.training.pms.model.BankAccount;
import com.training.pms.model.Employee;
import com.training.pms.model.Login;
import com.training.pms.utility.DBConnection;

public class MainMenu<Int> {

	Scanner scanner = new Scanner(System.in);
	int choice = 0;

	CustomerDAO customerDAO = new CustomerDAOImpl();
	BankAccount customerAccount = new BankAccount();

	EmployeeDAO employeeDAO = new EmployeeDAOImpl();
	Employee employee = new Employee();

	LoginDAO loginDAO = new LoginDAOImpl();
	Login login = new Login();

	public void startMainMenu() {

		// declare local variables for input
		String username = "";
		String password = "";
		double balance = 0;
		int amount = 0;
		String fname = "";
		String accountType = "";
		int userId = 0;
		boolean transfer;
		double accountBalance;
		String status = null;
		
		//start main menu
		while (true) {

			System.out.println("__________________________________________________________________");
			System.out.println("Welcome to the Bread Bank");
			System.out.println("------------------------------------------------");
			System.out.println("1. Login to Customer Account ");
			System.out.println("2. Login to Employee Account ");
			System.out.println("3. Create New Account ");
			System.out.println("7. EXIT");
			System.out.println("__________________________________________________________________");
			System.out.println("Please enter your choice (1 - 3 or 7 to exit): ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				// customer login page
				System.out.println("Welcome to the Customer Login Page");
				System.out.println("Press C for Customer");
				accountType = scanner.next();
				accountType = accountType.toLowerCase();

				// check if username exists
				if (loginDAO.validateCustomer(username, password)) {

				
				if (accountType.equalsIgnoreCase("c")) {

					while (true) {
						System.out.println("__________________________________________________________________");
						System.out.println("M E N U");
						System.out.println("------------------------------------------------");
						System.out.println("1. View Balance");
						System.out.println("2. Make a Withdrawal");
						System.out.println("3. Make a Deposit");
						System.out.println("4. Send Money");
						System.out.println("7. EXIT");
						System.out.println("__________________________________________________________________");
						System.out.println("Please enter your choice (1-4 or 7 to exit): ");
						choice = scanner.nextInt();

						switch (choice) {
						case 1:
							//view balance
							System.out.println("View your Balance");
							
							//System.out.println("Please enter your userId:");
							//userId = scanner.nextInt();

							customerDAO.showBalance(userId);

							break;

						case 2:
							//make withdrawal
							System.out.println("Make a Withdrawal");

							// make sure amount being withdrawn is not more than total in account
							if (amount <= customerAccount.getAccountBalance()) {
								balance = customerDAO.withdrawal(customerAccount);

							} else {
								System.out.println("Amount to be withdrawn cannot be greater than total balance");
							}

							break;

						case 3:
							//make deposit
							System.out.println("Make a Deposit");
							balance = customerDAO.deposit(customerAccount);

							break;

						case 4:
							//transfer
							System.out.println("Send Money to a Friend/Family");

							if (!(customerDAO.userIdExists(userId))) {
								customerDAO.transfer(userId, amount);
							} else {
								System.out.println("This User ID does not exist, please try a different ID.");
							}

							break;

						case 7:
							//exit
							System.out.println("Thank you for visiting The Bread Bank!");
							System.exit(0);

						default:
							System.out.println("Invalid choice, please select an option 1-4");
							break;
						}

					}

				} else {
					System.out.println("This user does not exist");
				}
				}
				break;

			case 2:
				//employee login page
				System.out.println("Welcome to the Employee Login Page");
				System.out.println("Press E for Employee");
				accountType = scanner.next();
				accountType = accountType.toLowerCase();

				// take input from user to input username
				System.out.println("Please enter your Username: ");
				username = scanner.next();

				// enter password
				System.out.println("Please enter your Password: ");
				password = scanner.next();

				// check if username exists
				if (!loginDAO.validateEmployee(username, password)) {
					System.out.println("Employee Login successful");
					System.out.println("Welcome " + username);

				} else {
					System.out.println("This user does not exist");
					continue;
				}

				if (accountType.equalsIgnoreCase("e")) {
					//employee menu
					while (true) {

						System.out.println("------------------------------------------------");
						System.out.println("1. View Customer Balance by ID ");
						System.out.println("2. Approve / Deny Accounts ");
						System.out.println("3. View all transactions");
						System.out.println("7. EXIT");
						System.out.println("__________________________________________________________________");
						System.out.println("Please enter your choice (1 - 3 or 7 to exit): ");
						choice = scanner.nextInt();

						switch (choice) {
						case 1:
							//view a customers balance
							System.out.println("Please enter Customer ID:");
							userId = scanner.nextInt();

							if (customerDAO.userIdExists(userId)) {
								BankAccount temp = employeeDAO.searchById(userId);
								System.out.println(temp);
							} else {
								System.out.println("Customer with user id : " + userId + " does not exist");
							}

							break;

						case 2:
							//approve and deny accounts
							if(employeeDAO.approveAccount(customerAccount)){
								System.out.println("Account has been approved");
					            }else {
					            	System.out.println("Account was not approved");
					            }

							break;

						case 3:
							//view all transactions
							System.out.println(employeeDAO.showTransactions(employee));
							break;
							//exit
						case 7:
							System.out.println("Thank you for visiting The Bread Bank!");
							System.exit(0);

						default:
							break;
						}

					}

				}
				break;

			case 3:

				// new user page
				System.out.println("Create Account (Press C for Customer and E for Employee) ");
				accountType = scanner.next();

				// check if username exists
				if (loginDAO.loginExists(username)) {
					System.out.println("This username already exists please try with another username");
					continue;
				} else {
					BankAccount customer = new BankAccount();
					loginDAO.addCustomer(customerAccount);
					System.out.println("Registration successful, pending account approval");
					continue;
				}
			case 7:
				System.out.println("Thank you for visiting The Bread Bank!");
				System.exit(0);

			default:
				System.out.println("Invalid choice ");

			}

		}

	}

}
