package com.twitter.android;

import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TwitterSession {
    private final SharedPreferences sharedPref;
    private final Editor editor;

    

    public TwitterSession(Context context) {
	sharedPref = context.getSharedPreferences(Util.TWEET_SHARED, Context.MODE_PRIVATE);

	editor = sharedPref.edit();
    }

    public void storeAccessToken(AccessToken accessToken, String username) {
	editor.putString(Util.TWEET_AUTH_KEY, accessToken.getToken());
	editor.putString(Util.TWEET_AUTH_SECRET_KEY, accessToken.getTokenSecret());
	editor.putString(Util.TWEET_USER_NAME, username);

	editor.commit();
    }

    public void resetAccessToken() {
	editor.putString(Util.TWEET_AUTH_KEY, null);
	editor.putString(Util.TWEET_AUTH_SECRET_KEY, null);
	editor.putString(Util.TWEET_USER_NAME, null);

	editor.commit();
    }

    public String getUsername() {
	return sharedPref.getString(Util.TWEET_USER_NAME, "");
    }

    public AccessToken getAccessToken() {
	String token = sharedPref.getString(Util.TWEET_AUTH_KEY, null);
	String tokenSecret = sharedPref.getString(Util.TWEET_AUTH_SECRET_KEY, null);

	if (token != null && tokenSecret != null)
	    return new AccessToken(token, tokenSecret);
	else
	    return null;
    }
}