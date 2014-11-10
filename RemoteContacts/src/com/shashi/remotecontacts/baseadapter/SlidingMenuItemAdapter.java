package com.shashi.remotecontacts.baseadapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.helper.SlidingMenuItems;

public class SlidingMenuItemAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<SlidingMenuItems> mArrayMenu;

	public SlidingMenuItemAdapter(Context mContext,
			ArrayList<SlidingMenuItems> mArrayMenu) {
		super();
		this.mContext = mContext;
		this.mArrayMenu = mArrayMenu;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayMenu.size();
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
			LayoutInflater li = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.menu_list, null);
		}
		ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
		icon.setImageResource(mArrayMenu.get(position).getIcon());
		TextView label = (TextView) convertView.findViewById(R.id.label);
		label.setText(mArrayMenu.get(position).getName());
		return convertView;
	}

}
