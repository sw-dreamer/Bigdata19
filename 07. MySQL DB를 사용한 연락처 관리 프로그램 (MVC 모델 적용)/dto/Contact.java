package contactMVC.dto;

public class Contact {
	private String name;
	private String address;
	private String phone;
	private String relation;
	
	public Contact(String name,  String phone, String address, String relation) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.relation = relation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	@Override
	public String toString() {
		return "이름 : " + name + ", 주소 : " + address + ", 연락처 : " + phone + ", 구분 : " + relation;
	}
	
	
	
}

