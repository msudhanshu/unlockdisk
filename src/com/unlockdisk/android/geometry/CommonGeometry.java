package com.unlockdisk.android.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

import com.unlockdisk.android.opengl.Config;

public class CommonGeometry {
    public FloatBuffer vertexBuffer;
    public FloatBuffer normalBuffer;
    public FloatBuffer textureBuffer;
    public ShortBuffer indexBuffer; 
  //  public 
    public  GeometryParameterInArray mGeometryParameterInArray;
    public boolean indexoffset = false;
    public int[] mVBOid = new int[4];     
    // number of coordinates per vertex in this array
    public static final int COORDS_PER_VERTEX = 3;
   
    public  int vertexCount = 0;
    public  int indexCount = 0;
    public final int vertexStride = COORDS_PER_VERTEX * Config.FLOAT_BYTE_SIZE; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public CommonGeometry() {
		// TODO Auto-generated constructor stub
	}
    
    public CommonGeometry(float radius,int SPHERELONG_SEGMENT,int SPHERELAT_SEGMENT) {
    }
    
   protected void initializeBuffer ( GeometryParameterInArray tGeometryParameterInArray) {
    	mGeometryParameterInArray = tGeometryParameterInArray;
    	vertexCount = mGeometryParameterInArray.geometryData.length / COORDS_PER_VERTEX;
    	indexCount = mGeometryParameterInArray.indexData.length;
    	
        if (indexoffset == false) {
///////////////////// vertex  ///////////////////
	  // initialize vertex byte buffer for shape coordinates
  ByteBuffer bb = ByteBuffer.allocateDirect(
          // (number of coordinate values * 4 bytes per float)
  		mGeometryParameterInArray.geometryData.length * Config.FLOAT_BYTE_SIZE);
  // use the device hardware's native byte order
  bb.order(ByteOrder.nativeOrder());
  // create a floating point buffer from the ByteBuffer
  vertexBuffer = bb.asFloatBuffer();
  // add the coordinates to the FloatBuffer
  vertexBuffer.put(mGeometryParameterInArray.geometryData);
 // vertexBuffer.put((Float [])mGeometryParameter.geometryData.toArray());
  // set the buffer to read the first coordinate
  vertexBuffer.position(0);

///////////////////// normal  ///////////////////
// initialize vertex byte buffer for shape coordinates
ByteBuffer bbnormal = ByteBuffer.allocateDirect(
// (number of coordinate values * 4 bytes per float)
mGeometryParameterInArray.normalData.length * Config.FLOAT_BYTE_SIZE);
// use the device hardware's native byte order
bbnormal.order(ByteOrder.nativeOrder());
// create a floating point buffer from the ByteBuffer
normalBuffer = bbnormal.asFloatBuffer();
// add the coordinates to the FloatBuffer
normalBuffer.put(mGeometryParameterInArray.normalData);
// vertexBuffer.put((Float [])mGeometryParameter.geometryData.toArray());
// set the buffer to read the first coordinate
normalBuffer.position(0);

///////////////////// texture  ///////////////////
//initialize vertex byte buffer for shape coordinates
ByteBuffer bbtexture = ByteBuffer.allocateDirect(
//(number of coordinate values * 4 bytes per float)
mGeometryParameterInArray.texCoordData.length * Config.FLOAT_BYTE_SIZE);
//use the device hardware's native byte order
bbtexture.order(ByteOrder.nativeOrder());
//create a floating point buffer from the ByteBuffer
textureBuffer = bbtexture.asFloatBuffer();
//add the coordinates to the FloatBuffer
textureBuffer.put(mGeometryParameterInArray.texCoordData);
//vertexBuffer.put((Float [])mGeometryParameter.geometryData.toArray());
//set the buffer to read the first coordinate
textureBuffer.position(0);

  ////////////// index ////////////////////
  // initialize byte buffer for the draw list
  ByteBuffer dlb = ByteBuffer.allocateDirect(
  // (# of coordinate values * 2 bytes per short)
  		indexCount * Config.SHORT_BYTE_SIZE);
  dlb.order(ByteOrder.nativeOrder());
  indexBuffer = dlb.asShortBuffer();
  indexBuffer.put(mGeometryParameterInArray.indexData);
  indexBuffer.position(0);
        }
        
        /*else {       	
         *     	 GLES20.glGenBuffers(2, mVBOid, 0);
        	///////////////////// vertex  ///////////////////
        	  // initialize vertex byte buffer for shape coordinates
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (number of coordinate values * 4 bytes per float)
            		mGeometryParameterInArray.geometryData.length * 4);
            // use the device hardware's native byte order
            bb.order(ByteOrder.nativeOrder());

            // create a floating point buffer from the ByteBuffer
            vertexBuffer = bb.asFloatBuffer();
            // add the coordinates to the FloatBuffer
            vertexBuffer.put(mGeometryParameterInArray.geometryData);
           // vertexBuffer.put((Float [])mGeometryParameter.geometryData.toArray());
            // set the buffer to read the first coordinate
            vertexBuffer.position(0);
            
           GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBOid[0]);
            MyGLRenderer.checkGlError("initShapes 5");
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,
            		COORDS_PER_VERTEX * 4,
                    vertexBuffer,
                    GLES20.GL_STATIC_DRAW);
            MyGLRenderer.checkGlError("initShapes 5");
            
            ////////////// index ////////////////////
            // initialize byte buffer for the draw list
            ByteBuffer dlb = ByteBuffer.allocateDirect(
            // (# of coordinate values * 2 bytes per short)
            		indexCount * 2);
            dlb.order(ByteOrder.nativeOrder());
            indexBuffer = dlb.asShortBuffer();
            indexBuffer.put(mGeometryParameterInArray.indexData);
            indexBuffer.position(0);
            
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBOid[1]);
            MyGLRenderer.checkGlError("initShapes 5");
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,
            		indexCount * 2,
                    indexBuffer,
                    GLES20.GL_STATIC_DRAW);
            MyGLRenderer.checkGlError("initShapes 5");
        }
        */
    }

    public void updateSubIndexBuffer(int tIndexCount, int tIndexOffset) {
        ////////////// index ////////////////////
        // initialize byte buffer for the draw list
    	indexCount = tIndexCount;
        ByteBuffer dlb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 2 bytes per short)
        		indexCount * 2);
        dlb.order(ByteOrder.nativeOrder());
        indexBuffer = null;
        indexBuffer = dlb.asShortBuffer();
      
        indexBuffer.put(copySubArray(mGeometryParameterInArray.indexData,tIndexCount,tIndexOffset));
        indexBuffer.position(0);
    }
    
    private short[] copySubArray (short[] tIndexData,int tIndexCount, int tIndexOffset) {
    	  short[] mindexData = new short[tIndexCount];
    	for (int i =0 ; i < tIndexCount ;i++)
    		mindexData[i]= tIndexData[tIndexOffset+i];
    	
    	//indexCount = mindexData.length;
    	  return mindexData;
    }
    public void initializeShaderVariable () {
  /*      // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");
    */	
    }
    
    public void draw(float[] mMVPMatrix) {
        // Add program to OpenGL environment
    /*    GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");
*/
        // Draw the triangle
      //  GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
      GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);   //( , first, count)(GLES20.GL_TRIANGLES, 0, vertexCount);
       //   GLES20.glDrawElements(GLES20.GL_TRIANGLES, tIndexCount, GLES20.GL_UNSIGNED_SHORT, tIndexOffset);
        // Disable vertex array
     //   GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    
    public void draw(float[] mMVPMatrix,int mProgram,float[] tColor,int tIndexCount, int tIndexOffset) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

     

        // Draw the triangle
      //  GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
      //  GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);   //( , first, count)(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, tIndexCount, GLES20.GL_UNSIGNED_SHORT, tIndexOffset);
        // Disable vertex array
        //GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    
}
