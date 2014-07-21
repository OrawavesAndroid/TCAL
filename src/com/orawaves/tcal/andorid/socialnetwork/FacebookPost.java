package com.orawaves.tcal.andorid.socialnetwork;
 

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.orawaves.tcal.andorid.socialnetwork.FbAuthentionActivity.FBAuthCallback;

public class FacebookPost {
	
	SharedPreferences mfb_preferences;
	private Activity context;
	private FbCallBack callBack;
	public FacebookPost(Activity context,FbCallBack fbCallBack){
		this.context=context;
		this.callBack=fbCallBack;
	}
	
	

	public void post(final Bitmap bitmap)
	{
		
		 Session session = Session.getActiveSession();
	        if(session!=null && session.isOpened() && !session.isClosed()){
	        	postImageIntoFacebook(session,bitmap);
	        }else{
	          	FbAuthentionActivity.fbAuthCallback= new FBAuthCallback() {
	        		@Override
	        		public void authCompleted(Session session) {
	        			if(session.isOpened()){
	        				postImageIntoFacebook(session,bitmap);
	        			}
	        		}
	        	};
	        	
	        	context.startActivity(new Intent(context,FbAuthentionActivity.class));
	        	
	        }
	}
	
	
   public void postImageWithCaption(final String caption, final Bitmap bitmap )
   {
	   Session session = Session.getActiveSession();
       if(session!=null && session.isOpened() && !session.isClosed()){ 
    	   postImageCaption(session, caption, bitmap);
       }else{
         	FbAuthentionActivity.fbAuthCallback= new FBAuthCallback() {
       		@Override
       		public void authCompleted(Session session) {
       			if(session.isOpened()){
       			   postImageCaption(session, caption, bitmap); 
       			}
       		}
       	};
       	
       	context.startActivity(new Intent(context,FbAuthentionActivity.class));
       	
       }
   }
	
	public void post(final String msg )
	{
		
		 Session session = Session.getActiveSession();
	        if(session!=null && session.isOpened() && !session.isClosed()){
	        	postImageIntoFacebook(session,msg);
	        }else{
	        	
	        	FbAuthentionActivity.fbAuthCallback= new FBAuthCallback() {
	        		
	        		@Override
	        		public void authCompleted(Session session) {
	        			if(session.isOpened()){
	        				postImageIntoFacebook(session,msg);
	        			}
	        		}
	        	};
	        	
	        	context.startActivity(new Intent(context,FbAuthentionActivity.class));
	        }
	}
	
	void postImageIntoFacebook(Session session, String message){
    	
	   	
  		if(!session.getPermissions().contains("publish_actions"))
    	{
  			session.requestNewPublishPermissions(new Session.NewPermissionsRequest(context, "publish_actions"));
    	}
    	
	    	Request.newStatusUpdateRequest(session, message, new Request.Callback() {
				
				@Override
				public void onCompleted(Response response) {
					callBack.callback();
					/*Toast toast=Toast.makeText(context.getApplicationContext(), "posted successfully on your facebook wall.", Toast.LENGTH_LONG);
			    	toast.setGravity(Gravity.CENTER, 0, 0);
			    	toast.show();*/
				}
			}).executeAsync();
    }
	
	 void postImageIntoFacebook(Session session, Bitmap bitmap){
	    	    	
	   	
	  		if(!session.getPermissions().contains("publish_actions"))
	    	{
	  			session.requestNewPublishPermissions(new Session.NewPermissionsRequest(context, "publish_actions"));
	    	}
	    	
		    	Request.newUploadPhotoRequest(session, bitmap, new Request.Callback() {
					
					@Override
					public void onCompleted(Response response) {
						callBack.callback();
						/*Toast toast=Toast.makeText(context.getApplicationContext(), "posted successfully on your facebook wall.", Toast.LENGTH_LONG);
				    	toast.setGravity(Gravity.CENTER, 0, 0);
				    	toast.show();*/
					}
				}).executeAsync();
	    }

	 private void postImageCaption(Session session,String caption,Bitmap bitmap)
	 {
		  Request request = Request.newUploadPhotoRequest(session, bitmap, new Request.Callback() {
              
              @Override
              public void onCompleted(Response response) {
                  callBack.callback();
                  Toast toast=Toast.makeText(context.getApplicationContext(), "posted successfully on your facebook wall.", Toast.LENGTH_LONG);
                  toast.setGravity(Gravity.CENTER, 0, 0);
                  toast.show();
              }
          });
            Bundle params = request.getParameters();
            params.putString("message", caption);
            request.setParameters(params);
            request.executeAsync();
	 }
	 
	 public interface FbCallBack{
		 
		 public abstract void callback();
	 }
	 
}
