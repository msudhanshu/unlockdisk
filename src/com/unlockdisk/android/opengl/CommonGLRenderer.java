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
	
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.unlockdisk.android.geometry.CommonGeometry;
import com.unlockdisk.android.geometry.Sphere;
import com.unlockdisk.android.geometry.Triangle;
import com.unlockdisk.android.opengl.GameLevelVar.SegmentType;
import com.unlockdisk.android.util.Utility;

public class CommonGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    protected static final int NEXTSPHERESEGMENT = 6;
	protected float radius = Config.MAXRADIUS;
	protected int SPHERELONG_SEGMENT = 120;
	protected int SPHERELAT_SEGMENT = 120;
	// they should divide long and lat segment length
	public int RingNo = 3;
	public int SliceNo = 3;
	protected  int SliceWidth = SPHERELONG_SEGMENT/SliceNo;
	protected  int RingWidth = SPHERELAT_SEGMENT/RingNo;
	
	protected Triangle mTriangle;
	protected CommonGeometry mGeometry;
   // private Square   mSquare;

    protected final float[] mMVPMatrix = new float[16];
    protected final float[] mProjMatrix = new float[16];
    protected final float[] mVMatrix = new float[16];
    protected final float[] mMVMatrix = new float[16];
    protected final float[] mNormalMatrix = new float[16];
    protected final float[] mRotationMatrix = new float[16];
    protected final float[] mTranslateMatrix = new float[16];
    protected int mPositionHandle;
    protected int mNormalHandle;
    protected int mTextureCoordHandle;
    protected int mColorHandle;
    protected int mMVPMatrixHandle;
    protected int mMVMatrixHandle;
    protected int mNormalMatrixHandle;
    
    protected int allShaderProgram[] = new int[10];
    // Declare as volatile because we are updating it from another thread
    public volatile float mAngleY;
    public volatile float mAngleX;
    public volatile float mTransZ=3;
    
    protected  int  ideltaAnim = 0;
    protected  int  iCurrentStepsCount =0 ;
    
    public int imoveRing=-1;
    public int imoveSlice=-1;
    public static enum KeyPressed {
    	MKEY_UP,
    	MKEY_DOWN,
    	MKEY_RIGHT,
    	MKEY_LEFT,
    	NOKEY
    }
    
     public volatile KeyPressed MoveDir =  KeyPressed.NOKEY;
    
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
     //   mSquare   = new Square();
       // mGeometry = new Sphere(radius,SPHERELONG_SEGMENT,SPHERELAT_SEGMENT);
        Config.initGameLevelVar();
        
        // TODO : do not do it ALL THE TIMES
        initializeAllShaderProgram();
    }

    private void initializeAllShaderProgram () {
    	  // create OpenGL program executables
      	String shaderName =  "light";
      	allShaderProgram[1] = MyGLRenderer.createShaderProgram(shaderName);
    }
    
    public void updateLevel () {
    	
        imoveRing=-1;
        imoveSlice=-1;
    	 RingNo =  Config.mGameLevelVar[0][0].ArenaGridCount[0];
    	 SliceNo = Config.mGameLevelVar[0][0].ArenaGridCount[0];
    	 SliceWidth = SPHERELONG_SEGMENT/SliceNo;
    	 RingWidth = SPHERELAT_SEGMENT/RingNo;
    }
    
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
      //  Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 2.f);
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 10.f, 100.f);
        //  Matrix.setLookAtM(mProjMatrix, 0, 0, 0, -1, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    }
    
    protected void initializeShaderUniformVariable(int mProgram) {
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, 20.f, 0.f, 0.f, 0.f, 0f, 1.0f, 0.0f);
    	Matrix.setIdentityM(mMVMatrix, 0);
        // Calculate the projection and view transformation
       // Matrix.setIdentityM(sm, smOffset)
      //  Matrix.
        // Create a rotation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        
      //  Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
        
        Matrix.setRotateM(mRotationMatrix, 0, mAngleX, 0, 1.f, 0.0f);
       // Matrix.setRotateM(mRotationMatrix, 0, mAngleY, 1.0f, 0.f, 0.0f);
        // Combine the rotation matrix with the projection and camera view
    //    Matrix.multiplyMM(mMVMatrix, 0, mRotationMatrix, 0, mMVMatrix, 0);
        Matrix.setRotateM(mRotationMatrix, 0, mAngleY, 1.0f, 0.f, 0.0f);
        // Combine the rotation matrix with the projection and camera view
    //    Matrix.multiplyMM(mMVMatrix, 0, mRotationMatrix, 0, mMVMatrix, 0);
        
     
        Matrix.multiplyMM(mMVMatrix,0,mVMatrix,0,mMVMatrix,0);
       Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
        
       Matrix.setRotateM(mRotationMatrix, 0, mAngleX, 0, 1.f, 0.0f);
       // Matrix.setRotateM(mRotationMatrix, 0, mAngleY, 1.0f, 0.f, 0.0f);
        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);
        Matrix.setRotateM(mRotationMatrix, 0, mAngleY, -1.0f, 0.f, 0.0f);
        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);
       
     /////////// ???????????????????   
        ///////////////////  Construct the normal matrix from the model-view matrix and pass it in ///////////////// 
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, 20.f, 0.f, 0.f, 0.f, 0f, 1.0f, 0.0f);
    	Matrix.setIdentityM(mMVMatrix, 0);
        // Calculate the projection and view transformation
       // Matrix.setIdentityM(sm, smOffset)
      //  Matrix.
        // Create a rotation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        
      //  Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
        
        Matrix.setRotateM(mRotationMatrix, 0, mAngleX, 0, 1.f, 0.0f);
       // Matrix.setRotateM(mRotationMatrix, 0, mAngleY, 1.0f, 0.f, 0.0f);
        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVMatrix, 0, mRotationMatrix, 0, mMVMatrix, 0);
        Matrix.setRotateM(mRotationMatrix, 0, mAngleY, 1.0f, 0.f, 0.0f);
        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVMatrix, 0, mRotationMatrix, 0, mMVMatrix, 0);
        
        Matrix.invertM(mNormalMatrix, 0, mMVMatrix, 0);
        Matrix.transposeM(mNormalMatrix, 0, mNormalMatrix, 0);
  //      g.normalMatrix.load(g.mvMatrix);
   //     g.normalMatrix.invert();
   //     g.normalMatrix.transpose();
  //      g.normalMatrix.setUniform(gl, g.u_normalMatrixLoc, false);
        
        //////////////////////////////////////////////////////////////
        
        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");
        
    
        // get handle to shape's transformation matrix
        mMVMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");
        
       // get handle to shape's transformation matrix
        mNormalMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uNormalMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mNormalMatrixHandle, 1, false, mNormalMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");
    }
    
    public void onBaseDrawFrame(GL10 unused) {

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        int mProgram = allShaderProgram[1];
        GLES20.glUseProgram(mProgram);

        initializeShaderUniformVariable(mProgram);
        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        int i;
    //    mNormalHandle = GLES20.glGetUniformLocation(mProgram, "vNormal");
    //    MyGLRenderer.checkGlError("glGetUniformLocation");
      //  GLES20.glUniform3fv(mNormalHandle, 1, mGeometry.normalBuffer.array(), 0);
        
       mNormalHandle = GLES20.glGetAttribLocation(mProgram, "v_normal");
       MyGLRenderer.checkGlError("glGetAttribLocation");
       GLES20.glEnableVertexAttribArray(mNormalHandle);
    /*   */     // Prepare the triangle coordinate data
    //    GLES20.gl
       GLES20.glVertexAttribPointer(mNormalHandle, CommonGeometry.COORDS_PER_VERTEX,GLES20.GL_FLOAT, true, mGeometry.vertexStride, mGeometry.normalBuffer);
        //MyGLRenderer.checkGlError("glVertexAttribPointer");
        // GLES20.glDisableVertexAttribArray(mNormalHandle);
       
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "v_position");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, CommonGeometry.COORDS_PER_VERTEX,GLES20.GL_FLOAT, false, mGeometry.vertexStride, mGeometry.vertexBuffer);

    }
    
    public void onDrawFrame(GL10 unused) {
    		onBaseDrawFrame(unused);

        	// GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mGeometry.mVBOid[1]);
    ////////////////////////////loop started //////////////////////////////
    for (int iring = 0; iring < RingNo ; ++iring) {

    //some rotation animation
    //if (iCompletionFlag == COMPLETIONANIM)
    //{
    //goNextLevel();
    //iCompletionFlag == NEWSTARTANIM
    //}
    for (int islice = 0; islice < SliceNo ; ++islice) {

          if (Config.mGameLevelVar[0][0].ArenaGrid[iring][islice] == SegmentType.VOID )
          {continue;
          }else {
        	  float tColor[] = Config.mGameLevelVar[0][0].ArenaColor[Config.mGameLevelVar[0][0].ArenaGrid[iring][islice].ordinal()];
        	/*float tColor[] = {Config.mGameLevelVar[0][0].ArenaColor[0][0] ,
        			Config.mGameLevelVar[0][0].ArenaColor[0][1] ,
        			Config.mGameLevelVar[0][0].ArenaColor[0][2] ,
        			Config.mGameLevelVar[0][0].ArenaColor[0][3] };*/
          
          // Set color for drawing the triangle
          GLES20.glUniform4fv(mColorHandle, 1, tColor, 0);
         } 
          
          
      	float delta = 360.0f/SliceNo;
      	float iInnerRadius = (Config.MAXRADIUS/RingNo*iring);
      	float iOuterRadius =  ((Config.MAXRADIUS/RingNo*(iring+1)) - Config.GAP);
      	float iStartAngle = islice*delta;
      	float iEndAngle = ((islice+1)*delta);

    	   if (iring == imoveRing && islice==imoveSlice && MoveDir != KeyPressed.NOKEY)
    	   {

    //for shere  up key is mapped with down and vice versa
    if (MoveDir == KeyPressed.MKEY_UP)
    {
    ++ideltaAnim;
    iInnerRadius = iInnerRadius + ideltaAnim*Config.ANIMRATE_RADIUS;
    iOuterRadius = iOuterRadius + ideltaAnim*Config.ANIMRATE_RADIUS;
    	//   if (iInnerRadius >= (Config.MAXRADIUS/RingNo*(iring+1)))
    	   {
    	   //movement done .. animation stop update currentAren
    	   ideltaAnim = 0;
    	   iCurrentStepsCount++;
    	   Config.iTotalStepsCount++;
          MoveDir = KeyPressed.NOKEY;
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing+1][imoveSlice]=Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice];
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice] = SegmentType.VOID;

    	   checkForCompletion();
    	   }
    }



    if (MoveDir == KeyPressed.MKEY_DOWN)
    {
    ++ideltaAnim;
    iInnerRadius = iInnerRadius - ideltaAnim*Config.ANIMRATE_RADIUS;
    iOuterRadius = iOuterRadius - ideltaAnim*Config.ANIMRATE_RADIUS;
    	 //  if (iInnerRadius <= (Config.MAXRADIUS/RingNo*(iring-1)))
    	   {
    	   //movement done .. animation stop update currentAren
    	   ideltaAnim = 0;
    	   	   iCurrentStepsCount++;
    	   	Config.iTotalStepsCount++;
          MoveDir = KeyPressed.NOKEY;
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing-1][imoveSlice]=Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice];
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice] = SegmentType.VOID;

    	   checkForCompletion();
    	   }
    }


    if (MoveDir == KeyPressed.MKEY_RIGHT)
    {
    ++ideltaAnim;
    iStartAngle = iStartAngle - ideltaAnim*Config.ANIMRATE_ANGLE;
    iEndAngle =  iEndAngle - ideltaAnim*Config.ANIMRATE_ANGLE;
    	 //  if (iStartAngle <= (islice-1)*delta)
    	   {
    	   //movement done .. animation stop update currentAren
    	   ideltaAnim = 0;
    	   	   iCurrentStepsCount++;
    	   	Config.iTotalStepsCount++;
          MoveDir = KeyPressed.NOKEY;
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][Utility.CircularMinus(imoveSlice,SliceNo-1)]=Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice];
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice] = SegmentType.VOID;

    	   checkForCompletion();
    	   }
    }


    if (MoveDir == KeyPressed.MKEY_LEFT)
    {
    ++ideltaAnim;
    iStartAngle = iStartAngle + ideltaAnim*Config.ANIMRATE_ANGLE;
    iEndAngle =  iEndAngle + ideltaAnim*Config.ANIMRATE_ANGLE;
    	//   if (iStartAngle >= (islice+1)*delta)
    	   {
    	   //movement done .. animation stop update currentAren
    	   ideltaAnim = 0;
    	   iCurrentStepsCount++;
    	  Config.iTotalStepsCount++;
          MoveDir = KeyPressed.NOKEY;
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][Utility.CircularPlus(imoveSlice,SliceNo-1)]=Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice];
          Config.mGameLevelVar[0][0].ArenaGrid[imoveRing][imoveSlice] = SegmentType.VOID;

    	   checkForCompletion();
    	   }
    }
    	   } // for that fragment which will move 
    	   
          for (int k = 0; k < RingWidth; k++)
    		{

    	        mGeometry.updateSubIndexBuffer(NEXTSPHERESEGMENT*SliceWidth, NEXTSPHERESEGMENT*(((iring)*RingWidth+k)*SliceNo+islice)*SliceWidth);
    			 GLES20.glDrawElements(mGeometry.mGeometryParameterInArray.PRIMITIVE, mGeometry.indexCount, GLES20.GL_UNSIGNED_SHORT, mGeometry.indexBuffer);
    		//  GLES20.glDrawElements(GLES20.GL_TRIANGLES, mGeometry.indexCount, GLES20.GL_UNSIGNED_SHORT,0);
    		// GLES20.glDrawElements(GLES20.GL_TRIANGLES, NEXTSPHERESEGMENT*SliceWidth, GLES20.GL_UNSIGNED_SHORT, 2*NEXTSPHERESEGMENT*(((iring)*RingWidth+k)*SliceNo+islice)*SliceWidth);
    		
    		}
    }
    }
    GLES20.glDisableVertexAttribArray(mNormalHandle);
    GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


    
   public void checkForCompletion() {
    /*	var RingNo =  ArenaGridCount[iLevel][iSubLevel][1];
    	var SliceNo = ArenaGridCount[iLevel][iSubLevel][0];
    	 int slicevalflag=-1;

    	for (int i = 0; i < RingNo; i++)
    			{

    	slicevalflag=-1;
    	           for (int j = 0; j < SliceNo; j++) 
    			   {

    				 if (ArenaGrid[iLevel][iSubLevel][i][j] != SegmentType.VOID)
    				   {
    					 //if slicevalflag is -1 it means it starting so ok and initialize it, otherwise slicevalflag should be equal to current one.
    					if (slicevalflag == -1)
    					     {
    					    	slicevalflag = ArenaGrid[iLevel][iSubLevel][i][j];
    					      }
    					else //otherwise slicevalflag should be equal to current one.
    						  {
    								 if (slicevalflag != ArenaGrid[iLevel][iSubLevel][i][j] )
    									{
    									 
    										return false;
    									 }
    						   }
    				   }


    			   }
    	        }
    			PosOfVoid ();
    			CodeOfVoid (); // redundant
    	iCompletionFlag = JUSTCOMPLETE;
    	//
    	//find and //fill the voidspace with the appropriate color
    	icurrentArena[iPosOfVoid[0]][iPosOfVoid[1]] = iCodeOfVoid;
    			//for temp ..... ow we will add animation on completion of this rather than opening new level directly
    			//goNextLevel();
    			return true;*/
	 }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}


