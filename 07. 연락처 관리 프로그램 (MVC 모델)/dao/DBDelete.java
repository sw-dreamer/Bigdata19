package contactMVC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import contactMVC.dto.Contact;

public class DBDelete {

	public void deletePersonByName(String name) {

		DBSelect dbSelect = new DBSelect();

		String sql = "DELETE FROM peopleT WHERE name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<Contact> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("삭제할 사람이 존재하지 않습니다.");
				return;
			}
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);

			int flag = pstmt.executeUpdate();
			if (flag > 0) {
				System.out.println(name + "의 정보가 성공적으로 삭제되었습니다.");
				conn.commit();
			} else {
				System.out.println(name + "의 정보 삭제에 실패했습니다.");
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}

	}
	
	public void deletePersonbyPhone(String phone) {

		DBSelect dbSelect = new DBSelect();

		String sql = "DELETE FROM peopleT WHERE phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<Contact> result = dbSelect.selectByPhoneNumber(phone);
			if (result.isEmpty()) {
				System.out.println("삭제할 사람이 존재하지 않습니다.");
				return;
			}
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, phone);

			int flag = pstmt.executeUpdate();
			if (flag > 0) {
				System.out.println(result.get(0).getName() + "의 정보가 성공적으로 삭제되었습니다.");
				conn.commit();
			} else {
				System.out.println(result.get(0).getName() + "의 정보 삭제에 실패했습니다.");
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}

	}
	public void deletePersonByNamePhone(String name, String phoneNumber) {
		DBSelect dbSelect = new DBSelect();

		String sql = "DELETE FROM peopleT WHERE name = ? and phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<Contact> result = dbSelect.selectByNamePhone(name, phoneNumber);
			if (result.isEmpty()) {
				System.out.println("삭제할 사람이 존재하지 않습니다.");
				return;
			}
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setString(2, phoneNumber);

			int flag = pstmt.executeUpdate();
			if (flag > 0) {
				System.out.println(name +"의 사용자 전화번호"+ phoneNumber+ " 정보가 성공적으로 삭제되었습니다.");
				conn.commit();
			} else {
				System.out.println(name +"의 사용자 전화번호"+ phoneNumber+ " 정보의 삭제가 실패하였습니다.");
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}
}