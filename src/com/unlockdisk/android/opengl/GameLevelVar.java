package com.unlockdisk.android.opengl;

import com.unlockdisk.android.util.IntArray2;
import com.unlockdisk.android.util.Utility;

public class GameLevelVar {
    public static enum SegmentType {
        FIRST,          
        SECOND,   
        THIRD,
        FOURTH,
        VOID,  // THE PLACE WHERE THERE IS VOID IE NO SEGMENT PIECE
        NOTUSED// 4TH CELL WILL BE VOID IN CASE OF 3
    }

    private int Level;
    private int subLevel;
	public int[] ArenaGridCount = new int[2];
	public  SegmentType[][] ArenaGrid;// = new int[4][4];
    public float[][] ArenaColor;
    
	public GameLevelVar() {
		// TODO Auto-generated constructor stub
	}
	
	public GameLevelVar(int Level,int subLevel) {
		// TODO Auto-generated constructor stub
		this.Level = Level;
		this.subLevel = subLevel;
		initArenaGridCount();
		initArenaGrid();
		initArenaColor();
	}
	
	private void initArenaColor()
	{
		  switch(Level) {
		     
		     case 0: 
		    	 switch(subLevel) {
		         case 0: 
		        	 ArenaColor = new float[][]{ 
		        			 { 0.f , 0.f , 1.f, 1.f}, 
		        			 { 0.f , 1.f  , 0.f, 1.0f}, 
		        			 {  1.f ,0.0f  ,0.1f, 1.f }, 
		        			 { 1.f ,0.5f  ,0.5f, 1.f }
		        			 } ;
		        	 break;
		         case 1:
		        	 
		        	 break;
		         case 2:
		        	 
		        	 break;
		        	 
		        	 default:
		        		 break;
		         }
		    	 break;
		     case 1:
		    	 switch(Level) {
		         
		         case 0: 
		        	 
		        	 break;
		         case 1:
		        	 
		        	 break;
		         case 2:
		        	 
		        	 break;
		        	 
		        	 default:
		        		 break;
		         }
		    	 break;
		     case 2:
		    	 switch(Level) {
		         
		         case 0: 
		        	 
		        	 break;
		         case 1:
		        	 
		        	 break;
		         case 2:
		        	 
		        	 break;
		        	 
		        	 default:
		        		 break;
		         }
		    	 break;
		    	 
		    	 default:
		    		 break;
		   
		    
		     }
		     
	
	}
	
	private void initArenaGridCount()
	{

		if (Level==0 || Level==2) {
		ArenaGridCount[0]=3;
		ArenaGridCount[1]=3;
		}
		else {
			ArenaGridCount[0]=4;
			ArenaGridCount[1]=4;
		}
	}
	
	private void initArenaGrid()
	{
     switch(Level) {
     
     case 0: 
    	 switch(subLevel) {
         case 0: 
        	 ArenaGrid = new SegmentType[][]{
        			 {  SegmentType.THIRD , SegmentType.SECOND , SegmentType.FIRST,SegmentType.NOTUSED}, 
        			 {SegmentType.SECOND ,  SegmentType.VOID ,  SegmentType.FIRST ,SegmentType.NOTUSED}, 
        			 { SegmentType.FIRST , SegmentType.THIRD , SegmentType.SECOND, SegmentType.NOTUSED} ,
        			 { SegmentType.NOTUSED , SegmentType.NOTUSED , SegmentType.NOTUSED, SegmentType.NOTUSED}
        			 } ;
        	 break;
         case 1:
        	 
        	 break;
         case 2:
        	 
        	 break;
        	 
        	 default:
        		 break;
         }
    	 break;
     case 1:
    	 switch(Level) {
         
         case 0: 
        	 
        	 break;
         case 1:
        	 
        	 break;
         case 2:
        	 
        	 break;
        	 
        	 default:
        		 break;
         }
    	 break;
     case 2:
    	 switch(Level) {
         
         case 0: 
        	 
        	 break;
         case 1:
        	 
        	 break;
         case 2:
        	 
        	 break;
        	 
        	 default:
        		 break;
         }
    	 break;
    	 
    	 default:
    		 break;
   
    
     }
     
	}
	
	
	

public static IntArray2 RightOfVoid (SegmentType[][] ArenaGrid,int RingNo, int SliceNo)
{
	int imoveRing=-1;
     int imoveSlice=-1;

for (int i = 0; i < RingNo; i++)
		{
           for (int j = 0; j < SliceNo; j++) 
		   {
		      if (ArenaGrid[i][j] == SegmentType.VOID)
		      {
                imoveRing = i;

				imoveSlice=Utility.CircularMinus(j,SliceNo-1);   
				
				return new IntArray2(i, Utility.CircularMinus(j,SliceNo-1));
		      } 
		   
		   }
        }

return new IntArray2(imoveRing,imoveSlice);
}


public static IntArray2 LeftOfVoid (SegmentType[][] ArenaGrid,int RingNo, int SliceNo)
{
	int imoveRing=-1;
     int imoveSlice=-1;

for (int i = 0; i < RingNo; i++)
		{
           for (int j = 0; j < SliceNo; j++) 
		   {
		      if (ArenaGrid[i][j] == SegmentType.VOID)
		      {
				
				return new IntArray2(i, Utility.CircularPlus(j,SliceNo-1));
		      } 
		   
		   }
        }

return new IntArray2(imoveRing,imoveSlice);
}


public static IntArray2 UpOfVoid (SegmentType[][] ArenaGrid,int RingNo, int SliceNo)
{
	int imoveRing=-1;
     int imoveSlice=-1;

for (int i = 0; i < RingNo; i++)
		{
           for (int j = 0; j < SliceNo; j++) 
		   {
		      if (ArenaGrid[i][j] == SegmentType.VOID)
		      {
			      imoveRing = i+1;
				   imoveSlice = j;
                  if (i == (RingNo-1))
                  {
                   imoveRing = -1;
                  }
				
				return new IntArray2(imoveRing,imoveSlice);
		      } 
		   
		   }
        }

return new IntArray2(imoveRing,imoveSlice);
}

public static IntArray2 DownOfVoid (SegmentType[][] ArenaGrid,int RingNo, int SliceNo)
{
	int imoveRing=-1;
     int imoveSlice=-1;

for (int i = 0; i < RingNo; i++)
		{
           for (int j = 0; j < SliceNo; j++) 
		   {
		      if (ArenaGrid[i][j] == SegmentType.VOID)
		      {
		    	  imoveRing = i-1;
				   imoveSlice = j;
				   //boundry point
                  if (i == 0)
                  {
                   imoveRing = -1;
                  }
				return new IntArray2(imoveRing,imoveSlice);
		      } 
		   
		   }
        }

return new IntArray2(imoveRing,imoveSlice);
}

}
