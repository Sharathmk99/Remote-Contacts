package com.shashi.remotecontacts.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.baseadapter.SettinsListAdapter;
import com.shashi.remotecontacts.clicklistener.SettingsListListner;

public class Settings extends Fragment {

	public static ListView mListView;
	GoogleAnalytics mgoogleanalytics;
	Tracker mtracker;

	public Settings() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.settings, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		AdView mAdView = (AdView) getActivity().findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		mgoogleanalytics = GoogleAnalytics.getInstance(getActivity());
		mtracker = mgoogleanalytics.getTracker(getResources().getString(
				R.string.googleanalytics));
		mtracker.sendView("/Settings");
		mListView = (ListView) view.findViewById(R.id.s_list);
		mListView.setAdapter(new SettinsListAdapter(getActivity()));
		mListView
				.setOnItemClickListener(new SettingsListListner(getActivity()));
	}
}
