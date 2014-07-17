package com.orawaves.tcal.android.activites;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orawaves.tcal.R;
import com.orawaves.tcal.andorid.dto.TimelineDTO;
import com.orawaves.tcal.android.dao.TimelineDAO;
import com.orawaves.tcal.android.database.DBHandler;
import com.orawaves.tcal.android.util.AppUtil;
import com.orawaves.tcal.android.util.DateTimePicker;

@SuppressLint("SimpleDateFormat") 
public class TextActivity extends Activity implements OnClickListener {

	private EditText edtTextToShare;

	private Button btnSelectDateTime;
	private Button btnTimelineText;
	private Button btnTwitterShare;	
	private Button btnFBShare;
	private Button btnEmailShare;

	//SocialAuthAdapter adapter;
	public	static int  minYear	 ;
	public	static	int minMonth ;
	public	static	int minDay;
	boolean mfb_share = false;
	boolean mtwitter_share = false;
	boolean memail_share = false;
	
	boolean isUpdate = false;

	String  userEmail="";
	private List<String> possibleEmail;


	private String meShareOptions="";

	private Calendar mCalendar;
	
	private TimelineDTO updatedDto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_share);

		edtTextToShare = (EditText) findViewById(R.id.edtTextShare);
		btnFBShare = (Button) findViewById(R.id.btnFbShare);
		btnSelectDateTime = (Button) findViewById(R.id.btnSelectDateTime);
		btnTimelineText = (Button) findViewById(R.id.btnTimelineText);
		btnTwitterShare = (Button) findViewById(R.id.btnTwitterShare);
		btnEmailShare = (Button) findViewById(R.id.btnEmailShare);



		btnFBShare.setOnClickListener(this);
		btnSelectDateTime.setOnClickListener(this);
		btnTimelineText.setOnClickListener(this);
		btnTwitterShare.setOnClickListener(this);
		btnEmailShare.setOnClickListener(this);

		
		Bundle bundle = getIntent().getExtras();
		if (bundle.getBoolean("isUpdate") ) 
		{
			isUpdate = true;
			updatedDto =(TimelineDTO) bundle.getSerializable("dto");
			updataOnGUI(updatedDto);
		}

	}
	
	private void updataOnGUI(TimelineDTO updatedDto) {
		
		edtTextToShare.setText(updatedDto.getContent());
		btnSelectDateTime.setText(updatedDto.getDateAndTime()); 
		if (updatedDto.getmShare().contains("f")) {
			 mfb_share = true;
			 btnFBShare.setBackgroundResource(R.drawable.facebook_focus);
		}
		if (updatedDto.getmShare().contains("t")) {
			 mtwitter_share = true;
			 btnTwitterShare.setBackgroundResource(R.drawable.twitter_focus);
		}
		if (updatedDto.getmShare().contains("e")) {
			 memail_share = true;
			 possibleEmail = new ArrayList<String>();
			 String upEmails[] = updatedDto.getmShareEmail().split(",");
			 for (int i = 0; i < upEmails.length; i++) {
				 possibleEmail.add(upEmails[i]);
			}
			 btnEmailShare.setBackgroundResource(R.drawable.email_focus);
		}
		
	}  

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnFbShare:

			if (mfb_share == true) {
				mfb_share = false;
				btnFBShare.setBackgroundResource(R.drawable.facebook_narmal);
			}else
			{
				mfb_share = true;
				btnFBShare.setBackgroundResource(R.drawable.facebook_focus);
			}

			break;

		case R.id.btnTwitterShare:

			if (mtwitter_share == true) {
				mtwitter_share = false;
				btnTwitterShare.setBackgroundResource(R.drawable.twitter_normal);
			}else
			{
				mtwitter_share = true;
				btnTwitterShare.setBackgroundResource(R.drawable.twitter_focus);
			}

			break;
		case R.id.btnEmailShare:



			//userEmail = UserEmailFetcher.getEmail(TextActivity.this);
			userEmail="";
			possibleEmail = new ArrayList<String>();
			displayEmailDialog();
 

			if (memail_share == true) {
				memail_share = false;
				btnEmailShare.setBackgroundResource(R.drawable.email_normal);
			}else
			{
				memail_share = true;
				btnEmailShare.setBackgroundResource(R.drawable.email_focus);
			}
			break;

		case R.id.btnSelectDateTime:
			showDateTimeDialog(btnSelectDateTime);
			break;

		case R.id.btnTimelineText:
			
			
			if (!btnSelectDateTime.getText().toString().trim().equalsIgnoreCase("Select date and time")) {

				//Click on submit button
				//Preparing the DTO
				TimelineDTO timeDto = new TimelineDTO();
				timeDto.setCtype("text");
				timeDto.setContent(edtTextToShare.getText().toString().trim());
				timeDto.setDateAndTime(btnSelectDateTime.getText().toString().trim());

				if (mfb_share == true) {
					meShareOptions = meShareOptions+"f";
				} 
				if (mtwitter_share == true) {
					meShareOptions = meShareOptions+"t";

				} 
				if (memail_share == true) {
					meShareOptions = meShareOptions+"e";
				} 

				timeDto.setmShare(meShareOptions);

				if (memail_share == true) {
					timeDto.setmShareEmail(userEmail);
				}else
				{
					timeDto.setmShareEmail("");
				}

				timeDto.setoShare("f");
				timeDto.setoShareEmail("");
				
				boolean insert;
				if (isUpdate) {
					 insert = TimelineDAO.getInstance().updateWithId( updatedDto.getId(), timeDto, DBHandler.getInstance(TextActivity.this).getWritableDatabase());
				}else
				{
					 insert = TimelineDAO.getInstance().insert(timeDto, DBHandler.getInstance(TextActivity.this).getWritableDatabase());
				}

				if (insert) {
					Toast.makeText(getApplicationContext(), "Timeline added sucessfully", Toast.LENGTH_LONG).show();
					resetValues();
				}

			}else
			{
				Toast.makeText(getApplicationContext(), "Please select the data and time", Toast.LENGTH_LONG).show();
			}

			break;

		}


	}

	private void resetValues()
	{
		edtTextToShare.setText(""); 
		meShareOptions="";

		memail_share = false;
		btnEmailShare.setBackgroundResource(R.drawable.email_normal);

		mtwitter_share = false;
		btnTwitterShare.setBackgroundResource(R.drawable.twitter_normal);

		mfb_share = false;
		btnFBShare.setBackgroundResource(R.drawable.facebook_narmal);

		btnSelectDateTime.setText("Select date and time");
	}
	
	private void displayEmailDialog()
	{
		 // custom dialog
		 final Dialog dialog = new Dialog(TextActivity.this);
		 dialog.setContentView(R.layout.email_select);
		 dialog.setTitle("Select email accounts");
		 
		 // set the custom dialog components - text, image and button
		 Button bntEmails = (Button) dialog.findViewById(R.id.btnEmailSelectList);
		 final ListView listViewEmails = (ListView) dialog.findViewById(R.id.listEmails); 
					
		 //Get possible emails
		 Account[] accounts=AccountManager.get(this).getAccountsByType("com.google");
		 String myEmailid=accounts[0].toString();
		 Log.d("My email id that i want", myEmailid);
		 for(Account account: accounts)
		  {
			 possibleEmail.add(account.name);
					 
		  }
		 
		 final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,possibleEmail  );
		 listViewEmails.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		 listViewEmails.setAdapter(adapter);
					 
					
				 
		 bntEmails.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View v) {
		 SparseBooleanArray checked = listViewEmails.getCheckedItemPositions();
		 ArrayList<String> selectedItems = new ArrayList<String>();
		 for (int i = 0; i < checked.size(); i++) {
			  // Item position in adapter
			  int position = checked.keyAt(i);
			  // Add sport if it is checked i.e.) == TRUE!
			  if (checked.valueAt(i))
				selectedItems.add(adapter.getItem(position));
				}
			  for (int i = 0; i < selectedItems.size(); i++) {
				  userEmail =  selectedItems.get(i)+",";
				}
			dialog.dismiss();
		}
	});
	dialog.show();
	}

	/**
	 * Listens Response from Library
	 * 
	 */


	private void showDateTimeDialog(final Button bntDateTime) 
	{
		mCalendar	= Calendar.getInstance();      
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(this);

		minDay		=mCalendar.get(Calendar.DAY_OF_MONTH);
		minMonth	=mCalendar.get(Calendar.MONTH)+1;
		minYear		=mCalendar.get(Calendar.YEAR);     
		DateTimePicker.getCurrentDate(minDay+"-"+minMonth+"-"+minYear+","+"9:10 ");

		final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);


		// Grab widget instance
		final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);


		// Check is system is set to use 24h time (this doesn't seem to work as expected though)
		final boolean is24h = false;      	  
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) 
			{        		

				mDateTimePicker.clearFocus();	
				try {
					String sdate =mDateTimePicker.get(Calendar.YEAR)+"/"+(mDateTimePicker.get(Calendar.MONTH)+1)+"/"+mDateTimePicker.get(Calendar.DAY_OF_MONTH);//
					String stime = mDateTimePicker.get(Calendar.HOUR) +":"+mDateTimePicker.get(Calendar.MINUTE)+" "+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");

					String dbDateFormat  = AppUtil.getDateDbFormat(sdate);
					String dbTimeformat = AppUtil.getTimeDbFormat(stime);

					String finlDateandTime = dbDateFormat+" "+dbTimeformat;

					bntDateTime.setText(finlDateandTime);

					//Toast.makeText(getApplicationContext(), finlDateandTime, 10).show();
				} catch (Exception e) {
					e.printStackTrace();
				}

				mDateTimeDialog.cancel();

			}
		});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				mDateTimeDialog.cancel();
			}
		});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mDateTimePicker.reset();
			}
		});

		// Setup TimePicker
		mDateTimePicker.setIs24HourView(is24h);
		// No title on the dialog window
		mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mDateTimeDialog.setContentView(mDateTimeDialogView);
		// Display the dialog
		mDateTimeDialog.show();

	} 
}
