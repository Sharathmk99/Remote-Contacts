package com.shashi.remotecontacts.clicklistener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ContactsGridListener implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		parent.showContextMenuForChild(view);
	}

}
