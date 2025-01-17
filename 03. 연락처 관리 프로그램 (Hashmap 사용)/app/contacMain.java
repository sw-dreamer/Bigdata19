package contact.app;

import java.util.Scanner;
import contact.lib.*;

public class contacMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChangePerson changePerson = new ChangePerson();
		ReadWriteToBinary readWriteToBinary = new ReadWriteToBinary();
		changePerson.putHashList(readWriteToBinary.readFile());

		int number;
		String name = null;
		String phoneNo = null;
		String address = null;
		String relation = null;

		while (true) {
			System.out.println("┌────────────────────┐");
			System.out.println("│       list         │");
			System.out.println("├────────────────────┤");
			System.out.println("│  1. 사람 추가 하기  │");
			System.out.println("│  2. 사람 목록 보기  │");
			System.out.println("│  3. 사람 정보 수정  │");
			System.out.println("│  4. 사람 정보 삭제  │");
			System.out.println("│  5. 프로그램 종료   │");
			System.out.println("└────────────────────┘");
			System.out.println("번호를 선택하세요 > ");
			try {
				number = Integer.parseInt(scanner.next());
				scanner.nextLine();

				if (number > 5 || number < 1) {
					System.out.println("1번부터 5번 사이의 숫자를 입력하세요.");
				} else if (number == 5) {
					System.out.println("프로그램을 종료합니다.");
					readWriteToBinary.writeFile(changePerson.getHashList());
					break;
				} else if (number == 4) {
					System.out.println("삭제할 회원의 이름을 입력하세요.");
					name = scanner.nextLine();
					changePerson.deletePerson(name, scanner);

				} else if (number == 3) {
					System.out.println("수정할 회원의 이름을 입력하세요.");
					name = scanner.nextLine();
					changePerson.changePersonInfo(name, phoneNo, address, relation, scanner);

				} else if (number == 2) {
					System.out.println("┌─────────────────────────────────────────────────────┐");
					System.out.println("│                    회원 목록 출력                    │");
					System.out.println("└─────────────────────────────────────────────────────┘");
					changePerson.printContactList();

				} else if (number == 1) {
					changePerson.addPerson(name, phoneNo, address, relation, scanner);
				}

			} catch (NumberFormatException e) {
				System.out.println("정수를 입력해주세요!");
			}
		}
		scanner.close();
	}
}
