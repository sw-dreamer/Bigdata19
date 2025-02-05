package contactMVC.dao;

import java.util.ArrayList;

import contactMVC.dto.Contact;

public interface DataAccessOjbectInterface {

	public void insert(Contact contact);

	public ArrayList<Contact> select();

	public ArrayList<Contact> selectByName(String name);
	
	public ArrayList<Contact> selectByPhone(String phone);

	public void update(Contact contact, String phone);


	public void delete(String phone);


}
