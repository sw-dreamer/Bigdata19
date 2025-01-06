package contact.lib;

import java.io.*;
import java.util.HashMap;

public class ReadWriteToBinary {
	private static final String DIRECTORY_PATH = "C:\\Temp\\write_and_read_binary";
	private static final String FILENAME = "C:\\Temp\\write_and_read_binary\\hashmap_data.bin";
	static File directory = new File(DIRECTORY_PATH);

	@SuppressWarnings("unchecked")
	public HashMap<String, PersonInfo> readFile() {
		HashMap<String, PersonInfo> map = new HashMap<String, PersonInfo>();
		checkDirectory();
		try (FileInputStream fileIn = new FileInputStream(FILENAME);
				ObjectInputStream ois = new ObjectInputStream(fileIn)) {
			map = (HashMap<String, PersonInfo>) ois.readObject();
			System.out.println("파일에서 데이터를 성공적으로 읽었습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다. 새로 시작합니다.");
			map = new HashMap<>();
		} catch (IOException e) {
			System.err.println("파일 읽기 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("파일 읽기 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	public void writeFile(HashMap<String, PersonInfo> temp) {
		checkDirectory();
		try (FileOutputStream fileOut = new FileOutputStream(FILENAME);
				ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {
			oos.writeObject(temp);
		} catch (IOException e) {
			System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void checkDirectory() {
		if (!directory.exists()) {
			boolean created = directory.mkdirs();
			if (!created) {
				System.out.println("디렉토리 생성에 실패했습니다.");
			} else {
				System.out.println("디렉토리가 생성되었습니다.");
			}
		}
	}
}
