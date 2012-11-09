package com.unlockdisk.android.geometry;

import java.util.ArrayList;
import java.util.List;

public class GeometryParameter {
	
	public List<Float> geometryData ;
	public List<Float> normalData;
	public List<Float> texCoordData;
	public List<Short> indexData;
	public int PRIMITIVE;
	
	GeometryParameter() {
		geometryData = new ArrayList<Float>();	
		normalData = new ArrayList<Float>();
		texCoordData = new ArrayList<Float>();
		indexData = new ArrayList<Short>();
	}
}
