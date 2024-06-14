package com.exam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value={"/login"})
	public String showLoginPage() {
		
		return "loginForm";
	}
	
	@GetMapping(value={"/login_fail"})
	public String showLogin_failPage() {
		
		return "loginForm";
	}
	
	@GetMapping(value={"/login_success"})
	public String showLogin_successPage() {
		
		return "home";
	}
}
