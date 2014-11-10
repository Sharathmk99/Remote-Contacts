package com.shashi.remotecontacts.database;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

	private SharedPreferences mPreference;
	private static String Filename1 = "Contacts";
	private SharedPreferences.Editor editor;

	public Preference(Context mContext) {
		super();
		mPreference = mContext.getSharedPreferences(Filename1, 0);
	}

	public void storeData(String data, String key) {
		editor = mPreference.edit();
		editor.putString(key, data);
		editor.commit();
	}

	public String getData(String key) {
		return mPreference.getString(key, "error");
	}
}
