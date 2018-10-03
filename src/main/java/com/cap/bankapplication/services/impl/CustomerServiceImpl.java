package com.cap.bankapplication.services.impl;

import com.cap.bankapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cap.bankapplication.entities.Customer;
import com.cap.bankapplication.exceptions.AccountNotFoundException;
import com.cap.bankapplication.repository.CustomerRepository;
import com.cap.bankapplication.repository.impl.CustomerRepositoryImpl;
import com.cap.bankapplication.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepositoryImpl customerRepository;

	
	@Override
	public Customer authenticate(Customer customer) throws AccountNotFoundException {
		try {
			return customerRepository.authenticate(customer);
}
catch(DataAccessException e) {
AccountNotFoundException accountnotFound=new AccountNotFoundException("user doesnt exit..");
accountnotFound.initCause(e);

}
		return customer;

	}

	@Override
	public Customer updateProfile(Customer customer) {

		return customerRepository.updateProfile(customer);
	}

	@Override
	public boolean updatePassword(Customer customer, String newPassword, String oldPassword) {

		return customerRepository.updatePassword(customer, newPassword, oldPassword);
	}

}