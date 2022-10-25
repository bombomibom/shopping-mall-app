package com.kopo.shoppingMall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DB {
	private Connection connection = null; // 이곳에서만 쓸 것이기에 private
	private String dbTableName = "";
	
	DB(){
		
	}
	
	DB(String dbTableName){
		this.dbTableName = dbTableName;
	}
	
	private void open() {
		try {
			// sqlite
//			String dbUrl = "jdbc:sqlite:c:/Users/PC25/kopo/java/springBoot/DB/dbtest.db"; 
//			this.connection = DriverManager.getConnection(dbUrl);
			
			// mysql (heroku 업로드 후 사용 가능. 환경변수는!!! 처음부터 잡으면 null! 인식 못 함. 만약 heroku db만 사용할거면 url 전체 복붙. 그리고 한 번 remote 되면 계속 login 안 해도 됨! 환경변수!)
//			URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
//		    String username = jdbUri.getUserInfo().split(":")[0];
//		    String password = jdbUri.getUserInfo().split(":")[1];
//		    String port = String.valueOf(jdbUri.getPort());
//		    String dbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
//		    this.connection = DriverManager.getConnection(dbUrl, username, password);
			
			// postgres
//		    String dbUrl = "jdbc:postgresql://ec2-3-220-207-90.compute-1.amazonaws.com:5432/d6p4id6pb66qk5";
//		    String user = "xsknxoupkddwmw";
//		    String pwd = "451b3112bac4d33cde308027a02d0b693a968aaede0703bdd07c71c74313783d";
//		    this.connection = DriverManager.getConnection(dbUrl, user, pwd);
			
			// 현재 사용
			String dbUrl = "jdbc:mysql://cwe1u6tjijexv3r6.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/fd0uj083rj5uqmal";
		    String user = "ax7jggpvxa2b2m67";
		    String pwd = "h6n367dxmuvdqwji";
		    this.connection = DriverManager.getConnection(dbUrl, user, pwd);
			
		} catch (Exception e) {
			e.printStackTrace(); // 보통은 옆에 코드 많이 사용. 그런데 보안 측면에서 catch를 안 할 때도 있으니 참고
		}
	}
	
	private void close() {
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	// 테이블 생성
	public boolean createTable() {
		boolean isSuccess = false;
		this.open();
		try {
			Statement statement = connection.createStatement(); 
			// sqlite
			// 변수명은 사실 두 단 어 이상으로 만들자! or 아래처럼 백틱
//			String sqlString = "CREATE TABLE tb_user(`idx` INTEGER PRIMARY KEY AUTOINCREMENT, `user_type` TEXT, `user_id` TEXT, `user_password` TEXT, `user_name` TEXT, `user_phone` TEXT, `user_address` TEXT, `user_created` TEXT, `user_updated` TEXT)";
//			statement.execute(sqlString);
//			sqlString = "CREATE TABLE product(`idx` INTEGER PRIMARY KEY AUTOINCREMENT, `prd_name` TEXT, `prd_price` INTEGER, `prd_quantity` INTEGER, `prd_created` TEXT, `prd_updated` TEXT)";
//			statement.execute(sqlString);
//			sqlString = "INSERT INTO tb_user(`user_type`, `user_id`, `user_password`) VALUES ('admin', 'admin', 'a1234')";
//			statement.execute(sqlString);
//			statement.close();
			
			// mysql
			String sqlString = "CREATE TABLE tb_user(`idx` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `user_type` varchar(100), `user_id` varchar(100), `user_password` varchar(300), `user_name` varchar(100), `user_phone` varchar(20), `user_address` varchar(100), `user_created` datetime, `user_updated` datetime)";
			statement.execute(sqlString);
			sqlString = "CREATE TABLE product(`idx` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `prd_name` varchar(100), `prd_price` int, `prd_quantity` int, `prd_created` datetime, `prd_updated` datetime)";
			statement.execute(sqlString);
			sqlString = "INSERT INTO tb_user(`user_type`, `user_id`, `user_password`) VALUES ('admin', 'admin', 'a1234')";
			statement.execute(sqlString);
			statement.close();
			
			// postgres
//			String sqlString = "CREATE TABLE tb_user(idx SERIAL PRIMARY KEY, user_type varchar(100), user_id varchar(100), user_password varchar(300), user_name varchar(100), user_phone varchar(20), user_address varchar(100), user_created timestamp, user_updated timestamp)";
//			statement.execute(sqlString);
//			sqlString = "CREATE TABLE product(idx SERIAL PRIMARY KEY, prd_name varchar(100), prd_price int, prd_quantity int, prd_created timestamp, prd_updated timestamp)";
//			statement.execute(sqlString);
//			sqlString = "INSERT INTO tb_user(user_type, user_id, user_password) VALUES ('admin', 'admin', 'a1234')";
//			statement.execute(sqlString);
//			statement.close();
			
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return isSuccess;
	}
	
	// 로그인
	public boolean confirmUser(String userId, String userPw) {
		boolean isSuccess = false;
		this.open();
		try {
			String query = "SELECT * FROM " + this.dbTableName + " WHERE user_id = ? AND user_password = ?;";
			System.out.println(query);
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userPw);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				isSuccess = true;
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return isSuccess;
	}
	
	// 유저 정보 가져오기(user_type, user_name)
	public HashMap<String, String> getUserInfo(String userId) {
		this.open();
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			String query = "SELECT user_type, user_name FROM " + this.dbTableName + " WHERE user_id = ?;";
			System.out.println(query);
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				result.put("userType", resultSet.getString(1));
				result.put("userName", resultSet.getString(2));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return result;
	}
	
	// 아이디 중복체크
	public boolean checkUserId(String userId) {
		boolean isSuccess = false;
		this.open();
		try {
			String query = "SELECT * FROM " + this.dbTableName + " WHERE user_id = ?;";
			System.out.println(query);
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				isSuccess = true;
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return isSuccess;
	}
}
