package contactMVC.dao;

import java.util.ArrayList;

import contactMVC.dto.Contact;

public class DataAccessOjbect implements DataAccessOjbectInterface{
	
//	연락처 추가 기능 구현 메소드
	@Override
	public void insert(Contact contact) {
		DBInsert dbinsert = new DBInsert();

		dbinsert.insertPerson(contact);
	}
	
//	연락처 전체 검색후 리트스 반환 기능 구현 메소드
	@Override
	public ArrayList<Contact> select() {
		DBSelect dbselect = new DBSelect();
		ArrayList<Contact> contactList = dbselect.select();
		return contactList;
	}
	
//	연락처 이름 검색후 리스트 반환 기능 구현 메소드
	@Override
	public ArrayList<Contact> selectByName(String name) {
		DBSelect dbselect = new DBSelect();
		ArrayList<Contact> contactList = dbselect.selectByName(name);
		return contactList;
	}
//	연락처 전화번호 검색후 리스트 반환 기능 구현 메소드
	@Override
	public ArrayList<Contact> selectByPhone(String phone) {
		DBSelect dbselect = new DBSelect();
		ArrayList<Contact> contactList = dbselect.selectByPhoneNumber(phone);
		return contactList;
	}
		
	
//	연락처 수정 구현 메소드
	@Override
	public void update(Contact contact, String phone) {
		DBUpdate dbupdate = new DBUpdate();
		dbupdate.updatePersonByPhone(contact, phone);
	}

	
//	연락처 삭제 구현 메소드
	@Override
	public void delete(String phone) {
		DBDelete dbdelete = new DBDelete();

		dbdelete.deletePersonbyPhone(phone);
	}

}
