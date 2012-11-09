package com.unlockdisk.android.geometry;

import java.util.ArrayList;
import java.util.List;

public class GeometryParameterInArray {
	
	public float geometryData [];
	public float normalData [];
	public float texCoordData [];
	public short indexData [];
	public int PRIMITIVE;
	
	GeometryParameterInArray(GeometryParameter mGeometryParameter) {
		geometryData = new float[mGeometryParameter.geometryData.size()];
		for (int i = 0; i < mGeometryParameter.geometryData.size(); i++) {
		    Float f = mGeometryParameter.geometryData.get(i);
		    geometryData[i] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		normalData = new float[mGeometryParameter.normalData.size()];
		for (int i = 0; i < mGeometryParameter.normalData.size(); i++) {
		    Float f = mGeometryParameter.normalData.get(i);
		    normalData[i] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		texCoordData = new float[mGeometryParameter.texCoordData.size()];
		for (int i = 0; i < mGeometryParameter.texCoordData.size(); i++) {
		    Float f = mGeometryParameter.texCoordData.get(i);
		    texCoordData[i] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		indexData = new short[mGeometryParameter.indexData.size()];
		for (int i = 0; i < mGeometryParameter.indexData.size(); i++) {
			Short f = mGeometryParameter.indexData.get(i);
		    indexData[i] = (f != null ? f : 0); // Or whatever default you want.
		}
		
		PRIMITIVE = mGeometryParameter.PRIMITIVE;
	}
}
