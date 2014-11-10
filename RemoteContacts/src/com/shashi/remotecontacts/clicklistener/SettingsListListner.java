package com.shashi.remotecontacts.clicklistener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.SMSService;
import com.shashi.remotecontacts.database.Preference;

public class SettingsListListner implements OnItemClickListener {

	Context mContext;
	Preference mPreference;

	public SettingsListListner(Context mContext) {
		super();
		this.mContext = mContext;
		mPreference = new Preference(mContext);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ImageView check = (ImageView) view.findViewById(R.id.s_icon);
		Log.v(null, "OnItemClick" + position);
		switch (position) {
		case 0:
			if (mPreference.getData("activate").equalsIgnoreCase("check")) {
				mPreference.storeData("uncheck", "activate");
				check.setImageResource(R.drawable.check_empty);
				mContext.stopService(new Intent(mContext, SMSService.class));
			} else {
				mPreference.storeData("check", "activate");

				check.setImageResource(R.drawable.check_full);
				mContext.startService(new Intent(mContext, SMSService.class));
			}
			break;
		case 1:
			if (mPreference.getData("autorun").equalsIgnoreCase("check")) {
				mPreference.storeData("uncheck", "autorun");
				check.setImageResource(R.drawable.check_empty);
			} else {
				mPreference.storeData("check", "autorun");
				check.setImageResource(R.drawable.check_full);
			}
			break;
		case 2:
			showDailog();
			break;
		default:
			break;
		}
		/*
		 * Log.v(null, "Fragment"); Fragment mFragment = new Settings();
		 * FragmentManager mFragementManager =
		 * ((ActionBarActivity)mContext).getFragmentManager();
		 * mFragementManager.beginTransaction() .replace(R.id.frame_container,
		 * mFragment).commit();
		 */
	}

	private void showDailog() {
		final View layout = View.inflate(mContext, R.layout.dailog, null);
		final EditText mInput = (EditText) layout.findViewById(R.id.pass);
		mInput.setText(mPreference.getData("pass"));
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setIcon(0);
		builder.setTitle("Change Password");
		builder.setCancelable(true);
		builder.setNegativeButton("Cancel", new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setPositiveButton("Done", new Dialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String password = mInput.getText().toString().trim();
				if (password.contains(" ")) {
					Toast.makeText(mContext,
							"No Space Allowed Please Try Again",
							Toast.LENGTH_LONG).show();
					return;
				}
				Toast.makeText(mContext, "Your Password is: " + password,
						Toast.LENGTH_LONG).show();
				mPreference.storeData(password, "pass");
			}
		});
		builder.setView(layout);
		builder.create();
		builder.show();
	}

}
