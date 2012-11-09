package com.unlockdisk.android.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.os.IInterface;

import com.unlockdisk.android.opengl.MyGLRenderer;

public class Sphere extends CommonGeometry{

    public Sphere(float radius,int SPHERELONG_SEGMENT,int SPHERELAT_SEGMENT) {
    	//GeometryParameter mGeometryParameter = null;
    	//mGeometryParameter = makeSphereGeometry(1.f, 90, 90);
    	 // initialize vertex byte buffer for shape coordinates

    	initializeBuffer (makeSphereGeometry(radius, SPHERELONG_SEGMENT, SPHERELAT_SEGMENT));
        // prepare shaders and OpenGL program
   /*     int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                                                   vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                                                     fragmentShaderCode);
*/
    //	String shaderName =  "brightness";
      //	mProgram = MyGLRenderer.createShaderProgram(shaderName);
    }
    /*
    private void initializeBuffer ( GeometryParameterInArray tGeometryParameterInArray) {
    	mGeometryParameterInArray = tGeometryParameterInArray;
    	 GLES20.glGenBuffers(2, mVBOid, 0);
    	vertexCount = mGeometryParameterInArray.geometryData.length / COORDS_PER_VERTEX;
    	indexCount = mGeometryParameterInArray.indexData.length;
    	
        if (indexoffset == true) {
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
        } else {
        	
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
          
        // GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBOid[0]);
        //  MyGLRenderer.checkGlError("initShapes 5");
        //  GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,COORDS_PER_VERTEX * 4,vertexBuffer,GLES20.GL_STATIC_DRAW);

          
          ////////////// index ////////////////////
          // initialize byte buffer for the draw list
          ByteBuffer dlb = ByteBuffer.allocateDirect(
          // (# of coordinate values * 2 bytes per short)
          		indexCount * 2);
          dlb.order(ByteOrder.nativeOrder());
          indexBuffer = dlb.asShortBuffer();
          indexBuffer.put(mGeometryParameterInArray.indexData);
          indexBuffer.position(0);
          
       //   GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBOid[1]);
       //   GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,indexCount * 2,indexBuffer,GLES20.GL_STATIC_DRAW);
        	
        	
        }
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
       // Add program to OpenGL environment
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
    	
    }
    */
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
   //     GLES20.glDrawElements(GLES20.GL_TRIANGLES, tIndexCount, GLES20.GL_UNSIGNED_SHORT, tIndexOffset);
        // Disable vertex array
        //GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    
    public static GeometryParameterInArray makeSphereGeometry (float radius, int SPHERELONG_SEGMENT, int SPHERELAT_SEGMENT) {
    	GeometryParameter mGeometryParameter = new GeometryParameter();
    	int NR_OF_SEGMENTS = Math.max(SPHERELAT_SEGMENT, SPHERELONG_SEGMENT);
    	  for (int latNumber = 0; latNumber <= NR_OF_SEGMENTS; latNumber++) {
    	      float theta = (float) (latNumber * (Math.PI) /NR_OF_SEGMENTS);
    	      float sinTheta = (float) Math.sin(theta);
    	      float cosTheta = (float) Math.cos(theta);

    	      for (int longNumber = 0; longNumber <= NR_OF_SEGMENTS; longNumber++) {
    	    	  float phi = (float) (longNumber * 2 * Math.PI / NR_OF_SEGMENTS);
    	    	  float sinPhi = (float) Math.sin(phi);
    	    	  float cosPhi = (float) Math.cos(phi);

    	    	  float x = cosPhi * sinTheta;
    	    	  float y = cosTheta;
    	    	  float z = sinPhi * sinTheta;
    	    	  float u = 1 - (longNumber / NR_OF_SEGMENTS);
    	    	  float v = 1 - (latNumber / NR_OF_SEGMENTS);

    	    	  mGeometryParameter.normalData.add(x);
    	    	  mGeometryParameter.normalData.add(y);
    	    	  mGeometryParameter.normalData.add(z);
    	    	  mGeometryParameter.texCoordData.add(u);
    	    	  mGeometryParameter.texCoordData.add(v);
    	    	  mGeometryParameter.geometryData.add(radius * x);
    	    	  mGeometryParameter.geometryData.add(radius * y);
    	    	  mGeometryParameter.geometryData.add(radius * z);
    	      }

    	    }

    	 for (int latNumber = 0; latNumber < NR_OF_SEGMENTS; latNumber++) {
    	      for (int longNumber = 0; longNumber < NR_OF_SEGMENTS; longNumber++) {
    	    	  int first = (latNumber * (NR_OF_SEGMENTS + 1)) + longNumber;
    	    	  int second = first + NR_OF_SEGMENTS + 1;
    	    	  mGeometryParameter.indexData.add((short)first);
    	    	  mGeometryParameter.indexData.add((short) second);
    	    	  mGeometryParameter.indexData.add((short) (first + 1));

    	    	  mGeometryParameter.indexData.add((short) second);
    	    	  mGeometryParameter.indexData.add((short) (second + 1));
    	    	  mGeometryParameter.indexData.add((short) (first + 1));
    	      }
    	    }
    	 /*
    	    retval.normalObject = ctx.createBuffer();
    	    ctx.bindBuffer(ctx.ARRAY_BUFFER, retval.normalObject);
    	    ctx.bufferData(ctx.ARRAY_BUFFER, new Float32Array(normalData), ctx.STATIC_DRAW);

    	    retval.texCoordObject = ctx.createBuffer();
    	    ctx.bindBuffer(ctx.ARRAY_BUFFER, retval.texCoordObject);
    	    ctx.bufferData(ctx.ARRAY_BUFFER, new Float32Array(texCoordData), ctx.STATIC_DRAW);

    	    retval.vertexObject = ctx.createBuffer();
    	    ctx.bindBuffer(ctx.ARRAY_BUFFER, retval.vertexObject);
    	    ctx.bufferData(ctx.ARRAY_BUFFER, new Float32Array(geometryData), ctx.STATIC_DRAW);

    	   retval.numIndices = indexData.length;
    	   retval.indexObject = ctx.createBuffer();
    	   ctx.bindBuffer(ctx.ELEMENT_ARRAY_BUFFER, retval.indexObject);
    	   ctx.bufferData(ctx.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), ctx.STREAM_DRAW);

    	retval.numVertices = ( NR_OF_SEGMENTS*NR_OF_SEGMENTS);
*/
    	 mGeometryParameter.PRIMITIVE = GLES20.GL_TRIANGLES;
    	 return (new GeometryParameterInArray(mGeometryParameter));
    }
    
    
}
