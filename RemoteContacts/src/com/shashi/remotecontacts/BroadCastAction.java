package com.shashi.remotecontacts;

import com.shashi.remotecontacts.database.Preference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadCastAction extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Preference mPreference = new Preference(context);
		if(mPreference.getData("autorun").equalsIgnoreCase("check"))
			context.startService(new Intent(context, SMSService.class));
	}

}
