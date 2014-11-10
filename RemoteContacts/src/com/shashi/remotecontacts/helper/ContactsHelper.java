package com.shashi.remotecontacts.helper;

public class ContactsHelper {
	private String Id, Name, Number, Email, ContactId;

	public ContactsHelper() {
		// TODO Auto-generated constructor stub
	}

	public ContactsHelper(String name, String number, String email,
			String contactId) {
		super();
		Name = name;
		Number = number;
		Email = email;
		ContactId = contactId;
	}

	public String getContactId() {
		return ContactId;
	}

	public void setContactId(String contactId) {
		ContactId = contactId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

}
