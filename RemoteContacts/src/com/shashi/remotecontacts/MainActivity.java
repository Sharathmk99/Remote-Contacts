package com.shashi.remotecontacts;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.shashi.remotecontacts.background.BackgroundInterface;
import com.shashi.remotecontacts.background.BackgroundRun;
import com.shashi.remotecontacts.baseadapter.SlidingMenuItemAdapter;
import com.shashi.remotecontacts.clicklistener.SlidingMenuListener;
import com.shashi.remotecontacts.database.Preference;
import com.shashi.remotecontacts.database.SqlDatabase;
import com.shashi.remotecontacts.fragments.Main;
import com.shashi.remotecontacts.helper.AllContacts;
import com.shashi.remotecontacts.helper.SlidingMenuItems;

public class MainActivity extends ActionBarActivity implements
		BackgroundInterface, RevMobAdsListener {
	AllContacts mContacts;
	Preference mPreference;
	SqlDatabase mSqlDatabase;
	Bundle savedInstanceState;
	DrawerLayout mDrawerLayout;
	ActionBarDrawerToggle mDrawerToggle;
	ListView mListView;
	ArrayList<SlidingMenuItems> mSlidingItem = new ArrayList<SlidingMenuItems>();
	GoogleAnalytics mgoogleanalytics;
	Tracker mtracker;
	RevMob revmob;
	RevMobFullscreen fullscreen, eFullScreen;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// if (savedInstanceState != null)
		// Log.v(null, "  " + savedInstanceState.toString());
		mPreference = new Preference(this);
		mSqlDatabase = new SqlDatabase(this);
		this.savedInstanceState = savedInstanceState;
		mgoogleanalytics = GoogleAnalytics.getInstance(this);
		mtracker = mgoogleanalytics.getTracker(getResources().getString(
				R.string.googleanalytics));
		mtracker.sendView("/MainActivity");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mListView = (ListView) findViewById(R.id.list_slidermenu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		loadSlidingMenuItems();
		revmob = RevMob.start(this);
		revmob.showFullscreen(this);

		eFullScreen = revmob.createFullscreen(this, this);
		eFullScreen.load();
		new BackgroundRun(mPreference, mSqlDatabase, this, this).execute();
	}

	private void loadSharedPreference() {
		// TODO Auto-generated method stub
		if (mPreference.getData("activate").equalsIgnoreCase("error"))
			mPreference.storeData("check", "activate");
		if (mPreference.getData("autorun").equalsIgnoreCase("error"))
			mPreference.storeData("check", "autorun");
		if (mPreference.getData("pass").equalsIgnoreCase("error"))
			mPreference.storeData("1234", "pass");
		if (mPreference.getData("pass").equals("1234"))
			Toast.makeText(this, "Default Password Is 1234", Toast.LENGTH_LONG)
					.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			mDrawerLayout.openDrawer(mListView);
			return true;
		}
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onContactsLoaded() {
		// TODO Auto-generated method stub
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.open_drawer,
				R.string.close_drawer) {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				supportInvalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				supportInvalidateOptionsMenu();
			}

		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			Fragment mFragment = new Main();
			FragmentManager mFragementManager = getFragmentManager();
			mFragementManager.beginTransaction()
					.replace(R.id.frame_container, mFragment).commit();
			loadSharedPreference();
			if (mPreference.getData("activate").equalsIgnoreCase("check"))
				startService(new Intent(this, SMSService.class));
			mDrawerLayout.openDrawer(mListView);
		}

		setUpListView();
	}

	/*
	 * @Override protected void onPostCreate(Bundle savedInstanceState) { //
	 * TODO Auto-generated method stub super.onPostCreate(savedInstanceState);
	 * mDrawerToggle.syncState(); }
	 * 
	 * @Override public void onConfigurationChanged(Configuration newConfig) {
	 * // TODO Auto-generated method stub
	 * super.onConfigurationChanged(newConfig);
	 * mDrawerToggle.onConfigurationChanged(newConfig); }
	 */

	private void setUpListView() {
		// TODO Auto-generated method stub
		mListView.setAdapter(new SlidingMenuItemAdapter(this, mSlidingItem));
		mListView.setOnItemClickListener(new SlidingMenuListener(this,
				mDrawerLayout, mListView));
	}

	private void loadSlidingMenuItems() {
		// TODO Auto-generated method stub
		mSlidingItem.add(new SlidingMenuItems("Contacts", R.drawable.allapps));
		mSlidingItem.add(new SlidingMenuItems("Settings", R.drawable.settings));
		mSlidingItem.add(new SlidingMenuItems("How To Use", R.drawable.help));
		mSlidingItem
				.add(new SlidingMenuItems("More Apps", R.drawable.moreapps));
		mSlidingItem.add(new SlidingMenuItems("Update", R.drawable.update));
	}

	@Override
	public void onRevMobAdClicked() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onRevMobAdDismiss() {
		// TODO Auto-generated method stub
		if (backpressed)
			finish();
	}

	@Override
	public void onRevMobAdDisplayed() {
		// TODO Auto-generated method stub
		if (backpressed)
			finish();
	}

	@Override
	public void onRevMobAdNotReceived(String arg0) {
		// TODO Auto-generated method stub
		adloaded = false;
	}

	@Override
	public void onRevMobAdReceived() {
		// TODO Auto-generated method stub
		adloaded = true;
	}

	boolean backpressed = false;
	boolean adloaded = false;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// MobileCore.showOfferWall(this,this);
		backpressed = true;
		if (adloaded) {
			eFullScreen.show();
			adloaded = false;
		} else {
			finish();
		}
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
