package com.orawaves.tcal.android.activites;

import java.util.List;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.facebook.Session;
import com.orawaves.android.adapters.PendingTimelineListAdapter;
import com.orawaves.tcal.R;
import com.orawaves.tcal.andorid.dto.DTO;
import com.orawaves.tcal.android.dao.TimelineDAO;
import com.orawaves.tcal.android.database.DBHandler;
import com.orawaves.tcal.android.util.AppUtil;

public class PostingTimelineNotification extends Activity implements OnItemClickListener{
 
	private ListView post_timeline_list;
	List<DTO> dtos ;
	String fbText = "";
	PendingTimelineListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posting_timeline);
		
		NotificationManager mNotificationManager =  (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(11);
		
		post_timeline_list = (ListView) findViewById(R.id.post_timeline_list);
		
		setListViewAdapter();
	}

	private void setListViewAdapter()
	{
		dtos = TimelineDAO.getInstance().getRecordBetwenDateTime(AppUtil.getCurrentDateTime(),
				AppUtil.getNextHourDateTime(), 
				DBHandler.getInstance(PostingTimelineNotification.this).getWritableDatabase());
				
		adapter = new PendingTimelineListAdapter(PostingTimelineNotification.this, dtos);
		post_timeline_list.setAdapter(adapter); 
		post_timeline_list.setOnItemClickListener(this);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	     
/*		final TimelineDTO tdto = (TimelineDTO)dtos.get(position);
	     new AlertDialog.Builder(this)
	    .setTitle("Delete Timeline")
	    .setMessage(tdto.getContent())
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	 
	        	boolean isDele = TimelineDAO.getInstance().delete(tdto, DBHandler.getInstance(PostingTimelineNotification.this).getWritableDatabase());
	        	if (isDele) {
	        		adapter.notifyDataSetChanged();
	        		Toast.makeText(PostingTimelineNotification.this, "Timeline deleted sucessfully", Toast.LENGTH_LONG).show();
	        		setListViewAdapter();
				}
	         
	        	dialog.dismiss();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	   .show(); */
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	   Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

	}

}
