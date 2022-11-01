package com.kopo.shoppingMall;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	/** 테이블 생성 */
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
	
	/** 로그인 */
	@PostMapping("/login_action")
	public String login_action(@ModelAttribute User user, HttpServletRequest request) {
		
		// step 1 : 프론트에서 전달받은 user 정보 변수 저장
		String userId = user.getId();
		System.out.println("userId : " + userId);
		String userPw = user.getPwd();
		System.out.println("userPwd : " + userPw);
		
		// step 2 : 유저 정보있는지 DB 조회
		DB db = new DB("tb_user");
		boolean isSuccess = db.confirmUser(userId, userPw);
		String resultMsg = "";
		
		// step 3 : 유저 정보가 있을 경우 유저 유형과 이름 가져오기
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
	
	/** 로그아웃 */
	@GetMapping("/logout_action")
	public String logout_action(HttpServletRequest request) {
		request.getSession().invalidate();
		return "로그아웃 되었습니다.";
	}
	
	/** 회원가입시 아이디 중복체크 */
	@PostMapping("/check_duplicate_userid_action")
	public String check_id_action(@ModelAttribute User user) {
		
		// step 1 : 프론트에서 전달받은 user 정보 변수 저장
		String userId = user.getId();
		System.out.println("userId : " + userId);
		
		// step 2 : 동일한 아이디가 있는지 DB 조회
		DB db = new DB("tb_user");
		boolean isSuccess = db.checkDuplicateUserId(userId);
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
