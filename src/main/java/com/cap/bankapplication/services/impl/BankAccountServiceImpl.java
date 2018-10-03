package com.cap.bankapplication.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.bankapplication.services.BankAccountService;
import com.cap.bankapplication.exceptions.AccountNotFoundException;
import com.cap.bankapplication.exceptions.InsufficientAccountBalanceException;
import com.cap.bankapplication.exceptions.NegativeAmountException;
import com.cap.bankapplication.repository.BankAccountRepository;
import com.cap.bankapplication.repository.impl.BankAccountRepositortyImpl;
@Service
public class BankAccountServiceImpl implements BankAccountService {
	@Autowired
	private BankAccountRepositortyImpl bankAccountRepository;
	

	@Override
	public double getBalance(long accountId) {
		return bankAccountRepository.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount)  {
		double balance = bankAccountRepository.getBalance(accountId);
		if(balance != -1) {
			if(balance - amount >= 0)
				
					{
						bankAccountRepository.updateBalance(accountId, balance - amount);
						return bankAccountRepository.getBalance(accountId);
					}
				} 
				
		return balance;
	}

	@Override
	public double deposit(long accountId, double amount) {
		double balance = bankAccountRepository.getBalance(accountId);
		if(balance != -1)
			
				{
					bankAccountRepository.updateBalance(accountId, balance + amount);
					return bankAccountRepository.getBalance(accountId);
				}
			
		return balance;
	}
	@Override
	public boolean fundTransfer(long fromAcc, long toAcc, double amount)
			throws InsufficientAccountBalanceException, AccountNotFoundException, NegativeAmountException {
		boolean found = false;
		boolean balProblem = false;
		boolean negAmount = false;
		
		if(bankAccountRepository.getBalance(toAcc)==-1)
		{
			throw new AccountNotFoundException("The Account Id is incorrect!!");
		}
		if (bankAccountRepository.getBalance(fromAcc) < amount) {
			throw new InsufficientAccountBalanceException("Your account balance is low!!");
		}

		if (amount < 0) {
			throw new NegativeAmountException("You have entered negative amount");
		}

		if (withdraw(fromAcc, amount) != -1) {
			if (deposit(toAcc, amount) != -1) {
				found = true;
				return true;
			}

		}
		
		return false;
	}

}