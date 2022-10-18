package com.kopo.dbtest;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	// REST Api Controller
	// db 접근할 수 있는 dba
	@GetMapping("/create_table")
	public HashMap<String, String> hello() {
		HashMap<String, String> result = new HashMap<String, String>();
		
		DB db = new DB();
		boolean isSuccess = db.createTable();
		
		if (isSuccess) {
			result.put("message", "success created");
		} else {
			result.put("message", "fail");
		}
		
		return result;
	}
	
	// login_action
	@PostMapping("/login_action")
	public String loginAction(Model model, HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		
		DB db = new DB();
		boolean isSuccess = db.loginAction(userId, userPw);
		String result = "";
		
		if (isSuccess) {
			result = "로그인 성공";
			return result;
		} else {
			result = "로그인 실패";
			return result;
		}
		
		
	}
}
