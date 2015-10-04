/*
 *
 Variables
 *
 */

int vCube[][][] = new int[6][3][3];
int vTemp[][] = new int[3][3];

BufferedWriter output;
String transformations = "";

File file;

/*
 *
 Methods
 *
 */


public void updateVCube()
{

  for ( int i = 0; i < 3; i++ )  
    for ( int j = 0; j < 3; j++ )    
    {
      vCube[0][i][j] = getUFColour(redFace[i][j]); 
      vCube[1][i][j] = getUFColour(greenFace[i][j]);
      vCube[2][i][j] = getUFColour(blueFace[i][j]);
      vCube[3][i][j] = getUFColour(orangeFace[i][j]);
      vCube[4][i][j] = getUFColour(yellowFace[i][j]);
      vCube[5][i][j] = getUFColour(whiteFace[i][j]);
    }
}
/*
Name: GetUserFriendlyColour
Paramaters:
  - int colour : the colour of the piece
Returns UserFriendlyColour from 0-5
*/
public int getUFColour( int colour )
{
    for( int i = 0; i < 6; i++ )
    {
      if( colour == COLOURS[i] )
      {
          return i;
      }
    }
    return -1;
}

/* Rotations of V Cube */


public void vUp()
{
  CW(vCube[4]);

  copyVFace(vCube[3]);
  for ( int i = 0; i < 3; i++ )
  {
    vCube[3][i][0] = vCube[2][i][0];
    vCube[2][i][0] = vCube[0][i][0];
    vCube[0][i][0] = vCube[1][i][0];
    vCube[1][i][0] = vTemp[i][0];
  }
  transformations += "U ";
}

public void vRight()
{
  CW(vCube[0]);

  copyVFace(vCube[4]);
  for ( int i = 0; i < 3; i++ )
  {
    vCube[4][2][i] = vCube[2][2][i];
    vCube[2][2][i] = vCube[5][2][i];
    vCube[5][2][i] = vCube[1][0][2-i];
    vCube[1][0][2-i] = vTemp[2][i];
  }
  transformations += "R ";
}

public void vLeft()
{
  CW(vCube[3]);

  copyVFace(vCube[4]);
  int j = 2;
  for ( int i = 0; i < 3; i++ )
  {
    vCube[4][0][i] = vCube[1][2][j];
    vCube[1][2][j] = vCube[5][0][i];
    vCube[5][0][i] = vCube[2][0][i];
    vCube[2][0][i] = vTemp[0][i];
    j--;
  }
  transformations += "L ";
}

public void vDown()
{
  CW(vCube[5]);

  copyVFace(vCube[3]);
  for ( int i = 0; i < 3; i++ )
  {
    vCube[3][i][2] = vCube[1][i][2];
    vCube[1][i][2] = vCube[0][i][2];    
    vCube[0][i][2] = vCube[2][i][2];
    vCube[2][i][2] = vTemp[i][2];
  }
  transformations += "D ";
}

public void vFront()
{
  CW(vCube[2]);

  copyVFace(vCube[4]);
  int j = 2;
  for ( int i = 0; i < 3; i++ )
  {
    vCube[4][j][2] = vCube[3][2][i];
    vCube[3][2][i] = vCube[5][i][0];    
    vCube[5][i][0] = vCube[0][0][j];
    vCube[0][0][j] = vTemp[j][2];
    j--;
  }
  transformations += "F ";
}

public void vBack()
{
  CW(vCube[1]);

  copyVFace(vCube[4]);
  int j = 2;
  for ( int i = 0; i < 3; i++ )
  {
    vCube[4][i][0] = vCube[0][2][i];
    vCube[0][2][i] = vCube[5][j][2];    
    vCube[5][j][2] = vCube[3][0][j];
    vCube[3][0][j] = vTemp[i][0];
    j--;
  }
  transformations += "B ";
}

public void vUpPrime()
{
  vUp();
  vUp();
  vUp();
}
public void vDownPrime()
{
  vDown();
  vDown();
  vDown();
}

public void vLeftPrime()
{
  vLeft();
  vLeft();
  vLeft();
}

public void vRightPrime()
{
  vRight();
  vRight();
  vRight();
}

public void vFrontPrime()
{
  vFront();
  vFront();
  vFront();
}
public void vBackPrime()
{
  vBack();
  vBack();
  vBack();
}



public void copyVFace(int[][] face)
{
  for ( int i = 0; i < 3; i++ )  
    for ( int j = 0; j < 3; j++ )    
      vTemp[i][j] = face[i][j];
}

public void transformsToFile(String name)
{
  try
  {
    file = new File (name); //new file name

      file.delete (); //clears the file
    file.createNewFile (); //creates a new file

      output = new BufferedWriter(new FileWriter(file, true));
    output.write(transformations);

    //transformations are reset in the solve display class

      output.close();
  }
  catch(Exception e)
  {
  }
}

