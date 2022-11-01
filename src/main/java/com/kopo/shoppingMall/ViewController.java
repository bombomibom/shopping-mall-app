package com.kopo.shoppingMall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	/** 로그인 페이지 */
	@GetMapping("/")
	public String root() {
		return "login";
	}
	
	/** 회원가입 페이지 */ 
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	/** 메인 페이지 */
	@GetMapping("/main")
	public String mainList() {
		return "main";
	}
	
}
