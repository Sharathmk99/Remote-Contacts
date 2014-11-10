package com.shashi.remotecontacts.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.database.Preference;

public class SettinsListAdapter extends BaseAdapter {

	Context mContext;
	String[] label = { "Activate", "Auto Run", "Change Password" };
	Preference mPreference;

	public SettinsListAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mPreference = new Preference(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return label.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.setiings_listr, null);
		}
		TextView label = (TextView) convertView.findViewById(R.id.s_label);
		label.setText(this.label[position]);
		ImageView check = (ImageView) convertView.findViewById(R.id.s_icon);
		if (position == 2)
			check.setVisibility(View.GONE);
		else if (position == 0) {
			check.setVisibility(View.VISIBLE);
			if (mPreference.getData("activate").equalsIgnoreCase("check"))
				check.setImageResource(R.drawable.check_full);
			else
				check.setImageResource(R.drawable.check_empty);
		} else if (position == 1) {
			check.setVisibility(View.VISIBLE);
			if (mPreference.getData("autorun").equalsIgnoreCase("check"))
				check.setImageResource(R.drawable.check_full);
			else
				check.setImageResource(R.drawable.check_empty);
		}
		return convertView;
	}

}
