/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unlockdisk.android.opengl;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import com.crittercism.app.Crittercism;
import com.unlockdisk.android.cylinder.CylinderSurfaceView;

public class MainGLActivity extends Activity {
	 public static AssetManager assetManager;
    protected CommonGLSurfaceView mGLView;
   public NotificationManager notificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager = getAssets();
        
       
     //   initializeCrittercism();
        generateNotification(10001,"manjeet","this is my unlockdisk");
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
       // mGLView = new MyGLSurfaceView(this);
       // mGLView = new SphereSurfaceView(this);
      //  mGLView = new DiskSurfaceView(this);
        mGLView = new CylinderSurfaceView(this);
       // mGLView = new SphereSurfaceView(this);
        setContentView(mGLView);
    }

    
    private void initializeCrittercism() {
    	 ///////////////
        // instantiate metadata json object
           JSONObject metadata = new JSONObject();
           // add arbitrary metadata
           try {
   			metadata.put("user_id", 123);
   		} catch (JSONException e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}
           try {
   			metadata.put("name", "John Doe");
   		} catch (JSONException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
           // send metadata to crittercism (asynchronously)
           Crittercism.setMetadata(metadata);
       //	String crittercismKey = buildHelper.getEnvProperty(Config.CRITTERCISM_KEY);
   		Crittercism.init(getApplicationContext(), Config.CRITTERCISM_KEY,metadata);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }
    
//    @Override
    protected void onDistroy() {
    	
    }
    
	 public void generateNotification(int id,String notificationTitle, String notificationMessage)
	    {
	        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	        Notification notification = new Notification(R.drawable.ic_launcher, "A New Message!", System.currentTimeMillis());
	 
	       Intent notificationIntent = new Intent(this, MainGLActivity.class);
	        // Intent notificationIntent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://www.android.com"));

	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	 
	        notification.setLatestEventInfo(MainGLActivity.this, notificationTitle, notificationMessage, pendingIntent);
	        
			//Setting Notification Flags
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.flags |= Notification.DEFAULT_SOUND;
	      //Adding the Custom Sound
	        notification.audioStreamType = AudioManager.STREAM_NOTIFICATION;
	        String uri = "android.resource://org.openmobster.notify.android.app/"+MainGLActivity.this.findSoundId(MainGLActivity.this, "beep");
	        notification.sound = Uri.parse(uri);
	      //  notification.sound = Uri.fromFile(new File(R.raw.a));
	        notificationManager.notify(id, notification);
	    }
	 
	 public void cancelNotification (int id) {
		 notificationManager.cancel(id);
	 }

	 private int findSoundId(Activity activity, String variable)
		{
			try
			{
				String idClass = activity.getPackageName()+".R$raw";
				Class clazz = Class.forName(idClass);
				Field field = clazz.getField(variable);
				
				return field.getInt(clazz);
			}
			catch(Exception e)
			{
				return -1;
			}
		}

}
