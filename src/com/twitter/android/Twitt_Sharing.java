package com.twitter.android;

import java.io.File;
import java.io.InputStream;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.Toast;

import com.twitter.android.Twitter_Handler.TwDialogListener;

public class Twitt_Sharing {

    private final Twitter_Handler mTwitter;
    private final Activity activity;
    private String twitt_msg;
    private File image_path;
    private InputStream stream;
    TwitterCallback twitterCallback;
    public Twitt_Sharing(Activity act , TwitterCallback twitterCallback) {
	this.activity = act;
	this.twitterCallback = twitterCallback;
	mTwitter = new Twitter_Handler(activity, Util.TWITTER_CONSUMER_KEY, Util.TWITTER_CONSUMER_SECRET);
    }
    
    /*public Twitt_Sharing(Activity act, String consumer_key,String consumer_secret,AccessToken accessToken) {
		this.activity = act;
		mTwitter = new Twitter_Handler(activity, consumer_key, consumer_secret,accessToken);
    }*/

    public void shareToTwitter(String msg, File Image_url,InputStream stream) {
	this.twitt_msg = msg;
	this.image_path = Image_url;
	this.stream=stream;
	mTwitter.setListener(mTwLoginDialogListener);

	if (mTwitter.hasAccessToken()) {
	    // this will post data in asyn background thread
	    showTwittDialog();
	} else {
	    mTwitter.authorize();
	}
    }
    
    public void authorizeWithTwitter(final CheckBox twitter_checkBox){
    	
    	mTwitter.setListener(new TwDialogListener() {
			
			@Override
			public void onError(String value) {
				showToast("Login Failed.");
			    mTwitter.resetAccessToken();
			   
			}
			
			@Override
			public void onComplete(String value) {
				//showToast("Successfully authenticated with Twitter.");
			}
		});
    	 mTwitter.authorize();
    }
    
    public void shareToTwitter(String msg) {
    	this.twitt_msg = msg;
    	mTwitter.setListener(mTwLoginDialogListener);

    	if (mTwitter.hasAccessToken()) {
    	    // this will post data in asyn background thread
    	    showTwittDialog();
    	} else {
    	    mTwitter.authorize();
    	}
        }

    private void showTwittDialog() {

    	new PostTwittTask().execute(twitt_msg);

    }

    private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

	@Override
	public void onError(String value) {
	    showToast("Login Failed.");
	    mTwitter.resetAccessToken();
	}

	@Override
	public void onComplete(String value) {
	    showTwittDialog();
	}
    };

    void showToast(final String msg) {
	activity.runOnUiThread(new Runnable() {

	    @Override
	    public void run() {
	    
	    	Toast toast= Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
	   	  toast.setGravity(Gravity.CENTER, 0, 0);
	   	  toast.show();

	    }
	});

    }

    class PostTwittTask extends AsyncTask<String, Void, String> {
	ProgressDialog pDialog;

	@Override
	protected void onPreExecute() {
	    pDialog = new ProgressDialog(activity);
	    pDialog.setMessage("Posting Twitt...");
	    pDialog.setCancelable(false);
	    pDialog.show();
	    super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... twitt) {
	    try {
		// mTwitter.updateStatus(twitt[0]);
		// File imgFile = new File("/sdcard/bluetooth/Baby.jpg");

		Share_Pic_Text_Titter(image_path, twitt_msg,mTwitter.twitterObj,stream);
		return "success";

	    } catch (Exception e) {
		if (e.getMessage().toString().contains("duplicate")) {
		    return "Posting Failed because of Duplicate message...";
		}
		e.printStackTrace();
		return "Posting Failed!!!";
	    }

	}

	@Override
	protected void onPostExecute(String result) {
	    pDialog.dismiss();

	    if (null != result && result.equals("success")) {
		//showToast("Posted Successfully on your twitter wall.");
		twitterCallback.twitterCall();
	    } else {
		showToast(result);
	    }

	    super.onPostExecute(result);
	}
    }

    public void Share_Pic_Text_Titter(File image_path, String message,
	    Twitter twitter,InputStream stream) throws Exception {
	try {
	    StatusUpdate st = new StatusUpdate(message);

	    if(image_path!=null)
	    st.setMedia(image_path);
	    
	    if(stream!=null)
	    st.setMedia("tymlyn",stream);
	    
	    
	    twitter.updateStatus(st);

	    /*
	     * Toast.makeText(activity, "Successfully update on Twitter...!",
	     * Toast.LENGTH_SHORT).show();
	     */
	} catch (TwitterException e) {
	    Log.d("TAG", "Pic Upload error" + e.getErrorMessage());
	   /* Toast.makeText(activity,
		    "Ooopss..!!! Failed to update on Twitter.",
		    Toast.LENGTH_SHORT).show(); */
	    throw e;
	}
    }

   public static abstract class TwitterCallback
   {
	   public abstract void twitterCall();
   }
}
