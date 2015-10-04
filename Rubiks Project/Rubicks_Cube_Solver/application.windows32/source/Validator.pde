/*
 *
 Variables
 *
 */

int counter[][] = new int[6][2];

boolean valid;


/*
 *
 Methods
 *
 */

public boolean cubeValidate()
{
  valid = true;

  for ( int i = 0; i < 3; i++ )
  {
    for ( int j = 0; j < 3; j++ )
    {
      checkFace(redFace[i][j], i+j);
      checkFace(greenFace[i][j], i+j);
      checkFace(blueFace[i][j], i+j);
      checkFace(orangeFace[i][j], i+j);
      checkFace(yellowFace[i][j], i+j);
      checkFace(whiteFace[i][j], i+j);
    }
  } 

  for ( int i = 0; i < 6; i++ )
  {
    if (counter [i][0]!=9)   
      valid=false;
    if (counter[i][1]!=5)
      valid=false;
    counter[i][0]=0; //resets counters
    counter[i][1]=0;
  }     

  return valid;
}

public void checkFace(int piece, int n)
{
  for ( int i = 0; i < 6; i++ )  
    if ( piece == COLOURS[i] )
    {
      counter[i][0]++;
      if ( n % 2 == 0 )
        counter[i][1]++;
    }
}

