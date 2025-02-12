package contactMVC.control;

import java.util.Scanner;

import contactMVC.service.*;
import contactMVC.view.ContactPrint;

public class ContactControl {
	ContactPrint cp = new ContactPrint();
	public void main() {
		Scanner scanner = new Scanner(System.in);
		ContactService cs = new ContactService();
		ContactPrint cp = new ContactPrint();
		String selection = "";
		boolean flag=true;
		do {
			cp.printMenu();
			selection = scanner.nextLine();
			switch(selection) {
			case "1":
				cp.printMemberList();
				cp.printPersonList(cs.searchAll());
				break;
			case "2":
				cp.printPersonList(cs.searchBy(scanner));
				break;
			case "3":
				cs.addPerson(scanner);
				break;
			case "4":
				System.out.println("수정할 회원의 이름을 입력하세요.");
				String changename = scanner.nextLine();
				cs.changePerson(changename, scanner);
				break;
			case "5":
				System.out.println("삭제할 회원의 이름을 입력하세요.");
				String deletename = scanner.nextLine();
				cs.deletePerson(deletename, scanner);
				break;
			default :
				System.out.println("프로그램 종료");
				flag=false;
			}
		}while(flag);
		
		scanner.close();
	}

}
