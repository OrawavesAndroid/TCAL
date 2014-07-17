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

import com.orawaves.android.adapters.DisplayTimelineListAdapter;
import com.orawaves.tcal.R;
import com.orawaves.tcal.andorid.dto.DTO;
import com.orawaves.tcal.andorid.dto.TimelineDTO;
import com.orawaves.tcal.android.dao.TimelineDAO;
import com.orawaves.tcal.android.database.DBHandler;

public class DisplayAllTimeLines extends Activity implements OnItemClickListener{


	private ListView post_timeline_list;
	List<DTO> dtos ; 
	String fbText = "";
	DisplayTimelineListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_timeline);

		NotificationManager mNotificationManager =  (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(11);

		post_timeline_list = (ListView) findViewById(R.id.post_timeline_list);

		setListViewAdapter();
	}

	public void setListViewAdapter()
	{ 
		dtos  = TimelineDAO.getInstance().getRecords(DBHandler.getInstance(DisplayAllTimeLines.this).getReadableDatabase());

		adapter = new DisplayTimelineListAdapter(DisplayAllTimeLines.this, dtos);
		post_timeline_list.setAdapter(adapter); 
		post_timeline_list.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

		final TimelineDTO tdto = (TimelineDTO)dtos.get(position);
		if (tdto.getCtype().equals("text"))
		{

			Intent intent = new Intent(DisplayAllTimeLines.this, TextActivity.class);
			Bundle bundle = new Bundle();
			bundle.putBoolean("isUpdate", true);
			bundle.putSerializable("dto", tdto);
			intent.putExtras(bundle);			
			startActivity(intent);
			finish();

		}
		else if (tdto.getCtype().equals("image"))
		{
			
		}

		else if (tdto.getCtype().equals("location"))
		{
		    Intent intent = new Intent(DisplayAllTimeLines.this, LocationActivity.class);
		    Bundle bundle = new Bundle();
			bundle.putBoolean("isUpdate", true);
			bundle.putSerializable("dto", tdto);
			intent.putExtras(bundle);			
			startActivity(intent);
			finish();
		}


	}
}
