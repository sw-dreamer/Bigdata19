package contactMVC.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import contactMVC.dto.PersonDTO;
import contactMVC.view.printResult;

public class DBInsert {

	public void insertPerson(String name, String phoneNumber, String address, String relationNo) {
		DBSelect dbSelect = new DBSelect();
		printResult printPerson = new printResult();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO peopleT (name, phoneNumber, address, relationNo) VALUES (?,?,?, ?)";
		try {
			conn = DBConnection.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phoneNumber);
			pstmt.setString(3, address);
			pstmt.setString(4, relationNo);
			int flag = pstmt.executeUpdate();
			
			if (flag == 1) {
				System.out.println("정상추가");
				conn.commit();
				System.out.print("추가된 정보 > ");
				ArrayList<PersonDTO> result = dbSelect.selectByNamePhone(name, phoneNumber);
				printPerson.printPersonList(result);
			} else {
				System.out.println("에러 발생!");
				conn.rollback();
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Error!");
		} finally {
			DBConnection.closeConnection();
		}
	}
}
