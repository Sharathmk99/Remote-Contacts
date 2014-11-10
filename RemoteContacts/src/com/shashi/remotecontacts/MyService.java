package com.shashi.remotecontacts;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.shashi.remotecontacts.database.Preference;
import com.shashi.remotecontacts.database.SqlDatabase;
import com.shashi.remotecontacts.helper.ContactsHelper;

public class MyService extends IntentService {

	ContentResolver mContentResolver;
	Cursor mCursor, mCursorNumber, mCursorEmail;
	String mName, mId, mPhoneNumber;
	ArrayList<String> mNumberList, mEmailList;
	SqlDatabase mSqlDatabase;
	Preference mPreference;

	public MyService() {
		super("MyService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		mContentResolver = getContentResolver();
		mNumberList = new ArrayList<String>();
		mEmailList = new ArrayList<String>();
		mSqlDatabase = new SqlDatabase(this);
		mPreference = new Preference(this);
		mPreference.storeData("notloaded", "ContactsLoaded");
		mCursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);
		int mNumberCount;
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				mName = mCursor
						.getString(mCursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				mId = mCursor.getString(mCursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				// Log.v(null,"  "+ mName);
				mNumberCount = Integer
						.parseInt(mCursor.getString(mCursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

				// / Get Numbers
				mNumberList.clear();
				if (mNumberCount > 0) {
					mCursorNumber = mContentResolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=?", new String[] { mId }, null);
					while (mCursorNumber.moveToNext()) {
						mPhoneNumber = mCursorNumber
								.getString(mCursorNumber
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						mNumberList.add(mPhoneNumber);
						// Log.v(null,"  "+ mName + "   " +mPhoneNumber);
						mSqlDatabase.addContacts(new ContactsHelper(mName,
								mPhoneNumber, "UnKnow", mId));
						mSqlDatabase.close();
					}
					mCursorNumber.close();
				}

				// / Get Emails
				mEmailList.clear();
				mCursorEmail = mContentResolver.query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ "=?", new String[] { mId }, null);
				while (mCursorEmail.moveToNext()) {
					mEmailList
							.add(mCursorEmail.getString(mCursorEmail
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
				}
				mCursorEmail.close();

			}
			mCursor.close();

		}
		mPreference.storeData("loaded", "ContactsLoaded");
	}

}
