package com.shashi.remotecontacts.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shashi.remotecontacts.helper.ContactsHelper;

public class SqlDatabase extends SQLiteOpenHelper {

	private static int Database_Version = 1;
	private static String Database_Name = "ContactsInfo";
	private static String Table_Name = "Contacts";
	private static String Id = "id";
	private static String Name = "name";
	private static String Number = "number";
	private static String Email = "email";
	private static String ContactId = "contactId";

	public SqlDatabase(Context context) {
		super(context, Database_Name, null, Database_Version);
		// TODO Auto-generated constructor stub
		Log.v(null, "SqlDatabase");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.v(null, "onCreateDB");
		String createTable = "CREATE TABLE " + Table_Name + "(" + Id
				+ " INTEGER PRIMARY KEY," + Name + " TEXT," + Number + " TEXT,"
				+ Email + " TEXT," + ContactId + " TEXT" + ")";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.v(null, "onUpgradeDB");
		db.execSQL("DROP IF TABLE EXISTS" + Table_Name);
		onCreate(db);
	}

	public void addContacts(ContactsHelper mContact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues mContentVal = new ContentValues();
		mContentVal.put(Name, mContact.getName());
		mContentVal.put(Number, mContact.getNumber());
		mContentVal.put(Email, mContact.getEmail());
		mContentVal.put(ContactId, mContact.getContactId());
		db.insert(Table_Name, null, mContentVal);
		db.close();
	}

	public ArrayList<ContactsHelper> getAllContacts() {
		ArrayList<ContactsHelper> mContactstemp = new ArrayList<ContactsHelper>();
		String selectQuery = "SELECT  * FROM " + Table_Name;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ContactsHelper contact = new ContactsHelper();
				contact.setId(cursor.getString(0));
				contact.setName(cursor.getString(1));
				contact.setNumber(cursor.getString(2));
				contact.setEmail(cursor.getString(3));
				contact.setContactId(cursor.getString(4));
				mContactstemp.add(contact);
			} while (cursor.moveToNext());
		}
		return mContactstemp;
	}

	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + Table_Name;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		// cursor.close();
		return cursor.getCount();
	}

}
