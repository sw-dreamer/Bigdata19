package contactMVC.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import contactMVC.db.DBDelete;
import contactMVC.db.DBInsert;
import contactMVC.db.DBSelect;
import contactMVC.db.DBUpdate;
import contactMVC.dto.PersonDTO;
import contactMVC.view.printResult;

public class ChangePersonDB implements ChangePersonService{

	DBSelect dbSelect = new DBSelect();
	DBInsert dbInsert = new DBInsert();
	DBDelete dbDelete = new DBDelete();
	DBUpdate dbUpdate = new DBUpdate();
	printResult printresult = new printResult();

	private String[] info(String check, Scanner scanner) {
		String[] arraystring = new String[4];
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
			arraystring[0] = name;
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
				ArrayList<PersonDTO> result = dbSelect.selectByPhoneNumber(phoneNumber);
				if (result.size() > 0) {
					System.out.println("전화번호가 중복되어있습니다.");
					printresult.printPersonList(result);
					continue;
				} else {
					arraystring[1] = phoneNumber;
					break;
				}
			} else if (check.equals("update")) {
				ArrayList<PersonDTO> result = dbSelect.selectByPhoneNumber(phoneNumber);
				if (result.size() > 0 && !result.get(0).getName().equals(name)) {
					System.out.println("전화번호가 중복되어있습니다.");
					printresult.printPersonList(result);
					continue;
				} else {
					arraystring[1] = phoneNumber;
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
			} else {
				arraystring[2] = address;
				break;
			}
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
				arraystring[3] = relationship;
				break;
			}
		}

		return arraystring;

	}

	public void searchPerson(Scanner scanner) {
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
					ArrayList<PersonDTO> result = dbSelect.selectByName(name);
					printresult.printPersonList(result);
				}
				break;
			case 2:
				System.out.println("조회하고 싶은 회원의 전화번호를 입력하세요.");
				String phoneNumber = scanner.nextLine();
				if (phoneNumber.isEmpty()) {
					System.out.println("공백을 입력하였습니다");
				} else {
					ArrayList<PersonDTO> result = dbSelect.selectByPhoneNumber(phoneNumber);
					printresult.printPersonList(result);
				}
				break;
			default:
				System.out.println("잘못 입력하였습니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("정수로 입력하셔야합니다.");
		}
	}

	@Override
	public void addPerson(Scanner scanner) {
		String[] personInfo = info("insert", scanner);
		dbInsert.insertPerson(personInfo[0], personInfo[1], personInfo[2], personInfo[3]);
	}

	@Override
	public void deletePerson(String name, Scanner scanner) {
		int addOneNumber = 0;
		int number;
		ArrayList<PersonDTO> personInfo = dbSelect.selectByName(name);
		if (personInfo.isEmpty()) {
			System.out.println("해당 이름으로 등록된 사용자가 없습니다.");
		} else {
			addOneNumber = personInfo.size();
			try {
				if (addOneNumber == 1) {
					printresult.printPersonList(personInfo);
					while (true) {
						System.out.print("삭제하시겠습니까? (예/아니오): ");
						String check = scanner.nextLine();
						if (check.equals("예")) {
							dbDelete.deletePerson(name);
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
						PersonDTO person = personInfo.get(i);
						System.out.println((i + 1) + ": " + person.toString());
					}
					while (true) {
						System.out.print("번호를 입력하세요 >");
						number = Integer.parseInt(scanner.nextLine());
						if (number > addOneNumber || number < 0) {
							System.out.println("잘못된 번호를 입력하였습니다.");
							continue;
						} else {
							PersonDTO selectedPerson = personInfo.get(number - 1);
							System.out.println("선택한 사용자: " + selectedPerson.toString());
							while (true) {
								System.out.print("삭제하시겠습니까? (예/아니오): ");
								String check = scanner.nextLine();
								if (check.equals("예")) {
									dbDelete.deletePerson(selectedPerson.getName(), selectedPerson.getPhoneNumber());
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

	@Override
	public void changePersonInfo(String name, Scanner scanner) {
		int addOneNumber = 0;
		int number;

		System.out.print("수정하고 싶은 사용자의 이름을 입력하세요 > ");
		name = scanner.nextLine();
		ArrayList<PersonDTO> personInfo = dbSelect.selectByName(name);
		if (personInfo.isEmpty()) {
			System.out.println("해당 이름으로 등록된 사용자가 없습니다.");
			return;
		} else {
			addOneNumber = personInfo.size();

			if (addOneNumber == 1) {
				printresult.printPersonList(personInfo);
				while (true) {
					System.out.print("수정하시겠습니까? (예/아니오): ");
					String check = scanner.nextLine();
					if (check.equals("예")) {
						String[] personIn = info("update", scanner);
						dbUpdate.updatePersonByName(personIn[0], personIn[1], personIn[2], personIn[3], name);
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
					PersonDTO person = personInfo.get(i);
					System.out.println((i + 1) + ": " + person.toString());
				}
				try {
					while (true) {
						System.out.print("번호를 입력하세요 >");
						number = Integer.parseInt(scanner.nextLine());
						if (number > addOneNumber || number < 0) {
							System.out.println("잘못된 번호를 입력하였습니다.");
							continue;
						} else {
							PersonDTO selectedPerson = personInfo.get(number - 1);
							System.out.println("선택한 사용자: " + selectedPerson.toString());
							while (true) {
								System.out.print("수정하시겠습니까? (예/아니오): ");
								String check = scanner.nextLine();
								if (check.equals("예")) {
									String[] personIn = info("update", scanner);
									dbUpdate.updatePersonByNameAndPhone(personIn[0], personIn[1], personIn[2],
											personIn[3], name, selectedPerson.getPhoneNumber());
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

	@Override
	public ArrayList<PersonDTO> printContactList() {
		DBSelect dbselect = new DBSelect();
		return dbselect.select();
	}

	@Override
	public HashMap<String, PersonDTO> getHashList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putHashList(HashMap<String, PersonDTO> personList) {
		// TODO Auto-generated method stub
		
	}


}
