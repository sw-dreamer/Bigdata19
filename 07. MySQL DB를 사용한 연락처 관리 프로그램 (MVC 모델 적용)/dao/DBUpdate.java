package contactMVC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import contactMVC.dto.Contact;

public class DBUpdate {
	public void updatePersonByName(Contact newContact, String name) {
		DBSelect dbSelect = new DBSelect();
		String sql = "UPDATE peopleT SET name = ?, phoneNumber = ?, address = ?, relationNo = ? WHERE name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<Contact> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("수정할 사람이 존재하지 않습니다.");
				return;
			} else {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newContact.getName());
				pstmt.setString(2, newContact.getPhone());
				pstmt.setString(3, newContact.getAddress());
				pstmt.setString(4, newContact.getRelation());
				pstmt.setString(5, name);
				int flag = pstmt.executeUpdate();
				if (flag > 0) {
					System.out.println(newContact.getName() + "의 정보가 성공적으로 업데이트되었습니다.");
					conn.commit();
				} else {
					System.out.println(name + "의 정보 업데이트에 실패했습니다.");
					conn.rollback();
				}
			}

		} catch (SQLException e) {
			System.out.println("SQL 오류 발생: " + e.getMessage());
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException 발생: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}

	public void updatePersonByPhone(Contact newContact, String Phone) {
		DBSelect dbSelect = new DBSelect();
		String sql = "UPDATE peopleT SET name = ?, phoneNumber = ?, address = ?, relationNo = ? WHERE phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<Contact> result = dbSelect.selectByPhoneNumber(Phone);
			if (result.isEmpty()) {
				System.out.println("수정할 사람이 존재하지 않습니다.");
				return;
			} else {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newContact.getName());
				pstmt.setString(2, newContact.getPhone());
				pstmt.setString(3, newContact.getAddress());
				pstmt.setString(4, newContact.getRelation());
				pstmt.setString(5, Phone);
				System.out.println(pstmt);
				int flag = pstmt.executeUpdate();
				if (flag > 0) {
					System.out.println(newContact.getName() + "의 정보가 성공적으로 업데이트되었습니다.");
					conn.commit();
				} else {
					System.out.println(newContact.getName() + "의 정보 업데이트에 실패했습니다.");
					conn.rollback();
				}
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException 발생: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}

	public void updatePersonByNameAndPhone(Contact newContact, String name, String phoneNumber) {
		DBSelect dbSelect = new DBSelect();
		String sql = "UPDATE peopleT SET name = ?, phoneNumber = ?, address = ?, relationNo = ? WHERE name = ? and phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<Contact> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("수정할 사람이 존재하지 않습니다.");
				return;
			} else {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newContact.getName());
				pstmt.setString(2, newContact.getPhone());
				pstmt.setString(3, newContact.getAddress());
				pstmt.setString(4, newContact.getRelation());
				pstmt.setString(5, name);
				pstmt.setString(6, phoneNumber);
				int flag = pstmt.executeUpdate();
				if (flag > 0) {
					System.out.println(newContact.getName() + "의 정보가 성공적으로 업데이트되었습니다.");
					conn.commit();
				} else {
					System.out.println(name + "의 정보 업데이트에 실패했습니다.");
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					e.printStackTrace();
				}
			}
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException 발생: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}
}