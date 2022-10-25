package com.kopo.shoppingMall;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	// REST Api Controller
	// db 접근할 수 있는 dba
	@GetMapping("/create_table")
	public HashMap<String, String> create_table() {
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
	public String login_action(@ModelAttribute User user, HttpServletRequest request) {
		
		String userId = user.getId();
		System.out.println("userId : " + userId);
		String userPw = user.getPwd();
		System.out.println("userPwd : " + userPw);
		
		DB db = new DB("tb_user");
		boolean isSuccess = db.confirmUser(userId, userPw);
		String resultMsg = "";
		
		if (isSuccess) {
			HashMap<String, String> result = new HashMap<String, String>();
			result = db.getUserInfo(userId);
			
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("userType", result.get("userType"));
			request.getSession().setAttribute("userName", result.get("userName"));
			
			resultMsg = "로그인 성공";
			return resultMsg;
			
		} else {
			resultMsg = "아이디 또는 비밀번호가 없는 정보입니다.";
			return resultMsg;
		}
	}
	
	// logout_action
	@GetMapping("/logout_action")
	public String logout_action(HttpServletRequest request) {
		request.getSession().invalidate();
		return "로그아웃 성공";
	}
	
	// check_id_action
	@PostMapping("/check_id_action")
	public String check_id_action(@ModelAttribute User user) {
		
		String userId = user.getId();
		System.out.println("userId : " + userId);
		
		DB db = new DB("tb_user");
		boolean isSuccess = db.checkUserId(userId);
		String resultMsg = "";
		
		if (isSuccess) {
			resultMsg = "이미 존재하는 아이디입니다.";
			return resultMsg;
			
		} else {
			resultMsg = "사용 가능한 아이디입니다.";
			return resultMsg;
		}
	}
}
