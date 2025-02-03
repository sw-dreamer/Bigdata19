package contactMVC.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import contactMVC.dto.PersonDTO;
import contactMVC.view.printResult;

public class DBDelete {

	public void deletePerson(String name) {

		DBSelect dbSelect = new DBSelect();
		printResult printPerson = new printResult();

		String sql = "DELETE FROM peopleT WHERE name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<PersonDTO> result = dbSelect.selectByName(name);
			if (result.isEmpty()) {
				System.out.println("삭제할 사람이 존재하지 않습니다.");
				return;
			}
			printPerson.printPersonList(result);
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

	public void deletePerson(String name, String phoneNumber) {
		DBSelect dbSelect = new DBSelect();
		printResult printPerson = new printResult();

		String sql = "DELETE FROM peopleT WHERE name = ? and phoneNumber = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();
			ArrayList<PersonDTO> result = dbSelect.selectByNamePhone(name, phoneNumber);
			if (result.isEmpty()) {
				System.out.println("삭제할 사람이 존재하지 않습니다.");
				return;
			}
			printPerson.printPersonList(result);
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
