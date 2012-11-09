package com.unlockdisk.android.opengl;

public class Config {

	public static final String SHADERPATH = "shaders/";
	
	/**
	 * 
	 */

	public static final int FLOAT_BYTE_SIZE = 4;
	public static final int SHORT_BYTE_SIZE = 2;

	public static final String CRITTERCISM_KEY = "5093f0ecbdbaea54a4000003";
	public static int LevelNo = 4;
	public static int SubLevelNo = 3;
	//animation speed
	public static float ANIMRATE_RADIUS = 0.1f;
	public static float ANIMRATE_ANGLE = 10;
	 public static float DRAG_THRESH = 3.f;
	public static  float GAP = 0.03f;
	public static float MAXRADIUS = 1.2f;

	public static int iTotalStepsCount = 0;
	
	public static GameLevelVar[][] mGameLevelVar= new GameLevelVar[LevelNo][SubLevelNo];
	public static float[][][] ArenaGridCount = new float[LevelNo][SubLevelNo][2];
	
	 // for (int i = 0; i < LevelNo; i++) {
		 // ArenaGridCount[i] = new Array(SubLevelNo);
	 // }
	public static void initGameLevelVar()
	{
		  for (int i=0;i<LevelNo;i++)
				for (int j=0;j<SubLevelNo;j++) {
					mGameLevelVar[i][j] = new GameLevelVar(i, j);
				}
	
	}
	private void initArenaGridCount()
	{
		//ArenaGridCount[0][0][0] = 3;
    for (int i=0;i<LevelNo;i++)
	for (int j=0;j<SubLevelNo;j++) {
		
		if (i==0 || i==2) {
		ArenaGridCount[i][j][0]=3;
		ArenaGridCount[i][j][1]=3;
		}
		else {
			ArenaGridCount[i][j][0]=4;
			ArenaGridCount[i][j][1]=4;
		}
	  }
	}

	
	public static int[][][][] ArenaGrid = new int[LevelNo][SubLevelNo][4][4];
	
	private void initArenaGrid() {
		//ring 1       //ring 2       //ring3

	}
	
	public static void dispose() {
		 iTotalStepsCount = 0;
		 GameLevelVar[][] mGameLevelVar = null;
	}

	/*ArenaGrid[0][1] = [[  0 , 2 , 1], [ 2 ,  VoidCode , 1], [ 1 , 0 , 2] ] ;
	ArenaGrid[0][2] = [[ 1 , 2 ,VoidCode], [ 0 , 2 , 1], [ 1 ,  0 , 2] ] ;

	ArenaGrid[1][0] = [[ 1 , 2 , VoidCode , 3], [3, 0 , 2 , 1], [ 1 , 2 , 0, 1], [ 0 , 2 , 0, 3] ] ;
	ArenaGrid[1][1] = [[3, 0 , 2 , 1], [ 0 , 2 , 0, 3], [ 1 , 2 , 0, 1], [ 1 , 2 , VoidCode , 3]] ;
	ArenaGrid[1][2] = [[ 0 , 2 , 0, 3], [3, 0 , 2 , 1], [ 1 , 2 , VoidCode , 3],[ 1 , 2 , 0, 1]] ;

	ArenaGrid[2][0] = [[ 1 , 2 , VoidCode], [ 0 , 2 , 1], [ 1 , 0 , 0] ] ;
	ArenaGrid[2][1] = [[  0 , 2 , 1], [ 2 ,  VoidCode , 1], [ 1 , 0 , 2] ] ;
	ArenaGrid[2][2] = [[ 1 , 2 ,VoidCode], [ 0 , 2 , 1], [ 1 ,  0 , 2] ] ;

	ArenaGrid[3][0] = [[ 1 , 2 , VoidCode , 3], [3, 0 , 2 , 1], [ 1 , 2 , 0, 1], [ 0 , 2 , 0, 3] ] ;
	ArenaGrid[3][1] = [[3, 0 , 2 , 1], [ 0 , 2 , 0, 3], [ 1 , 2 , 0, 1], [ 1 , 2 , VoidCode , 3]] ;
	ArenaGrid[3][2] = [[ 0 , 2 , 0, 3], [3, 0 , 2 , 1], [ 1 , 2 , VoidCode , 3],[ 1 , 2 , 0, 1]] ;


	float ArenaColor = new Array(LevelNo);
	  for (int i = 0; i < LevelNo; i++) {
		  ArenaColor[i] = new Array(SubLevelNo);
	  }
	  //each triplet contains rgba value of one color code of the element of ArenaGrid
	ArenaColor[0][0] = [[ 1 , 1 , 1, 1], [ 0 , 1  , 0, 0.8], [  1 ,0.4  ,0.1, 1 ], [ 1 ,0.5  ,0.5, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[0][1] = [[ 1 , 0 , 0, 0.7], [ 0  , 1  , 0, .5], [ 0 ,0  ,1, 0.9 ], [ 1 ,0.5  ,0.5, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[0][2] = [[ 1 , 0.1 , 0, 1], [ 0.8  , 0.1  , 1, 1], [ 1 ,0.9  ,0.1, 1 ], [ 1 ,0.5  ,0.5, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;

	ArenaColor[1][0] = [[ 1 , 0.5 , 1, 1], [ 1  , 0.0  ,0, 1], [ 0  , 0.8  , 0, .5], [ 0 ,0  ,1, 0.9 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[1][1] = [[ 0 , 1  , 0, 0.8], [ 0.5  , 0  , 1, 0.5], [ 1 ,0.5  ,0, 0.4 ], [ 1 ,0.1  ,0.3, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[1][2] = [[ 1 , 0 , 1, 0.7], [ 0.5 ,0.9  ,0.3, 1 ], [ 1 ,0.8  ,0.1, 1 ], [ 0.5 ,0.1  ,0.3, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;

	  //each triplet contains rgba value of one color code of the element of ArenaGrid
	ArenaColor[2][0] = [[ 1 , 1 , 0.5, 1], [ 0 , 0.7  , 0, 0.8], [  1 ,0.4  ,0.1, 1 ], [ 1 ,0.5  ,0.5, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[2][1] = [[ 1 , 0 , 0, 0.7], [ 0  , 1  , 0, .5], [ 0 ,0  ,1, 0.9 ], [ 1 ,0.5  ,0.5, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[2][2] = [[ 1 , 0.1 , 0, 1], [ 0.8  , 0.1  , 1, 1], [ 1 ,0.9  ,0.1, 1 ], [ 1 ,0.5  ,0.5, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;

	ArenaColor[3][0] = [[ 1 , 0.5 , 1, 1], [ 1  , 0.0  ,0, 1], [ 0  , 0.8  , 0, 1], [ 0 ,0  ,1, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[3][1] = [[ 0.05 , 0.9  , 0, 0.8], [ 0.5  , 0  , 0.7, 0.5], [ 1 ,0.5  ,0, 0.4 ], [ 1 ,0.1  ,0.3, 1 ],[ 1 , 0.5 , 0.7, 1], [ 0.5  , 0.9  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;
	ArenaColor[3][2] = [[ 1 , 0 , 1, 0.7], [ 0.5 ,0.9  ,0.3, 1 ], [ 1 ,0.8  ,0.1, 1 ], [ 0.5 ,0.1  ,0.3, 1 ],[ 1 , 0.5 , 1, 1], [ 0.5  , 0.5  , 1, 1], [ 1 ,0.5  ,0.5, 1 ], [ 1 ,0.5  ,0.5, 1 ] ] ;




*/
	
}

