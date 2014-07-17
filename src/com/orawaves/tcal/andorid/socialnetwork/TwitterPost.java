package com.orawaves.tcal.andorid.socialnetwork;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;

import com.twitter.android.Twitt_Sharing;
import com.twitter.android.Twitt_Sharing.TwitterCallback;

public class TwitterPost {

	
	public void Post(Activity context, final String content, TwitterCallback twitterCallback)
	{
		
		 Twitt_Sharing twitt = new Twitt_Sharing(context,twitterCallback);
		    twitt.shareToTwitter(content, null,null);// to share image in place of null pass file object.
	}

	public void Post(Activity context, final String content, final File file,final InputStream inputStream,TwitterCallback twitterCallback)
	{
		
		 Twitt_Sharing twitt = new Twitt_Sharing(context,twitterCallback);
		    twitt.shareToTwitter(content, file,inputStream);// to share image in place of null pass file object.
	}
	
}
