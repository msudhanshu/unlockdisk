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

package com.unlockdisk.android.disk;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import com.unlockdisk.android.geometry.Disk;
import com.unlockdisk.android.opengl.CommonGLRenderer;
import com.unlockdisk.android.opengl.Config;
import com.unlockdisk.android.opengl.GameLevelVar.SegmentType;
import com.unlockdisk.android.util.Utility;

public class DiskRenderer extends CommonGLRenderer {
	
	protected float height = 1.f;
	   public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		   //mGeometry = new Sphere(radius,SPHERELONG_SEGMENT,SPHERELAT_SEGMENT);
		   mGeometry = new Disk(radius,height,SPHERELONG_SEGMENT,SPHERELAT_SEGMENT);
             super.onSurfaceCreated(unused, config);
	    }
    public void onDrawFrame(GL10 unused) {
    	super.onDrawFrame(unused);
    }

}


