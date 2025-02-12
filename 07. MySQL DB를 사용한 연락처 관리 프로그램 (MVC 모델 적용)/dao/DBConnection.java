package contactMVC.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBConnection {
	private static final String url = "jdbc:mysql://172.24.32.100:3306/contact?serverTimezone=Asia/Seoul";
	private static final String user = "root";
	private static final String password = "rootpassword";
	private static Connection connection = null;
	private static PreparedStatement pstmt = null;
	
	@SuppressWarnings("finally")
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스를 찾을 수 없습니다");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return connection;
		}
	}

	public static void closeConnection() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}