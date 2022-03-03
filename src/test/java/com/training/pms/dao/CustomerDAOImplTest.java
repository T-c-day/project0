package com.training.pms.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerDAOImplTest {

	CustomerDAO customerDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		customerDAO = new CustomerDAOImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUserIdExists() {
		int userId = 5;
		boolean actual = customerDAO.userIdExists(userId);
		assertTrue(actual);
	}
	
	@Test
	void testUserIdExists2() {
		int userId = 4;
		boolean actual = customerDAO.userIdExists(userId);
		assertFalse(actual);
	}
	
	@Test
	void testUserIdExists3() {
		int userId = 100;
		boolean actual = customerDAO.userIdExists(userId);
		assertFalse(actual);
	}

}
