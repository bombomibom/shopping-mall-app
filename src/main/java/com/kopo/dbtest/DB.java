package com.kopo.dbtest;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DB {
	private Connection connection = null; // 이곳에서만 쓸 것이기에 private
	
	private void open() {
		try {
			// sqlite
			//String dbUrl = "jdbc:sqlite:c:/Users/PC25/kopo/java/springBoot/DB/dbtest.db"; 
			//this.connection = DriverManager.getConnection(dbUrl);
			
			// mysql (heroku 업로드 후 사용 가능. 환경변수는!!! 처음부터 잡으면 null! 인식 못 함. 만약 heroku db만 사용할거면 url 전체 복붙. 그리고 한 번 remote 되면 계속 login 안 해도 됨! 환경변수!)
//			URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
//		    String username = jdbUri.getUserInfo().split(":")[0];
//		    String password = jdbUri.getUserInfo().split(":")[1];
//		    String port = String.valueOf(jdbUri.getPort());
//		    String dbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
//		    this.connection = DriverManager.getConnection(dbUrl, username, password);
			
			// postgres
		    String dbUrl = "jdbc:postgresql://ec2-3-220-207-90.compute-1.amazonaws.com:5432/d6p4id6pb66qk5";
		    String user = "xsknxoupkddwmw";
		    String pwd = "451b3112bac4d33cde308027a02d0b693a968aaede0703bdd07c71c74313783d";
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
	
	public boolean createTable() {
		boolean isSuccess = false;
		this.open();
		try {
			Statement statement = connection.createStatement(); 
			// sqlite
			// 변수명은 사실 두 단 어 이상으로 만들자! or 아래처럼 백틱
//			String sqlString = "CREATE TABLE user(`idx` INTEGER PRIMARY KEY AUTOINCREMENT, `type` TEXT, `id` TEXT, `pwd` TEXT, `name` TEXT, `phone` TEXT, `address` TEXT, `created` TEXT, `updated` TEXT)";
//			statement.execute(sqlString);
//			sqlString = "CREATE TABLE product(`idx` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `price` INTEGER, `quantity` INTEGER, `created` TEXT, `updated` TEXT)";
//			statement.execute(sqlString);
//			sqlString = "INSERT INTO user(`type`, `id`, `pwd`) VALUES ('admin', 'admin', 'a1234')";
//			statement.execute(sqlString);
//			statement.close();
			
			// mysql
//			String sqlString = "CREATE TABLE user(`idx` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `type` varchar(100), `id` varchar(100), `password` varchar(300), name varchar(100), `phone` varchar(20), `address` varchar(100), `created` datetime, `updated` datetime)";
//			statement.execute(sqlString);
//			sqlString = "CREATE TABLE product(`idx` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `name` varchar(100), `price` int, `quantity` int, `created` datetime, `updated` datetime)";
//			statement.execute(sqlString);
//			sqlString = "INSERT INTO user(`type`, `id`, `password`) VALUES ('admin', 'admin', 'a1234')";
//			statement.execute(sqlString);
//			statement.close();
			
			// postgres
			String sqlString = "CREATE TABLE user(`idx` SERIAL PRIMARY KEY, `type` varchar(100), `id` varchar(100), `password` varchar(300), name varchar(100), `phone` varchar(20), `address` varchar(100), `created` timestamp, `updated` timestamp)";
			statement.execute(sqlString);
			sqlString = "CREATE TABLE product(`idx` SERIAL PRIMARY KEY, name varchar(100), `price` int, `quantity` int, `created` timestamp, `updated` timestamp)";
			statement.execute(sqlString);
			sqlString = "INSERT INTO user(`type`, `id`, `password`) VALUES ('admin', 'admin', 'a1234')";
			statement.execute(sqlString);
			statement.close();
			
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return isSuccess;
	}
}
