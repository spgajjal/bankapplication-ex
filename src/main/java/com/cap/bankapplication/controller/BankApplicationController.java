package com.cap.bankapplication.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cap.bankapplication.entities.Customer;
import com.cap.bankapplication.services.BankAccountService;

@Controller
public class BankApplicationController {
	
@Autowired
private BankAccountService bankservice;

@RequestMapping(value = "/transferMoneyPage", method = RequestMethod.GET)
public String getTransferMoneyPage() {
	return "transferMoney";
}

@RequestMapping(value = "/transferMoney", method = RequestMethod.POST)
public String getTransferMoney(Model model,HttpSession session,@RequestParam("toAccountId") long toAccount,@RequestParam("amount") double amount) {
	try {
		Customer c=(Customer)session.getAttribute("customer") ;
		long fromAccountId=c.getAccount().getAccountId() ;
		if(bankservice.fundTransfer(fromAccountId, toAccount, amount))
		{
			System.out.println("hello");
			Customer customer=(Customer) session.getAttribute("customer") ;
			customer.getAccount().setBalance(bankservice.getBalance(customer.getAccount().getAccountId()));
		}
		
	}
	catch(Exception e){
		model.addAttribute("exception", e) ;
		return "errorMessages" ;
		 
	}
	
	return "dashboard" ;
}

	

}
