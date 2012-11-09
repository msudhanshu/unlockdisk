package com.unlockdisk.android.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.unlockdisk.android.opengl.MainGLActivity;

public class Utility {
	
	 public static String fileToString (String filename) {
	    	StringBuffer buffer = new StringBuffer();
			BufferedReader input = null;
			
			// AssetManager assetManager = getAssets();
			// InputStream  inputStream = getResources().openRawResource(R.raw.brightness.frag)
			try {
			//  input = new BufferedReader(new InputStreamReader(openFileInput(filename)));
				  //input = new BufferedReader(new FileReader(filename));
				
				  input = new BufferedReader(new InputStreamReader( MainGLActivity.assetManager.open(filename)  ));
			  String line; 
			  buffer.delete(0, buffer.length());
			  while ((line = input.readLine()) != null) {
				buffer.append(line);
			  }
			
			} catch (Exception e) {
			 	e.printStackTrace();
			} finally {
			if (input != null) {
			  try {
				input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
			}
			
			return buffer.toString();
			
	    }
	 
	 
	 public static int CircularPlus (int i ,int MAX )
	 {
	 i++;
	 if (i > MAX)
	 {
	 	i = 0;
	 }
	 return i;
	 }


	 public static int  CircularMinus (int i , int MAX )
	 {
	 i--;
	 if (i < 0)
	 {
	 	i = MAX;
	 }
	 return i;
	 }
	 
	 void checkForCompletion() {
		 
	 }
	 
}
