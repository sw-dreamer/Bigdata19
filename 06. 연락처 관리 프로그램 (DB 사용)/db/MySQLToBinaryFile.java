package contactMVC.db;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import contactMVC.view.printResult;

public class MySQLToBinaryFile {
	DBSelect dbSelect = new DBSelect();
	printResult printPerson = new printResult();
	private static final String DIRECTORY_PATH = "C:\\Temp\\write_and_read_binary";
	private static final String FILE_NAME = "hashmap_data.bin"; 
	static File directory = new File(DIRECTORY_PATH);

	private static String FILENAME = DIRECTORY_PATH + "\\" + FILE_NAME;

	public void mysqltofile() {
		String sql = "select p.name, p.phoneNumber, p.address, r.relationship " + "from peopleT p "
				+ "inner join relationT r on r.relationNo = p.relationNo " + "order by p.name";
		
		String filePath = FILENAME;
		Connection conn = null;
		DataOutputStream dos = null;

		try {
			conn = DBConnection.getConnection();
			if (conn == null) {
				System.out.println("데이터베이스 연결 실패");
				return;
			}
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			dos = new DataOutputStream(new FileOutputStream(filePath));
			while (rs.next()) {
				String column1 = rs.getString(1);
				String column2 = rs.getString(2);
				String column3 = rs.getString(3);
				String column4 = rs.getString(4);

				dos.writeUTF(column1);
				dos.writeUTF(column2);
				dos.writeUTF(column3);
				dos.writeUTF(column4);
			}
			System.out.println("데이터가 바이너리 파일로 성공적으로 저장되었습니다.");
		} catch (SQLException e) {
			System.err.println("SQL 예외 발생: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("파일 처리 예외 발생: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				DBConnection.closeConnection();
				if (dos != null) {
					dos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
