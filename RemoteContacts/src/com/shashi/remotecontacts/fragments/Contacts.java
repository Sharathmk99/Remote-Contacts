package com.shashi.remotecontacts.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.baseadapter.ContactsGridAdapter;
import com.shashi.remotecontacts.clicklistener.ContactsGridListener;
import com.shashi.remotecontacts.helper.AllContacts;
import com.shashi.remotecontacts.helper.ContactsHelper;

public class Contacts extends Fragment implements RevMobAdsListener {

	ArrayList<ContactsHelper> mContacts;
	GridView mGridView;
	ContactsGridAdapter mAdapter;
	GoogleAnalytics mgoogleanalytics;
	Tracker mtracker;
	RevMob revmob;
	RevMobFullscreen fullscreen;

	public Contacts() {
		// TODO Auto-generated constructor stub
		mContacts = AllContacts.getInstance(null).getContacts();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_grid, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// if(savedInstanceState == null)
		super.onViewCreated(view, savedInstanceState);
		AdView mAdView = (AdView) getActivity().findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		mgoogleanalytics = GoogleAnalytics.getInstance(getActivity());
		mtracker = mgoogleanalytics.getTracker(getResources().getString(
				R.string.googleanalytics));
		mtracker.sendView("/Contacts");
		revmob = RevMob.start(getActivity());
		revmob.showFullscreen(getActivity());
		mAdapter = new ContactsGridAdapter(getActivity());
		mGridView = (GridView) view.findViewById(R.id.gridView1);
		mGridView.setAdapter(mAdapter);
		registerForContextMenu(mGridView);
		mGridView.setOnItemClickListener(new ContactsGridListener());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	AdapterView.AdapterContextMenuInfo info;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		int position = info.position;
		menu.setHeaderTitle(mContacts.get(position).getName() + "("
				+ mContacts.get(position).getNumber() + ")");
		menu.add(0, 0, 0, "Call");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int position = info.position;
		if (item.getItemId() == 0) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:"
					+ mContacts.get(position).getNumber()));
			getActivity().startActivity(intent);
			return true;
		} else if (item.getItemId() == 1) {

			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onRevMobAdClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobAdDismiss() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobAdDisplayed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobAdNotReceived(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobAdReceived() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobEulaIsShown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobEulaWasAcceptedAndDismissed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobEulaWasRejected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobSessionIsStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRevMobSessionNotStarted(String arg0) {
		// TODO Auto-generated method stub

	}

}
