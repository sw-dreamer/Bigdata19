package contactMVC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import contactMVC.dto.Contact;

public class DBInsert {

	public void insertPerson(Contact contact) {
		DBSelect dbSelect = new DBSelect();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO peopleT (name, phoneNumber, address, relationNo) VALUES (?,?,?, ?)";
		try {
			conn = DBConnection.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getAddress());
			pstmt.setString(4, contact.getRelation());
			int flag = pstmt.executeUpdate();
			
			if (flag == 1) {
				System.out.println("정상추가");
				conn.commit();
				System.out.print("추가된 정보 > ");
				System.out.println(dbSelect.selectByNamePhone(contact.getName(), contact.getPhone()));
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