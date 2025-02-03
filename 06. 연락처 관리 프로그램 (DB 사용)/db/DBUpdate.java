package contactMVC.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import contactMVC.dto.PersonDTO;
import contactMVC.view.printResult;

public class DBUpdate {
	public void updatePersonByName(String newName, String phoneNumber, String address, String relationNo, String name) {
		DBSelect dbSelect = new DBSelect();
		printResult printPerson = new printResult();
		String sql = "UPDATE peopleT SET name = ?, phoneNumber = ?, address = ?, relationNo = ? WHERE name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<PersonDTO> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("수정할 사람이 존재하지 않습니다.");
				return;
			} else {
				printPerson.printPersonList(result);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newName);
				pstmt.setString(2, phoneNumber);
				pstmt.setString(3, address);
				pstmt.setString(4, relationNo);
				pstmt.setString(5, name);
				int flag = pstmt.executeUpdate();
				if (flag > 0) {
					System.out.println(newName + "의 정보가 성공적으로 업데이트되었습니다.");
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

	public void updatePersonByNameAndPhone(String newName, String newphoneNumber, String address, String relationNo,
			String name, String phoneNumber) {
		DBSelect dbSelect = new DBSelect();
		printResult printPerson = new printResult();
		String sql = "UPDATE peopleT SET name = ?, phoneNumber = ?, address = ?, relationNo = ? WHERE name = ? and phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<PersonDTO> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("수정할 사람이 존재하지 않습니다.");
				return;
			} else {
				printPerson.printPersonList(result);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newName);
				pstmt.setString(2, newphoneNumber);
				pstmt.setString(3, address);
				pstmt.setString(4, relationNo);
				pstmt.setString(5, name);
				pstmt.setString(6, phoneNumber);
				int flag = pstmt.executeUpdate();
				if (flag > 0) {
					System.out.println(newName + "의 정보가 성공적으로 업데이트되었습니다.");
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

	public void updatePhone(String newName, String newphoneNumber, String name, String phoneNumber) {
		DBSelect dbSelect = new DBSelect();
		printResult printPerson = new printResult();
		String sql = "UPDATE peopleT SET name = ?, phoneNumber = ? WHERE name = ? and phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<PersonDTO> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("수정할 사람이 존재하지 않습니다.");
				return;
			} else {
				printPerson.printPersonList(result);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newName);
				pstmt.setString(2, newphoneNumber);
				pstmt.setString(3, name);
				pstmt.setString(4, phoneNumber);
				int flag = pstmt.executeUpdate();
				if (flag > 0) {
					System.out.println(newName + "의 정보가 성공적으로 업데이트되었습니다.");
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

}
