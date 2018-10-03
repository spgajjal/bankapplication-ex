package com.cap.bankapplication.services;

import com.cap.bankapplication.exceptions.AccountNotFoundException;
import com.cap.bankapplication.exceptions.InsufficientAccountBalanceException;
import com.cap.bankapplication.exceptions.NegativeAmountException;

public interface BankAccountService {
	

	public double getBalance(long accountId);
	public double withdraw(long accountId, double amount);
	public double deposit(long accountId, double amount);
	public boolean fundTransfer(long fromAcc, long toAcc, double amount) throws InsufficientAccountBalanceException,AccountNotFoundException, NegativeAmountException;


}
