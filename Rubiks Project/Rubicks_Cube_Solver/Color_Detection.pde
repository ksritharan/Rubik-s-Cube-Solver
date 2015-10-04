/*
*
Variables
*
*/
int[][] colour = new int[3][3];     //Input Color
int[][] realColor = new int[3][3]; //Detected Color

final int COLOURS[] = 
{
  color(255, 0, 0), //red
  color(0, 255, 0), //green
  color(0, 50, 255), //blue
  color(255, 140, 0), //orange
  color(255, 215, 0), //yellow
  color(255, 255, 255) //white
};


/*
*
Methods
*
*/
public int detectColor(int colour)
{
  float[] diff = new float[5];
  float[] values = 
  {
    hue(color(131, 53, 96)), //red
    hue(color(39, 108, 132)), //green
    hue(color(34, 49, 131)), //blue
    hue(color(146, 67, 67)), //orange
    hue(color(152, 147, 100)), //yellow
  };

  float f = hue(colour);
  float s = saturation(colour);

  if ( s < 55 )  
    return color(255, 255, 255);

  float smallestValue = 360;
  int detectedColor = 0;
  for ( int i = 0; i < 5; i++ )
  {
    diff[i] = abs(f-values[i]);
    if ( diff[i] < smallestValue )
    {
      smallestValue = diff[i];
      detectedColor = i;
    }
  }
  return COLOURS[detectedColor];
}

public void setArray(int c)
{    
  switch(c)
  {
  case 0:
    for ( int i = 0; i < 3; i++ )
      for ( int j = 0; j < 3; j++ )
        redFace[i][j] = realColor[i][j];
    break;


  case 1:
    for ( int i = 0; i < 3; i++ )        
      for ( int j = 0; j < 3; j++ )      
        greenFace[i][j] = realColor[i][j];      
    break;


  case 2:
    for ( int i = 0; i < 3; i++ )          
      for ( int j = 0; j < 3; j++ )      
        blueFace[i][j] = realColor[i][j];
    break;


  case 3:
    for ( int i = 0; i < 3; i++ )          
      for ( int j = 0; j < 3; j++ )      
        orangeFace[i][j] = realColor[i][j];     
    break;


  case 4:
    for ( int i = 0; i < 3; i++ )
      for ( int j = 0; j < 3; j++ )     
        yellowFace[i][j] = realColor[i][j];       
    break;


  case 5:
    for ( int i = 0; i < 3; i++ )        
      for ( int j = 0; j < 3; j++ )
        whiteFace[i][j] = realColor[i][j];    
    break;
  }
}

