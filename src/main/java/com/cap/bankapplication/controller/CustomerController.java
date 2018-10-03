package com.cap.bankapplication.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cap.bankapplication.entities.Customer;
import com.cap.bankapplication.exceptions.AccountNotFoundException;
import com.cap.bankapplication.services.BankAccountService;
import com.cap.bankapplication.services.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getindex() {
		return "iciciHome";
	}
	
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public String getHomePage() {
		return "iciciHome";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getlogin(Model model) {
		model.addAttribute("customer", new Customer());
		return "login";
	}
	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String getLogout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String getAuthentication(@ModelAttribute Customer customer, HttpSession session, Model model) throws AccountNotFoundException {
		customer = service.authenticate(customer);
		session.setAttribute("customer", customer);
		return "dashboard";
			/*try {
				customer = service.authenticate(customer);
				System.out.println("hello");
				session.setAttribute("customer", customer);
				System.out.println("hello");
				return "dashboard";
			}
			catch(Exception e)*/
			/*{
				return "wrongCredentials";
			}*/
			
			
		} 

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String getdashboard(Model model) {
		return "dashboard";
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	public String getupdatePassword(Model model) {
		model.addAttribute("customer", new Customer());
		return "updatepassword";
	}

	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String getUpdatePassword(HttpSession session,@RequestParam("newPassword") String newPassword,@RequestParam("oldPassword") String oldPassword) {
		Customer customer=(Customer) session.getAttribute("customer") ;
		if(!service.updatePassword(customer, newPassword, oldPassword))
		 {
			 return "/updatePasswordError" ;
		 }
		 else
		 {
			 customer.setPassword(newPassword);
			 session.setAttribute("customer", customer);
			 return "/updatePasswordSuccess" ;
		 }
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String geteditProfile(HttpSession session, Model model) {
		model.addAttribute("customer", session.getAttribute("customer"));
		return "editProfile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String getEditProfile(@ModelAttribute Customer customer, HttpSession session) {
		Customer newCustomer = (Customer) session.getAttribute("customer");
		customer.setAccount(newCustomer.getAccount());
		customer.setCustomerId(newCustomer.getCustomerId());
		customer.setPassword(newCustomer.getPassword());
		service.updateProfile(customer);
		session.setAttribute("customer", customer);
		return "editProfileSuccess";
	}
	
	
	
}