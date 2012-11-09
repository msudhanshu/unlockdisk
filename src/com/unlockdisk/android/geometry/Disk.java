package com.unlockdisk.android.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.os.IInterface;

import com.unlockdisk.android.opengl.MyGLRenderer;

public class Disk extends CommonGeometry{

    public Disk(float radius,float height,int SPHERELONG_SEGMENT,int SPHERELAT_SEGMENT) {
    	initializeBuffer (makeDiskGeometry(radius, height, SPHERELONG_SEGMENT, SPHERELAT_SEGMENT));
    }
 
    public void draw(float[] mMVPMatrix) {
      GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);   //( , first, count)(GLES20.GL_TRIANGLES, 0, vertexCount);
       //   GLES20.glDrawElements(GLES20.GL_TRIANGLES, tIndexCount, GLES20.GL_UNSIGNED_SHORT, tIndexOffset);
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
    
    public static GeometryParameterInArray makeDiskGeometry (float radius, float height, int SPHERELONG_SEGMENT, int SPHERELAT_SEGMENT) {
    	GeometryParameter mGeometryParameter = new GeometryParameter();
    	int NR_OF_SEGMENTS = Math.max(SPHERELAT_SEGMENT, SPHERELONG_SEGMENT);
    	  for (int latNumber = 0; latNumber <= NR_OF_SEGMENTS; latNumber++) {
    	      float currentRadius = (float) (latNumber * (radius) /NR_OF_SEGMENTS);
    	    //  float sinTheta = (float) Math.sin(theta);
    	     // float cosTheta = (float) Math.cos(theta);
              //float deltaRadius = ;
          //    float currentRadius = ;
    	      for (int longNumber = 0; longNumber <= NR_OF_SEGMENTS; longNumber++) {
    	    	  float phi = (float) (longNumber * 2 * Math.PI / NR_OF_SEGMENTS);
    	    	  float sinPhi = (float) Math.sin(phi);
    	    	  float cosPhi = (float) Math.cos(phi);

    	    	  float x = cosPhi ;
    	    	  float y = sinPhi ;
    	    	  float z = 0.f;
    	    	  float u = 1 - (longNumber / NR_OF_SEGMENTS);
    	    	  float v = 1 - (latNumber / NR_OF_SEGMENTS);

    	    	  mGeometryParameter.normalData.add(x);
    	    	  mGeometryParameter.normalData.add(y);
    	    	  mGeometryParameter.normalData.add(z);
    	    	  mGeometryParameter.texCoordData.add(u);
    	    	  mGeometryParameter.texCoordData.add(v);
    	    	  mGeometryParameter.geometryData.add(currentRadius * x);
    	    	  mGeometryParameter.geometryData.add(currentRadius * y);
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
    	 mGeometryParameter.PRIMITIVE = GLES20.GL_TRIANGLES;
    	 return (new GeometryParameterInArray(mGeometryParameter));
    }
    
    
}
