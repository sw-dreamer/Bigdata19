package contactMVC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import contactMVC.dto.Contact;

public class DBSelect {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	public ArrayList<Contact> select() {
		ArrayList<Contact> peopleList = new ArrayList<>();
		String sql = "select																"
				+ "p.name, p.phoneNumber,p.address, r.relationship from peopleT p		"
				+ "inner join relationT r on r.relationNo =p.relationNo				"
				+ "order by p.name													";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String relationship = rs.getString("relationship");
				Contact pdto = new Contact(name, phoneNumber, address, relationship);
				peopleList.add(pdto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection Error");
		} finally {
			DBConnection.closeConnection();
		}
		return peopleList;
	}
	

	public ArrayList<Contact> selectByName(String findName) {
		ArrayList<Contact> personList = new ArrayList<>();
		String sql = "select p.name, p.phoneNumber, p.address, r.relationship from peopleT p "
				+ "inner join relationT r on r.relationNo = p.relationNo " + "where p.name = ?";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, findName);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String relationship = rs.getString("relationship");
				Contact pdto = new Contact(name, phoneNumber, address, relationship);
				personList.add(pdto);
			}

			return personList;

		} catch (SQLException e) {
			System.out.println("이름으로 검색 중 오류가 발생했습니다: " + findName);
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DBConnection.closeConnection();
		}
	}

	public ArrayList<Contact> selectByPhoneNumber(String phonNumber) {
		ArrayList<Contact> personList = new ArrayList<>();
		String sql = "select p.name, p.phoneNumber, p.address, r.relationship from peopleT p "
				+ "inner join relationT r on r.relationNo = p.relationNo " + "where p.phoneNumber = ?";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phonNumber);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String relationship = rs.getString("relationship");
				Contact pdto = new Contact(name, phoneNumber, address, relationship);
				personList.add(pdto);
			}

			return personList;

		} catch (SQLException e) {
			System.out.println("전화번호로 검색 중 오류가 발생했습니다: " + phonNumber);
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DBConnection.closeConnection();
		}
	}
	
	
	public ArrayList<Contact> selectByNamePhone(String findName, String phoneNo) {
		ArrayList<Contact> personList = new ArrayList<>();
		String sql = "select p.name, p.phoneNumber,p.address, r.relationship from peopleT p	"
				+ "inner join relationT r on r.relationNo =p.relationNo					"
				+ "where p.name = ?														"
				+ "and p.phoneNumber= ?														";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, findName);
			pstmt.setString(2, phoneNo);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String relationship = rs.getString("relationship");
				Contact pdto = new Contact(name, phoneNumber, address, relationship);
				personList.add(pdto);
			}

			return personList;

		} catch (SQLException e) {
			System.out.println("이름으로 검색 중 오류가 발생했습니다: " + findName);
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DBConnection.closeConnection();
		}

	}
}