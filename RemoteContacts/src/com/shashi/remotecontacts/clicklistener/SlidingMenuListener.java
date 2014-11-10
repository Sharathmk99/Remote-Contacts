package com.shashi.remotecontacts.clicklistener;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.fragments.Contacts;
import com.shashi.remotecontacts.fragments.Settings;
import com.shashi.remotecontacts.helper.AllContacts;
import com.shashi.remotecontacts.helper.ContactsHelper;

public class SlidingMenuListener implements OnItemClickListener {

	Context context;
	DrawerLayout mDrawerLayout;
	ListView mListView;
	ArrayList<ContactsHelper> mContacts;
	String PlayLink = "market://details?id=com.shashi.remotecontacts";
	String DeveloperLink = "https://play.google.com/store/apps/developer?id=Shashi+Developer%27s";
	GoogleAnalytics mgoogleanalytics;
	Tracker mtracker;

	public SlidingMenuListener(Context context, DrawerLayout mDrawerLayout,
			ListView mListView) {
		super();
		this.context = context;
		this.mDrawerLayout = mDrawerLayout;
		this.mListView = mListView;
		this.mContacts = AllContacts.getInstance(null).getContacts();
		;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Fragment mFragment;
		switch (position) {
		case 0:
			mFragment = new Contacts();
			break;
		case 1:
			mFragment = new Settings();
			break;
		case 2:
			mgoogleanalytics = GoogleAnalytics.getInstance(context);
			mtracker = mgoogleanalytics.getTracker(context.getResources()
					.getString(R.string.googleanalytics));
			mtracker.sendView("/How to use");
			showDailog();
			return;
		case 3:
			startPlay(DeveloperLink);
			return;
		case 4:
			startPlay(PlayLink);
			return;

		default:
			return;
		}
		if (mFragment != null) {
			FragmentManager mFragmentManager = ((ActionBarActivity) context)
					.getFragmentManager();
			mFragmentManager.beginTransaction()
					.replace(R.id.frame_container, mFragment).commit();
			mListView.setItemChecked(position, true);
			mListView.setSelection(position);
			mDrawerLayout.closeDrawer(mListView);
		}

	}

	private String help = "Usefull Commands \n \n 1. To receive your contacts remotely just type and send: \n "
			+ "remotecontacts <name of contacts you want> <password>\n\n"
			+ "2. To receive all contacts from specific letter just type and send:\n"
			+ "remotecontacts starts with <letter of contacts you want> <password>\n"
			+ "Warning: These may use more messages\n\n"
			+ "3. To receive all contacts just type and send:\n"
			+ "remotecontacts <type all> <password>\n\n";

	private void showDailog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(0);
		builder.setTitle("Help");
		builder.setCancelable(true);
		builder.setMessage(help);
		builder.setPositiveButton("Done", new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create();
		builder.show();
	}

	private void startPlay(String link) {
		Intent i = new Intent(android.content.Intent.ACTION_VIEW);
		i.setData(Uri.parse(link));
		context.startActivity(i);
	}

}
