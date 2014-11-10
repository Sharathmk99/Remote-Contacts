package com.shashi.remotecontacts.helper;

import java.util.ArrayList;

public class AllContacts {

	ArrayList<ContactsHelper> mContacts;
	static AllContacts instance = null;
	
	public static AllContacts getInstance(ArrayList<ContactsHelper> mContacts) {
		if(instance == null)
			instance = new AllContacts(mContacts);
		return instance;
	}
	public AllContacts(ArrayList<ContactsHelper> mContacts) {
		// TODO Auto-generated constructor stub
		this.mContacts = mContacts;
	}
	
	public ArrayList<ContactsHelper> getContacts() {
		return mContacts;
	}
}
