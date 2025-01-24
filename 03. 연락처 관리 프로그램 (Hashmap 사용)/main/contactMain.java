package contactMVC.main;

import java.util.Scanner;

import contactMVC.control.serviceControl;

public class contactMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("HashMap 또는 DB로 입력해주세요 > ");
		String what = scanner.nextLine();
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
