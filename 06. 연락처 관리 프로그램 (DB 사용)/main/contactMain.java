package contactMVC.main;

import java.util.Scanner;

import contactMVC.control.serviceControl;

public class contactMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String what = null;
		System.out.print("hashmap을 사용하고 싶으면 1번을 DB를 사용하고 싶으면 2번을 눌러주세요 > ");
		int selectnumber = Integer.parseInt(scanner.nextLine());
		if (selectnumber == 1) {
			what = "hashmap";
		} else if (selectnumber == 2) {
			what = "DB";
		}
		if (what.toLowerCase().equals("hashmap")) {
			serviceControl.contactControl("hashmap");
		} else if (what.toLowerCase().equals("db")) {
			serviceControl.contactControl("DB");
		} else {
			System.out.println("잘못 입력하였습니다.");
		}
		scanner.close();
	}
}
