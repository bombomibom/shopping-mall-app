package com.kopo.dbtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	// login page
	@GetMapping("/")
	public String root() {
		return "login";
	}
	
	// signup page
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	// mainList page
	@GetMapping("/mainList")
	public String mainList() {
		return "mainList";
	}
	
}
