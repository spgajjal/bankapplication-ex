package com.cap.bankapplication.services;

import com.cap.bankapplication.entities.Customer;
import com.cap.bankapplication.exceptions.AccountNotFoundException;

public interface CustomerService {
	public Customer authenticate(Customer customer) throws AccountNotFoundException;
	public Customer updateProfile(Customer customer);
	public boolean updatePassword(Customer customer,String newPassword,String oldPassword);

}
