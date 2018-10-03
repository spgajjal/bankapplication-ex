package com.cap.bankapplication.controller;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControl {
	@ExceptionHandler(value=AccountNotFoundException.class)
	public String accountNotFound(HttpServletRequest request, AccountNotFoundException exception, Model model) {
		System.out.println(exception);
		
		return "wrongCredentials";

}
	}
