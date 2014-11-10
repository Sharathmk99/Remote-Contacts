package com.shashi.remotecontacts.background;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.shashi.remotecontacts.MyService;
import com.shashi.remotecontacts.database.Preference;
import com.shashi.remotecontacts.database.SqlDatabase;
import com.shashi.remotecontacts.helper.AllContacts;
import com.shashi.remotecontacts.helper.ContactsHelper;

public class BackgroundRun extends AsyncTask<Void, Void, Void> {

	ArrayList<ContactsHelper> mContacts;
	Preference mPreference;
	SqlDatabase mSqlDatabase;
	Context mContext;
	ProgressDialog mDialog;
	BackgroundInterface mInterface;

	public BackgroundRun(Preference mPreference, SqlDatabase mSqlDatabase,
			Context mContext, BackgroundInterface mInterface) {
		this.mPreference = mPreference;
		this.mSqlDatabase = mSqlDatabase;
		this.mContext = mContext;
		this.mInterface = mInterface;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage("Loading...");
		mDialog.setCancelable(false);
		mDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if (mPreference.getData("ContactsLoaded").equals("error")
				|| mPreference.getData("ContactsLoaded").equals("notloaded")) {
			mContext.startService(new Intent(mContext, MyService.class));
			while (mPreference.getData("ContactsLoaded").equals("error")
					|| mPreference.getData("ContactsLoaded")
							.equals("notloaded"))
				;
			mContext.stopService(new Intent(mContext, MyService.class));
			mContacts = mSqlDatabase.getAllContacts();
		}

		else {
			mContacts = mSqlDatabase.getAllContacts();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (mDialog != null)
			mDialog.dismiss();
		AllContacts.getInstance(mContacts);
		mInterface.onContactsLoaded();
	}
}
