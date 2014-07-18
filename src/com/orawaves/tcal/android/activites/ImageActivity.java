package com.orawaves.tcal.android.activites;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orawaves.tcal.R;
import com.orawaves.tcal.andorid.dto.TimelineDTO;
import com.orawaves.tcal.android.dao.TimelineDAO;
import com.orawaves.tcal.android.database.DBHandler;
import com.orawaves.tcal.android.util.AppUtil;
import com.orawaves.tcal.android.util.DateTimePicker;

public class ImageActivity extends Activity implements OnClickListener {

	private EditText edtTextToShare;

	private Button btnSelectDateTime;
	private Button btnTimelineImage;
	private Button btnTwitterShare;	
	private Button btnFBShare;
	private Button btnEmailShare;
	private ImageView btnCamera;
	private ImageView btnGallerry;
	

	private final int CAMERA_REQUEST = 1;
	private final int RESULT_LOAD_IMAGE = 2;
	String imgFilePath ="";
	File mypath;
	public String current = null;

	//SocialAuthAdapter adapter;
	public	static int  minYear	 ;
	public	static	int minMonth ;
	public	static	int minDay;
	boolean mfb_share = false;
	boolean mtwitter_share = false;
	boolean memail_share = false;
	
	String  userEmail="";
	private List<String> possibleEmail;
	
	private String meShareOptions="";

	
	private Calendar mCalendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_share);

		edtTextToShare = (EditText) findViewById(R.id.edtTextShare);
		btnFBShare = (Button) findViewById(R.id.btnFbShare);
		btnSelectDateTime = (Button) findViewById(R.id.btnSelectDateTime);
		btnTimelineImage = (Button) findViewById(R.id.btnTimelineText);
		btnTwitterShare = (Button) findViewById(R.id.btnTwitterShare);
		btnEmailShare = (Button) findViewById(R.id.btnEmailShare);
		btnCamera = (ImageView) findViewById(R.id.bntCamera);
		btnGallerry = (ImageView) findViewById(R.id.bntGallary);



		btnFBShare.setOnClickListener(this);
		btnSelectDateTime.setOnClickListener(this);
		btnTimelineImage.setOnClickListener(this);
		btnTwitterShare.setOnClickListener(this);
		btnEmailShare.setOnClickListener(this);
		btnCamera.setOnClickListener(this); 
		btnGallerry.setOnClickListener(this);

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
				
				if (!imgFilePath.equalsIgnoreCase("")) {
				
				
				//Click on submit button
				//Preparing the DTO
				TimelineDTO timeDto = new TimelineDTO();
				timeDto.setCtype("image");
				timeDto.setContent(edtTextToShare.getText().toString().trim() + "~"+imgFilePath);
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

				boolean insert = TimelineDAO.getInstance().insert(timeDto, DBHandler.getInstance(ImageActivity.this).getWritableDatabase());

				if (insert) {
					Toast.makeText(getApplicationContext(), "Timeline added sucessfully", Toast.LENGTH_LONG).show();
					resetValues();
				}
				 
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please select Image to share", Toast.LENGTH_LONG).show();
				}
			}else
			{
				Toast.makeText(getApplicationContext(), "Please select the data and time", Toast.LENGTH_LONG).show();
			}
				

			break;
			
		case R.id.bntCamera:
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
			
			break;
		case R.id.bntGallary:
			/*Intent i = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, RESULT_LOAD_IMAGE);*/
			if (Environment.getExternalStorageState().equals("mounted")) {
			    Intent intent = new Intent();
			    intent.setType("image/*");
			    intent.setAction(Intent.ACTION_PICK);
			    startActivityForResult(
			        Intent.createChooser(
			            intent,
			            "Select Picture:"),
			            RESULT_LOAD_IMAGE);
			} 
			break;
		}
	}
	private void displayEmailDialog()
	{
		// custom dialog
					final Dialog dialog = new Dialog(ImageActivity.this);
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode) { 
		case RESULT_LOAD_IMAGE:
			if(resultCode == Activity.RESULT_OK){  
				Uri selectedImage = data.getData();
				imgFilePath = getRealPathFromURI(selectedImage);
				System.out.println("Gallarry Image path =" + imgFilePath);
				try {
					//edtTextToShare.setText(imgFilePath);
					//image.setImageBitmap(decodeUri(selectedImage));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			break;
		case CAMERA_REQUEST:
			// Toast.makeText(getActivity(), "okay", 8).show();
			if(resultCode == Activity.RESULT_OK){ 
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				//image.setImageBitmap(photo);

				try {

					ContextWrapper cw = new ContextWrapper(ImageActivity.this);
					File directory = cw.getDir(getResources().getString(R.string.external_dir),	Context.MODE_PRIVATE);
					// prepareDirectory();
					String uniqueId = getCurrentDate() + "_" + getCurrentTime()	+ "_" + Math.random();
					System.out.println("uniqueId" + uniqueId);
					// uniqueId = yourName.getText().toString().trim();
					current = uniqueId + ".jpg";
					mypath = new File(directory, current);
					FileOutputStream mFileOutStream;
					mFileOutStream = new FileOutputStream(mypath);
					photo.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutStream);
					// Converting to byte array
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					photo.compress(CompressFormat.JPEG, 0, stream);
					imgFilePath = mypath.getAbsolutePath();
					//edtTextToShare.setText(imgFilePath);
					System.out.println("capture image  ===" + mypath);				
					//Log.i("Complaint", imageArray.toString());
					mFileOutStream.flush();
					mFileOutStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}


	}
	private String getCurrentDate() {
		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.d("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));
	}

	private String getCurrentTime() {

		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
				+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		Log.d("TIME:", String.valueOf(currentTime));
		return (String.valueOf(currentTime));

	}
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                ImageActivity.this.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
        		 ImageActivity.this.getContentResolver().openInputStream(selectedImage), null, o2);
    }
	
	private String getRealPathFromURI(Uri contentURI) {
	    String result;
	    Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
	    if (cursor == null) { // Source is Dropbox or other similar local file path
	        result = contentURI.getPath();
	    } else { 
	        cursor.moveToFirst(); 
	        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	        result = cursor.getString(idx);
	        cursor.close();
	    }
	    return result;
	}

}
