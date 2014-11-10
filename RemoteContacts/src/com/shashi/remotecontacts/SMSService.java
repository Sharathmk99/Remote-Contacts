package com.shashi.remotecontacts;

import java.util.ArrayList;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.shashi.remotecontacts.database.Preference;
import com.shashi.remotecontacts.database.SqlDatabase;
import com.shashi.remotecontacts.helper.ContactsHelper;

@SuppressWarnings("deprecation")
public class SMSService extends Service {

	BroadcastReceiver SMSReceiver;
	IntentFilter intent_filter;
	private String mPassword;
	Preference mPreference;
	SqlDatabase mSql;
	ArrayList<ContactsHelper> mContacts;
	GoogleAnalytics mgoogleanalytics;
	Tracker mtracker;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mPreference = new Preference(this);
		mgoogleanalytics = GoogleAnalytics.getInstance(this);
		mtracker = mgoogleanalytics.getTracker(getResources().getString(
				R.string.googleanalytics));
		mSql = new SqlDatabase(this);
		if (mPreference.getData("ContactsLoaded").equals("error")
				|| mPreference.getData("ContactsLoaded").equals("notloaded")) {
			startService(new Intent(this, MyService.class));
			while (mPreference.getData("ContactsLoaded").equals("error")
					|| mPreference.getData("ContactsLoaded")
							.equals("notloaded"))
				;
			mContacts = mSql.getAllContacts();
		}

		else {
			mContacts = mSql.getAllContacts();
		}

		sortContacts();
		intent_filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		SMSReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if (intent.getAction().equals(
						"android.provider.Telephony.SMS_RECEIVED")) {

					Bundle b = intent.getExtras();
					SmsMessage[] msg = null;
					String msgfrom = null;
					String msgBody = null;
					if (b != null) {
						Object[] pdus = (Object[]) b.get("pdus");
						msg = new SmsMessage[pdus.length];
						for (int i = 0; i < msg.length; i++) {
							msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
							msgfrom = msg[i].getOriginatingAddress();
							msgBody = msg[i].getMessageBody();
							String name = checkMessage(msgBody, msgfrom);
							name = name.toLowerCase();
							// Log.v(null, name);
							if (!name.equalsIgnoreCase("error")) {
								if (name.equals("all")) {
									appendAllContacts(msgfrom);
								} else if (isStartsWith) {
									checkStartsWith(name, msgfrom);
								} else {
									checkContactsName(name, msgfrom);
								}
							}

						}
					}
				}
			}

		};
		registerReceiver(SMSReceiver, intent_filter);
	}

	boolean isStartsWith = false;

	protected String checkMessage(String msgBody, String toAdress) {
		// TODO Auto-generated method stub
		isStartsWith = false;
		mPassword = mPreference.getData("pass");
		msgBody.toLowerCase();
		msgBody = msgBody.replace(" ", "");
		if (msgBody.contains("remotecontacts"))
			if (msgBody.subSequence(0, 14).toString().equals("remotecontacts")) {
				if (msgBody.contains(mPassword)) {
					int index = msgBody.indexOf(mPassword);
					if (msgBody.substring(index, msgBody.length()).equals(
							mPassword)) {
						String temp = msgBody.substring(14, index);
						if (temp.equals("all")) {
							return "all";
						} else if (temp.startsWith("startswith")) {
							String containts = temp.substring(10);
							isStartsWith = true;
							return containts;
						} else {
							return temp;
						}
					} else
						return "error";
				} else
					sendSMS(toAdress,
							"Thanks for using Remote Contacts\n Password Error");

			} else {
				return "error";
			}
		return "error";
	}

	private void appendAllContacts(String toAdress) {
		// TODO Auto-generated method stub
		mtracker.sendView("/Request All Contacts");
		StringBuilder mMessage = new StringBuilder();
		mMessage.append("Thanks for using Remote Contacts\n");
		for (int i = 0; i < mContacts.size(); i++) {
			mMessage.append(mContacts.get(i).getName().replace(" ", ""))
					.append("\n");
			mMessage.append(mContacts.get(i).getNumber().replace(" ", ""))
					.append("\n");
		}
		String temp = " ";
		String sendmsg = mMessage.toString();
		double index = Math.ceil(sendmsg.length() / 160.0f);
		if (sendmsg.length() > 160) {
			for (int i = 0; i < index; i++) {
				if (sendmsg.length() > 160) {
					temp = sendmsg.substring(0, 160);
					sendmsg = sendmsg.substring(160, sendmsg.length());
				} else
					temp = sendmsg;

				sendSMS(toAdress, temp);
			}
		} else {
			sendSMS(toAdress, sendmsg);
		}
	}

	private void checkStartsWith(String name, String toAdress) {
		// TODO Auto-generated method stub
		mtracker.sendView("/Request Start With Contacts");
		StringBuilder mMessage = new StringBuilder();
		mMessage.append("Thanks for using Remote Contacts\n");
		for (int i = 0; i < mContacts.size(); i++) {
			if (mContacts.get(i).getName().toLowerCase().trim()
					.replace(" ", "").startsWith(name)) {
				mMessage.append(mContacts.get(i).getName().replace(" ", ""))
						.append("\n");
				mMessage.append(mContacts.get(i).getNumber().replace(" ", ""))
						.append("\n");
			}
		}
		String temp = " ";
		String sendmsg = mMessage.toString();
		// Log.v(null, ""+sendmsg);
		double index = Math.ceil(sendmsg.length() / 160.0f);
		if (sendmsg.length() > 160) {
			for (int i = 0; i < index; i++) {
				if (sendmsg.length() > 160) {
					temp = sendmsg.substring(0, 160);
					sendmsg = sendmsg.substring(160, sendmsg.length());
				} else
					temp = sendmsg;

				sendSMS(toAdress, temp);
			}
		} else {
			sendSMS(toAdress, sendmsg);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(SMSReceiver);
	}

	private void checkContactsName(String name, String toAdress) {
		mtracker.sendView("/Requset Specific Contacts");
		StringBuilder mMessage = new StringBuilder();
		boolean isFound = false;
		mMessage.append("Thanks for using Remote Contacts\n").append(name)
				.append("\n");
		for (int i = 0; i < mContacts.size(); i++) {
			if (mContacts.get(i).getName().toLowerCase().trim()
					.replace(" ", "").contains(name)) {
				mMessage.append(mContacts.get(i).getNumber()).append("\n");
				isFound = true;
			}
		}
		if (isFound)
			sendSMS(toAdress, mMessage.toString());
		else
			sendSMS(toAdress,
					"Thanks for using Remote Contacts \n No Such Contacts Found");

	}

	private void sendSMS(String phoneNumber, String message) {
		Log.v(null, message);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

	private void sortContacts() {
		// TODO Auto-generated method stub
		for (int i = 0; i < mContacts.size(); i++)
			for (int j = 0; j < mContacts.size(); j++) {
				if (mContacts.get(i).getName()
						.compareToIgnoreCase(mContacts.get(j).getName()) < 0) {
					ContactsHelper mExchange = mContacts.get(i);
					mContacts.set(i, mContacts.get(j));
					mContacts.set(j, mExchange);
				}
			}
	}
}
