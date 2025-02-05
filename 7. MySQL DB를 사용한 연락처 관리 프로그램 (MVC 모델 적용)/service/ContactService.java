package contactMVC.service;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import contactMVC.dto.Contact;
import contactMVC.dao.DataAccessOjbect;
import contactMVC.dao.DataAccessOjbectInterface;

public class ContactService {

	DataAccessOjbectInterface doi = new DataAccessOjbect();

	private Contact info(String check, Scanner scanner) {
		String name;
		String phoneNumber;
		String address;
		String relationship;
		Pattern phoneNumberPattern = Pattern.compile("^010[0-9]*$");

		while (true) {
			System.out.print("▶ 이름을 입력해주세요 >");
			name = scanner.nextLine();
			if (name.isEmpty()) {
				System.out.println("공백이면 안됩니다.");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("▶ 전화번호를 입력해주세요 >");
			phoneNumber = scanner.nextLine();
			Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
			if (phoneNumber.isEmpty()) {
				System.out.println("공백이면 안됩니다.");
				continue;
			} else if (!matcher.matches()) {
				System.out.println("010으로 시작하는 숫자만 입력 가능합니다.");
				continue;
			} else if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
				System.out.println("10자리 또는 11자리로 입력해주세요.");
				continue;
			} else if (check.equals("insert")) {
				ArrayList<Contact> result = doi.selectByPhone(phoneNumber);
				if (result.size() > 0) {
					System.out.println("전화번호가 중복되어있습니다.");
					continue;
				} else {
					break;
				}
			} else if (check.equals("update")) {
				ArrayList<Contact> result = doi.selectByPhone(phoneNumber);
				if (result.size() > 0 && !result.get(0).getName().equals(name)) {
					System.out.println("전화번호가 중복되어있습니다.");
					continue;
				} else {
					break;
				}
			}
		}

		while (true) {
			System.out.print("▶ 주소 > ");
			address = scanner.nextLine();
			if (address.isBlank()) {
				System.out.println("주소는 빈칸이면 안됩니다.");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("▶ 관계 (가족, 친구, 기타 중에서 입력하세요) > ");
			relationship = scanner.nextLine();
			if (!relationship.equals("가족") && !relationship.equals("친구") && !relationship.equals("기타")) {
				System.out.println("가족, 친구, 기타 중에서 입력하세요.");
				continue;
			} else {
				if (relationship.equals("가족")) {
					relationship = "1";
				} else if (relationship.equals("친구")) {
					relationship = "2";
				} else if (relationship.equals("기타")) {
					relationship = "3";
				}
				break;
			}
		}

		return new Contact(name, phoneNumber, address, relationship);
	}

	public ArrayList<Contact> searchAll() {
		return doi.select();
	}

	public ArrayList<Contact> searchByName(String name) {
		return doi.selectByName(name);
	}

	public ArrayList<Contact> searchBy(Scanner scanner) {
		ArrayList<Contact> result = null; 
		ArrayList<Contact> a = null; 
		try {
			System.out.println("이름으로 검색을 하고 싶으면 1번, 전화번호로 검색하고 싶으면 2번을 선택해주세요");
			int number = Integer.parseInt(scanner.nextLine());


			switch (number) {
			case 1:
				System.out.println("조회하고 싶은 회원의 이름을 입력하세요.");
				String name = scanner.nextLine();
				if (name.isEmpty()) {
					System.out.println("공백을 입력하였습니다");
				} else {
					result = doi.selectByName(name);
				}
				break;
			case 2:
				System.out.println("조회하고 싶은 회원의 전화번호를 입력하세요.");
				String phoneNumber = scanner.nextLine();
				if (phoneNumber.isEmpty()) {
					System.out.println("공백을 입력하였습니다");
				} else {
					result = doi.selectByPhone(phoneNumber);
				}
				break;
			default:
				System.out.println("잘못 입력하였습니다.");
				break;
			}
			if (result != null) {
				a = result;
			} else {
				System.out.println("결과가 없습니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("정수로 입력하셔야합니다.");
		}
		return a;
	}

	public void addPerson(Scanner scanner) {
		doi.insert(info("insert", scanner));
	}

	public void deletePerson(String name, Scanner scanner) {
		int addOneNumber = 0;
		int number;
		System.out.print("삭제하고 싶은 사용자의 이름을 입력하세요 > ");
		name = scanner.nextLine();
		ArrayList<Contact> personInfo = doi.selectByName(name);
		if (personInfo.isEmpty()) {
			System.out.println("해당 이름으로 등록된 사용자가 없습니다.");
			return;
		} else {
			addOneNumber = personInfo.size();
			try {
				if (addOneNumber == 1) {
					System.out.println(doi.selectByName(name));
					while (true) {
						System.out.print("삭제하시겠습니까? (예/아니오): ");
						String check = scanner.nextLine();
						if (check.equals("예")) {
							doi.delete(personInfo.get(0).getPhone());
							break;
						} else if (check.equals("아니요")) {
							System.out.println("삭제가 취소되었습니다.");
							break;
						} else {
							System.out.println("(예 또는 아니요)를 입력하세요");
							continue;
						}
					}
				} else {
					for (int i = 0; i < personInfo.size(); i++) {
						System.out.println((i + 1) + ": " + personInfo.get(i).toString());
					}
					while (true) {
						System.out.print("번호를 입력하세요 >");
						number = Integer.parseInt(scanner.nextLine());
						if (number > addOneNumber || number < 0) {
							System.out.println("잘못된 번호를 입력하였습니다.");
							continue;
						} else {
							Contact selectedPerson = personInfo.get(number - 1);
							System.out.println("선택한 사용자: " + selectedPerson.toString());
							while (true) {
								System.out.print("삭제하시겠습니까? (예/아니오): ");
								String check = scanner.nextLine();
								if (check.equals("예")) {
									doi.delete(selectedPerson.getPhone());
									System.out.println("사용자가 삭제되었습니다.");
									break;
								} else if (check.equals("아니요")) {
									System.out.println("삭제가 취소되었습니다.");
									break;
								} else {
									System.out.println("(예 또는 아니요)를 입력하세요");
									continue;
								}
							}
							break;
						}
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("잘못 입력하였습니다 정수를 입력하세요.");
			}
		}
	}

	public void changePerson(String name, Scanner scanner) {
		int addOneNumber = 0;
		int number;
		System.out.print("수정하고 싶은 사용자의 이름을 입력하세요 > ");
		name = scanner.nextLine();
		ArrayList<Contact> personInfo = doi.selectByName(name);
		if (personInfo.isEmpty()) {
			System.out.println("해당 이름으로 등록된 사용자가 없습니다.");
			return;
		} else {
			addOneNumber = personInfo.size();

			if (addOneNumber == 1) {
				System.out.println(doi.selectByName(name));

				while (true) {
					System.out.print("수정하시겠습니까? (예/아니오): ");
					String check = scanner.nextLine();
					if (check.equals("예")) {
						Contact personIn = info("update", scanner);
						doi.update(personIn, personInfo.get(0).getPhone());
						break;
					} else if (check.equals("아니요")) {
						System.out.println("수정이 취소되었습니다.");
						break;
					} else {
						System.out.println("(예 또는 아니요)를 입력하세요");
						continue;
					}
				}
			} else {
				for (int i = 0; i < personInfo.size(); i++) {
					System.out.println((i + 1) + ": " + personInfo.get(i).toString());
				}
				try {
					while (true) {
						System.out.print("번호를 입력하세요 >");
						number = Integer.parseInt(scanner.nextLine());
						if (number > addOneNumber || number < 0) {
							System.out.println("잘못된 번호를 입력하였습니다.");
							continue;
						} else {
							Contact selectedPerson = personInfo.get(number - 1);
							System.out.println("선택한 사용자: " + selectedPerson.toString());
							while (true) {
								System.out.print("수정하시겠습니까? (예/아니오): ");
								String check = scanner.nextLine();
								if (check.equals("예")) {
									Contact personIn = info("update", scanner);
									doi.update(personIn, selectedPerson.getPhone());
									System.out.println("사용자가 수정되었습니다.");
									break;
								} else if (check.equals("아니요")) {
									System.out.println("수정이 취소되었습니다.");
									break;
								} else {
									System.out.println("(예 또는 아니요)를 입력하세요");
									continue;
								}
							}
							break;
						}

					}
				} catch (NumberFormatException e) {
					System.out.println("잘못 입력하였습니다 정수를 입력하세요.");
				}

			}

		}
	}

}
