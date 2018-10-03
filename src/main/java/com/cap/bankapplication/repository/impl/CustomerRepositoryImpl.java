package com.cap.bankapplication.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cap.bankapplication.entities.BankAccount;
import com.cap.bankapplication.entities.Customer;
import com.cap.bankapplication.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository{
@Autowired
private JdbcTemplate jdbcTemplate;
@Override
public Customer authenticate(Customer customer) throws DataAccessException
	{
	try {
		
	
customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id=? AND password=?",
			new Object[] { customer.getCustomerId(), customer.getPassword() }, new CustomerRowMapper());
	BankAccount account = jdbcTemplate.queryForObject(
			"SELECT * FROM  bankAccount WHERE account_Id =(SELECT account_Id FROM customer WHERE customer_id = ?)",
			new Object[] { customer.getCustomerId() }, new AccountRowMapper());
	customer.setAccount(account);
	return customer;
	}
	catch(DataAccessException e) {
	e.initCause(new EmptyResultDataAccessException("Expected 1 actual 0 ", 1));
	throw e;
}
	


}

@Override
public Customer updateProfile(Customer customer) {
	jdbcTemplate.update(
			"UPDATE customer SET location = ?,dob = ?,email=?,customer_name=?   WHERE customer_id = ?",
			new Object[] { customer.getAddress(), customer.getDateOfBirth(),
					customer.getEmail(), customer.getCustomerName(), customer.getCustomerId() });
	customer=jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id=?",new Object[] {customer.getCustomerId()},new CustomerRowMapper());
	return customer;		
}

@Override
public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
int count=jdbcTemplate.update("UPDATE customer SET password=? WHERE customer_id=? AND password=?",new Object[] { newPassword, customer.getCustomerId(), oldPassword});
return (count!=0)?true:false;

}
private class CustomerRowMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

		Customer customer = new Customer();
		customer.setCustomerId(rs.getLong(1));
		customer.setCustomerName(rs.getString(2));
		customer.setPassword(rs.getString(3));
		customer.setEmail(rs.getString(4));
		customer.setAddress(rs.getString(5));
		customer.setDateOfBirth(rs.getDate(6).toLocalDate());
		
		return customer;
	}
	
}

private class AccountRowMapper implements RowMapper<BankAccount>{

	@Override
	public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BankAccount account=new BankAccount();
		account.setAccountId(rs.getLong(1));
		account.setAccountType(rs.getString(2));
		account.setBalance(rs.getDouble(3));
		return account;
	}
	
}





}
