/*
*
Variables
*
*/
int redFace[][] = new int[3][3]; 
int greenFace[][] = new int[3][3];
int blueFace[][] = new int[3][3];
int orangeFace[][] = new int[3][3];
int yellowFace[][] = new int[3][3];
int whiteFace[][] = new int[3][3];
int[][] temp = new int[3][3];
int cube[][][] = new int[6][3][3];



/*
*
Methods
*
*/
public void CW(int[][] face)
{
  copyFace(face);
  //Corner CW Rotation
  face[0][0] = (int)temp[0][2];
  face[0][2] = (int)temp[2][2];
  face[2][2] = (int)temp[2][0];
  face[2][0] = (int)temp[0][0];
  //Side CW Rotation
  face[1][0] = (int)temp[0][1];
  face[0][1] = (int)temp[1][2];
  face[1][2] = (int)temp[2][1];
  face[2][1] = (int)temp[1][0];
}

public void Up()
{
  CW(yellowFace);

  copyFace(orangeFace);
  for ( int i = 0; i < 3; i++ )
  {
    orangeFace[i][0] = blueFace[i][0];
    blueFace[i][0] = redFace[i][0];
    redFace[i][0] = greenFace[i][0];
    greenFace[i][0] = temp[i][0];
  }
}

public void Right()
{
  CW(redFace);

  copyFace(yellowFace);
  for ( int i = 0; i < 3; i++ )
  {
    yellowFace[2][i] = blueFace[2][i];
    blueFace[2][i] = whiteFace[2][i];
    whiteFace[2][i] = greenFace[0][2-i];
    greenFace[0][2-i] = temp[2][i];
  }
}

public void Left()
{
  CW(orangeFace);

  copyFace(yellowFace);
  int j = 2;
  for ( int i = 0; i < 3; i++ )
  {
    yellowFace[0][i] = greenFace[2][j];
    greenFace[2][j] = whiteFace[0][i];
    whiteFace[0][i] = blueFace[0][i];
    blueFace[0][i] = temp[0][i];
    j--;
  }
}

public void Down()
{
  CW(whiteFace);

  copyFace(orangeFace);
  for ( int i = 0; i < 3; i++ )
  {
    orangeFace[i][2] = greenFace[i][2];
    greenFace[i][2] = redFace[i][2];    
    redFace[i][2] = blueFace[i][2];
    blueFace[i][2] = temp[i][2];
  }
}

public void Front()
{
  CW(blueFace);

  copyFace(yellowFace);
  int j = 2;
  for ( int i = 0; i < 3; i++ )
  {
    yellowFace[j][2] = orangeFace[2][i];
    orangeFace[2][i] = whiteFace[i][0];    
    whiteFace[i][0] = redFace[0][j];
    redFace[0][j] = temp[j][2];
    j--;
  }
}

public void Back()
{
  CW(greenFace);

  copyFace(yellowFace);
  int j = 2;
  for ( int i = 0; i < 3; i++ )
  {
    yellowFace[i][0] = redFace[2][i];
    redFace[2][i] = whiteFace[j][2];    
    whiteFace[j][2] = orangeFace[0][j];
    orangeFace[0][j] = temp[i][0];
    j--;
  }
}

public void UpPrime()
{
  Up();
  Up();
  Up();
}
public void DownPrime()
{
  Down();
  Down();
  Down();
}

public void LeftPrime()
{
  Left();
  Left();
  Left();
}

public void RightPrime()
{
  Right();
  Right();
  Right();
}

public void FrontPrime()
{
  Front();
  Front();
  Front();
}
public void BackPrime()
{
  Back();
  Back();
  Back();
}

public void copyFace(int[][] face)
{
  for ( int i = 0; i < 3; i++ )  
    for ( int j = 0; j < 3; j++ )    
      temp[i][j] = face[i][j];
}

