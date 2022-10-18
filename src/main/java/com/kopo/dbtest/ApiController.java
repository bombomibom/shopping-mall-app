package com.kopo.dbtest;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
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
}
