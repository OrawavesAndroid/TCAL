package com.orawaves.tcal.andorid.socialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.Session;
import com.facebook.SessionState;

public class FbAuthentionActivity extends Activity {
	
	//static boolean isFromLogin = false;
	 static FBAuthCallback fbAuthCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      //  	isFromLogin = true;
        	Session.openActiveSession(this, true, new Session.StatusCallback() {
				
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					if(session.isOpened()){
						fbAuthCallback.authCompleted(session);
						finish();
					}
				}
			});
        
    }
    
  
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	//if(isFromLogin){
    		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    //	}
    }
    
    public  interface FBAuthCallback
    {
 	   public abstract void authCompleted(Session session);
    } 
    
}
