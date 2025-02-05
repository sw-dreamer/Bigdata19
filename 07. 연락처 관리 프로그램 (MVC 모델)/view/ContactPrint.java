package contactMVC.view;

import java.util.ArrayList;

import contactMVC.dto.Contact;

public class ContactPrint {
	public void printMenu() {
		System.out.println("┌──────────────────┐");
		System.out.println("│       list       │");
		System.out.println("├──────────────────┤");
		System.out.println("│  1. 전체 목록 보기   │");
		System.out.println("│  2. 사람 정보 보기   │");
		System.out.println("│  3. 사람 추가 하기   │");
		System.out.println("│  4. 사람 정보 수정   │");
		System.out.println("│  5. 사람 정보 삭제   │");
		System.out.println("│  6. 프로그램 종료    │");
		System.out.println("└──────────────────┘");
		System.out.println("번호를 입결하세요 >");
	}

	public void printMemberList() {
		System.out.println("┌─────────────────────────────────────────────────────┐");
		System.out.println("│                     회원 목록 출력                      │");
		System.out.println("└─────────────────────────────────────────────────────┘");
	}
	
	
	public void printPersonList(ArrayList<Contact> personList) {
		if (personList.isEmpty()) {
			System.out.println("검색된 데이터가 없습니다.");
		} else {
			for (Contact person : personList) {
				System.out.print("이름: " + person.getName() + " 전화번호: " + person.getPhone() + " 주소: "
						+ person.getAddress() + " 관계: " + person.getRelation() + '\n');
			}
		}
	}
}
