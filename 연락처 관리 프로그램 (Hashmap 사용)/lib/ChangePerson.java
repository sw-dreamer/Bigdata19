package contact.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePerson {

	private HashMap<String, PersonInfo> personList = new HashMap<>();
	ReadWriteToBinary readWriteToBinary = new ReadWriteToBinary();

	public void addPerson(String name, String phoneNumber, String address, String relationship, Scanner scanner) {
		
		boolean checkIsDigit = false;
		Pattern phoneNumberPattern = Pattern.compile("^010[0-9]*$");
		if (personList.containsKey(phoneNumber)) {
			System.out.println(phoneNumber + "은 이미 존재합니다.");
		} else {
			System.out.println("회원의 정보를 입력하세요.");
			while (true) {
				System.out.print("▶ 이름 \n> ");
				name = scanner.nextLine();
				if (name.isBlank() == true) {
					System.out.println("이름은 공백이면 안됩니다.");
				} else {
					break;
				}
			}
			while (!checkIsDigit) {
				System.out.print("▶ 휴대폰 전화번호 \n(e.g. 01012345678) > ");
				phoneNumber = scanner.nextLine();
				Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
				if (!matcher.matches()) {
					System.out.println("010으로 시작하는 숫자만 입력 가능합니다.");
					checkIsDigit = false;
					continue;
				} else if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
					System.out.println("10자리 또는 11자리로 입력해주세요.");
					checkIsDigit = false;
					continue;
				} else if (personList.containsKey(phoneNumber)) {
					System.out.println("중복된 값입니다.");
					checkIsDigit = false;
				} else {
					checkIsDigit = true;
				}
			}
			while (true) {
				System.out.print("▶ 주소 \n> ");
				address = scanner.nextLine();
				if (address.isBlank() == true) {
					System.out.println("주소는 빈칸이면 안됩니다.");
				} else {
					break;
				}

			}
			while (true) {
				System.out.print("▶ 종류 (가족, 친구, 기타 중에서 입력하세요) \n> ");
				relationship = scanner.nextLine();
				if (!relationship.equals("가족") && !relationship.equals("친구") && !relationship.equals("기타")) {
					System.out.println("가족, 친구, 기타 중에서 입력하세요.");
				} else {
					break;
				}
			}
			PersonInfo person = new PersonInfo(name, phoneNumber, address, relationship);
			personList.put(phoneNumber, person);
			System.out.println("사람이 추가되었습니다: " + name);
		}
	}

	public void deletePerson(String name, Scanner scanner) {
		ArrayList<String> phoneNumbers = new ArrayList<>();
		int number;
		int addOneNumber;
		String isYes = "예";
		for (String phone : personList.keySet()) {
			if (personList.get(phone).getName().equals(name)) {
				phoneNumbers.add(phone);
			}
		}

		if (phoneNumbers.isEmpty()) {
			System.out.println("이름 '" + name + "'을 찾을 수 없습니다.");
		} else if (phoneNumbers.size() == 1) {
			String userDelete = phoneNumbers.get(0);
			PersonInfo person = personList.get(userDelete);
			while (true) {
				System.out.println("이름 : " + person.getName() + ", 전화번호 : " + person.getPhoneNumber() + ", 주소 : "
						+ person.getAddress() + ", 관계 : " + person.getRelationship());
				System.out.println("삭제할 정보가 맞나여 > (예/아니요)");
				isYes = scanner.nextLine();
				if (isYes.equals("예")) {
					personList.remove(userDelete);
					System.out.println("삭제된 정보 = 이름 : " + person.getName() + ", 전화번호 : " + person.getPhoneNumber()
							+ ", 주소 : " + person.getAddress() + ", 관계 : " + person.getRelationship());
					break;
				} else if (isYes.equals("아니요")) {
					System.out.println("삭제가 되지 않았습니다.");
					break;
				} else {
					System.out.println("예/아니요 중에서 선택하세요.");
				}
			}
		} else {
			System.out.println("이름 '" + name + "'을 가진 사람들은 총 " + phoneNumbers.size() + "명");
			for (int i = 0; i < phoneNumbers.size(); i++) {
				String phone = phoneNumbers.get(i);
				PersonInfo person = personList.get(phone);
				System.out.println("[" + (i + 1) + "] 이름 = " + person.getName() + ", 전화번호 : " + person.getPhoneNumber()
						+ ", 주소 : " + person.getAddress() + ", 관계 : " + person.getRelationship());
			}
			while (true) {
				try {
					System.out.println("삭제하고 싶은 번호를 입력하세요 >");
					number = Integer.parseInt(scanner.nextLine());
					addOneNumber = number - 1;
					if (addOneNumber < 0 || addOneNumber > phoneNumbers.size()) {
						System.out.println("숫자를 다시 입력하세요.");
						continue;
					} else {
						while (true) {
							String userDelete = phoneNumbers.get(addOneNumber);
							PersonInfo person = personList.get(userDelete);
							try {
								System.out.println("이름 : " + person.getName() + ", 전화번호 : " + person.getPhoneNumber()
										+ ", 주소 : " + person.getAddress() + ", 관계 : " + person.getRelationship());
								System.out.println("삭제하실건가요? (예/아니요)");
								isYes = scanner.nextLine();
								if (isYes.equals("예")) {
									personList.remove(userDelete);
									System.out.println("삭제된 정보 = 이름 : " + person.getName() + ", 전화번호 : "
											+ person.getPhoneNumber() + ", 주소 : " + person.getAddress() + ", 관계 : "
											+ person.getRelationship());
									break;
								} else if (isYes.equals("아니요")) {
									System.out.println("삭제가 되지 않았습니다.");
									break;
								} else {
									System.out.println("예/아니요 중에서 선택하세요.");
									continue;
								}
							} catch (NoSuchElementException e) {
								System.out.println("잘못 입력했습니다.");
							}
							break;
						}

					}
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력하세요.");
					continue;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("범위 안에 숫자를 입력하세요.");
					continue;
				}
				break;
			}
		}
	}

	public void changePersonInfo(String name, String phoneNumber, String address, String relationship,
			Scanner scanner) {
		ArrayList<String> phoneNumbers = new ArrayList<>();
		String isYes = "예";
		for (String phone : personList.keySet()) {
			if (personList.get(phone).getName().equals(name)) {
				phoneNumbers.add(phone);
			}
		}
		if (phoneNumbers.isEmpty()) {
			System.out.println("이름 '" + name + "'을 찾을 수 없습니다.");
		} else if (phoneNumbers.size() == 1) {
			String changePersonName = phoneNumbers.get(0);
			PersonInfo person = personList.get(changePersonName);
			System.out.println("수정할 사람 정보 = 이름 : " + person.getName() + ", 전화번호 : " + person.getPhoneNumber()
					+ ", 주소 : " + person.getAddress() + ", 관계 : " + person.getRelationship());
			while (true) {
				System.out.println("수정하실건가요? (예/아니요)");
				isYes = scanner.nextLine();
				if (isYes.equals("예")) {
					personList.remove(changePersonName);
					addPerson(changePersonName, phoneNumber, address, relationship, scanner);
					break;
				} else if (isYes.equals("아니요")) {
					System.out.println("수정 되지 않았습니다.");
					break;
				} else {
					System.out.println("예/아니요 중에서 선택하세요.");
					continue;
				}
			}

		} else {
			System.out.println("이름 '" + name + "'을 가진 사람들은 총 " + phoneNumbers.size() + "명");
			for (int i = 0; i < phoneNumbers.size(); i++) {
				String phone = phoneNumbers.get(i);
				PersonInfo person = personList.get(phone);
				System.out.println("[" + (i + 1) + "] 이름 = " + person.getName() + ", 전화번호 : " + person.getPhoneNumber()
						+ ", 주소 : " + person.getAddress() + ", 관계 : " + person.getRelationship());
			}
			while (true) {
				try {

					System.out.println("수정하고 싶은 번호를 입력하세요 >");
					int number = Integer.parseInt(scanner.nextLine());
					int addOneNumber = number - 1;
					if (addOneNumber < 0 || addOneNumber > phoneNumbers.size()) {
						System.out.println("숫자를 다시 입력하세요.");
						continue;
					} else {
						while (true) {
							String changeuser = phoneNumbers.get(addOneNumber);
							PersonInfo person = personList.get(changeuser);
							System.out
									.println("수정할 정보 = 이름 : " + person.getName() + ", 전화번호 : " + person.getPhoneNumber()
											+ ", 주소 : " + person.getAddress() + ", 관계 : " + person.getRelationship());
							System.out.println("수정하실건가요? (예/아니요)");
							isYes = scanner.nextLine();
							if (isYes.equals("예")) {
								personList.remove(changeuser);
								addPerson(changeuser, phoneNumber, address, relationship, scanner);
								break;
							} else if (isYes.equals("아니요")) {
								System.out.println("수정 되지 않았습니다.");
								break;
							} else {
								System.out.println("예/아니요 중에서 선택하세요.");
								continue;
							}

						}

					}
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력하세요.");
					continue;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("범위 안에 숫자를 입력하세요.");
					continue;
				}
				break;
			}
		}

	}

	public void printContactList() {
		if (personList.isEmpty()) {
			System.out.println("등록된 사람이 없습니다.");
		} else {
			for (PersonInfo person : personList.values()) {
				System.out.println(person);
			}
		}
	}

	public HashMap<String, PersonInfo> getHashList() {
		return this.personList;
	}

	public void putHashList(HashMap<String, PersonInfo> personList) {
		this.personList = personList;
	}
}
