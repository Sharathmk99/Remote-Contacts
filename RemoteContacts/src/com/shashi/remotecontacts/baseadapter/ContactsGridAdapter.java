package com.shashi.remotecontacts.baseadapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashi.remotecontacts.R;
import com.shashi.remotecontacts.helper.AllContacts;
import com.shashi.remotecontacts.helper.ContactsHelper;

public class ContactsGridAdapter extends BaseAdapter {

	ArrayList<ContactsHelper> mContacts;
	Context mContext;

	public ContactsGridAdapter() {
		// TODO Auto-generated constructor stub
	}

	public ContactsGridAdapter(Context mContext) {
		super();
		this.mContacts = AllContacts.getInstance(null).getContacts();
		this.mContext = mContext;
		sortContacts();
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

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mContacts.size();
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
			convertView = li.inflate(R.layout.grid_supp, null);
		}
		ImageView icon = (ImageView) convertView.findViewById(R.id.g_icon);
		retrieveContactPhoto(mContacts.get(position).getContactId(), icon);

		TextView label = (TextView) convertView.findViewById(R.id.g_label);
		label.setText(mContacts.get(position).getName());

		Animation animation_left = AnimationUtils.loadAnimation(mContext,
				android.R.anim.slide_in_left);
		convertView.setAnimation(animation_left);
		convertView.startAnimation(animation_left);
		return convertView;
	}

	private void retrieveContactPhoto(String contactID, ImageView imageView) {

		Bitmap photo = null;

		InputStream inputStream = ContactsContract.Contacts
				.openContactPhotoInputStream(mContext.getContentResolver(),
						ContentUris.withAppendedId(
								ContactsContract.Contacts.CONTENT_URI,
								Long.valueOf(contactID)));

		if (inputStream != null) {
			photo = BitmapFactory.decodeStream(inputStream);
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageView.setImageBitmap(photo);
		} else {
			imageView.setImageResource(R.drawable.ic_contact_picture);
		}

	}

}
