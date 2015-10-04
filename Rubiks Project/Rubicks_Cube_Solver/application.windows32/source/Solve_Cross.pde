/*
 *
 Constants
 *
 */


final int RED = 0;
final int GREEN = 1;
final int BLUE = 2;
final int ORANGE = 3;
final int YELLOW = 4;
final int WHITE = 5;

final int faceMatch[][] =
{
  //[X][0] is -1 since num is 1,2,3,4
  //-1  Above   Left    Right  Below 
  /*Red   */   {
    -1, YELLOW, BLUE, GREEN, WHITE
  }
  , 
  /*Green */   {
    -1, YELLOW, RED, ORANGE, WHITE
  }
  , 
  /*Blue  */   {
    -1, YELLOW, ORANGE, RED, WHITE
  }
  , 
  /*Orange*/   {
    -1, YELLOW, GREEN, BLUE, WHITE
  }
  , 
  /*Yellow*/   {
    -1, GREEN, ORANGE, RED, BLUE
  }
  , 
  /*White */   {
    -1, BLUE, ORANGE, RED, GREEN
  }
};

final String[] stringColour = {
  "RED", "GREEN", "BLUE", "ORANGE", "YELLOW", "WHITE"
};

final int SIDE = 0;
final int CORNER = 1;

final int DEBUG = 01;

/*
 *
 Variables
 *
 */

int x;
int y;

int pieceColour;
int faces[] = {
  RED, GREEN, BLUE, ORANGE
};
int face;
boolean doneSolving = false;


/*
 *
 Main Solve Cross Method
 *
 */

public void solveCross()
{
  transformations = "";

  int loopCount = 0;

  do
  {
    doneSolving = true;
    //First White
    x = findXOnFace( WHITE, WHITE, SIDE, false );
    y = findYOnFace( WHITE, WHITE, SIDE, false );

    if ( x != -1 )
    {
      doneSolving = false;
      rotateUntilAligned(reverseFaceMatch(WHITE, x, y), WHITE, x, y, WHITE, false);
    }

    do
    {
      x = findXOnFace( YELLOW, WHITE, SIDE, false );
      y = findYOnFace( YELLOW, WHITE, SIDE, false );
      if ( x != -1 )
      {
        doneSolving = false;
        face = reverseFaceMatch(YELLOW, x, y);
        rotateUntilAligned(reverseFaceMatch(YELLOW, x, y), YELLOW, 1, 0, WHITE, false);
        rotateFace(face);
        rotateFace(face);
      }
    }
    while ( x != -1 );


    do
    {
      x = findXOnFace( WHITE, WHITE, SIDE, false );
      y = findYOnFace( WHITE, WHITE, SIDE, false );
      if ( x != -1 )
      {
        doneSolving = false;
        rotateFace(faceMatch[WHITE][((1+x+y*3)/2)]);
        rotateFace(faceMatch[WHITE][((1+x+y*3)/2)]);
        face = vCube[faceMatch[WHITE][((1+x+y*3)/2)]][1][0];
        rotateUntilAligned(vCube[faceMatch[WHITE][((1+x+y*3)/2)]][1][0], YELLOW, 1, 0, WHITE, false);
        rotateFace(face);
        rotateFace(face);
      }
    }
    while ( x != -1 );

    do
    {
      face = findColourOnFaces( faces, 1, 0, WHITE );
      if ( face != -1 )
      {
        doneSolving = false;
        pieceColour = reverseFaceMatch(face, 1, 0);
        rotateUntilAligned( reverseFaceMatch(face, 1, 0), YELLOW, 1, 0, WHITE, true);

        switch(pieceColour)
        {
        case RED:
          ApplySeriesMoves("R D B' D'", "VIRTUAL");
          break;
        case GREEN:
          ApplySeriesMoves("B D L' D'", "VIRTUAL");
          break;
        case BLUE:
          ApplySeriesMoves("F D R' D'", "VIRTUAL");
          break;
        case ORANGE:
          ApplySeriesMoves("L D F' D'", "VIRTUAL");
          break;
        }
      }
    }
    while ( face != -1 );
    {
      face = findColourOnFaces( faces, 0, 1, WHITE );
      if ( face != -1 )
      {
        doneSolving = false;
        pieceColour = reverseFaceMatch(face, 0, 1);
        System.out.println ( stringColour[pieceColour] + " " + stringColour[faceMatch[face][2]] );
        if ( pieceColour == faceMatch[face][2] )
        {
          rotateFace(pieceColour);
        }
        else
        {
          rotateFace(faceMatch[face][2]);
          rotateFace(faceMatch[face][2]);
          rotateFace(faceMatch[face][2]);
        }
      }
      else
      {
        face = findColourOnFaces( faces, 2, 1, WHITE );
        if ( face != -1 )
        {
          doneSolving = false;
          pieceColour = reverseFaceMatch(face, 2, 1);
          System.out.println ( stringColour[pieceColour] + " " + stringColour[faceMatch[face][3]] );
          if ( pieceColour == faceMatch[face][3] )
          {
            rotateFace(pieceColour);
            rotateFace(pieceColour);
            rotateFace(pieceColour);
          }
          else
          {
            rotateFace(faceMatch[face][3]);
          }
        }
        else
        {

          face = findColourOnFaces( faces, 1, 2, WHITE );
          if ( face != -1 )
          {
            doneSolving = false;
            pieceColour = reverseFaceMatch(face, 1, 2);
            rotateFace(pieceColour);
            rotateFace(pieceColour);
          }
        }
      }
    }
    loopCount++;
  }
  while ( doneSolving == false && loopCount <= 100 );
  if ( loopCount > 100 )
  {
    System.out.println("YOU DERPED");
  }
  if ( DEBUG == 1 )
  {
    //int colour = YELLOW;
    //System.out.println( stringColour[reverseFaceMatch(colour,1,0)] + " " + stringColour[reverseFaceMatch(colour,0,1)] + " " + stringColour[reverseFaceMatch(colour,2,1)] + " " +stringColour[reverseFaceMatch(colour,1,2)] );
    //System.out.println( findXOnFace( WHITE, WHITE, SIDE ) + " " + findYOnFace( WHITE, WHITE, SIDE ) );
  }
  // transformsToFile("transformations.txt");
  //System.out.println("Done Solving");
}




/*
 *
 Sub-Methods
 *
 */


/*
Name: RotateUntilAligned ( only for side piece matching )
 Parameters: 
 - int face : the face the piece is on
 - int layer : the layer/side that you rotate
 - int x : the x coordinate of the piece to check on layer
 - int y : the y coordinate of the piece to check on layer
 - int colour : the colour to check on the layer
 - boolean inverse : inverse?
 Returns: 0 for failure
 */
public int rotateUntilAligned( int face, int layer, int x, int y, int colour, boolean inverse)
{
  int num2 = 0;
  for ( int i = 1; i <= 4; i++ )
  {
    if ( faceMatch[face][i] == layer )
    {
      num2 = i;
    }
  }
  if ( num2 == 0 )
  {
    return -1;
  }
  num2 = (num2)*2 - 1;
  int x2 = num2%3;
  int y2 = num2/3;

  int x3 = reverseFaceMatchX( face, x2, y2 );
  int y3 = reverseFaceMatchY( face, x2, y2 );

  if ( inverse )
  {
    for ( int i = 0; i < 4; i++ )
    {
      rotateFace(layer);
      if ( vCube[face][x2][y2] == colour && vCube[layer][x3][y3] == face )
      {
        break;
      }
    }
  }
  else
  {
    for ( int i = 0; i < 4; i++ )
    {
      rotateFace(layer);
      if ( vCube[face][x2][y2] == face && vCube[layer][x3][y3] == colour )
      {
        break;
      }
    }
  }
  if ( vCube[face][x2][y2] == face )
  {
    return 1;
  }
  return 0;
}




/*
Name: ReverseFaceMatch ( only for side piece matching )
 Parameters: 
 - int face : the current face
 - int x : the x coordinate of the current piece
 - int y : the y coordinate of the current piece
 Returns: the colour
 */
public int reverseFaceMatch( int face, int x, int y )
{
  int num = ((1+x+y*3)/2);
  //1 = above
  //2 = left
  //3 = right
  //4 = below
  //faceMatch[face][num] will tell us the face it's on
  int oppface = faceMatch[face][num];
  int num2 = 0;
  for ( int i = 1; i <= 4; i++ )
  {
    if ( faceMatch[oppface][i] == face )
    {
      num2 = i;
    }
  }

  num2 = (num2)*2 - 1;
  int x2 = num2%3;
  int y2 = num2/3;
  return vCube[faceMatch[face][num]][x2][y2];
}





/*
Name: ReverseFaceMatchX ( only for side piece matching )
 Parameters: 
 - int face : the current face
 - int x : the x coordinate of the current piece
 - int y : the y coordinate of the current piece
 Returns: the X value of the other piece
 */
public int reverseFaceMatchX( int face, int x, int y )
{
  int num = ((1+x+y*3)/2);
  //1 = above
  //2 = left
  //3 = right
  //4 = below
  //faceMatch[face][num] will tell us the face it's on
  int oppface = faceMatch[face][num];
  int num2 = 0;
  for ( int i = 1; i <= 4; i++ )
  {
    if ( faceMatch[oppface][i] == face )
    {
      num2 = i;
    }
  }

  num2 = (num2)*2 - 1;
  int x2 = num2%3;
  int y2 = num2/3;
  return x2;
}





/*
Name: ReverseFaceMatchY ( only for side piece matching )
 Parameters: 
 - int face : the current face
 - int x : the x coordinate of the current piece
 - int y : the y coordinate of the current piece
 Returns: the Y value of the other piece
 */
public int reverseFaceMatchY( int face, int x, int y )
{
  int num = ((1+x+y*3)/2);
  //1 = above
  //2 = left
  //3 = right
  //4 = below
  //faceMatch[face][num] will tell us the face it's on
  int oppface = faceMatch[face][num];
  int num2 = 0;
  for ( int i = 1; i <= 4; i++ )
  {
    if ( faceMatch[oppface][i] == face )
    {
      num2 = i;
    }
  }

  num2 = (num2)*2 - 1;
  int x2 = num2%3;
  int y2 = num2/3;
  return y2;
}





/*
Name: FindXOnFace
 Parameters: 
 - int face : the face to check
 - int colour : the colour to check
 - int type : the type of piece to check (side/corner)
 - boolean ignoreFPosition : do you want to ignore pieces that are in the final position
 Returns: X coordinate, if unavailable then -1
 */
public int findXOnFace( int face, int colour, int type, boolean ignoreFPosition )
{
  int x = -1;
  if ( type == SIDE )
  {
    for ( int i = 2; i < 9; i += 2 )
    {      
      if ( vCube[face][(i-1)%3][(i-1)/3] == colour )
      {
        if ( ignoreFPosition ) //I dont care if it's in final position i just want a piece
        {
          x = (i-1)%3; 
          break;
        }
        else                 //I care if it's in final position, so imma check
        {            
          if ( isFinalPosition(face, (i-1)%3, (i-1)/3) == false )
          {              
            x = (i-1)%3;
            break;
          }
        }
      }
    }
    return x;
  }
  else
  {
    for ( int i = 1; i < 9; i += 2 )
    {      
      if ( vCube[face][(i-1)%3][(i-1)/3] == colour )
      {
        x = (i-1)%3;
        break;
      }
    }
    return x;
  }
}





/*
Name: FindYOnFace
 Parameters: 
 - int face : the face to check
 - int colour : the colour to check
 - int type : the type of piece to check (side/corner)
 - boolean ignoreFPosition : do you want to ignore pieces that are in the final position
 Returns: Y coordinate, if unavailable then -1
 */
public int findYOnFace( int face, int colour, int type, boolean ignoreFPosition )
{
  int y = -1;
  if ( type == SIDE )
  {
    for ( int i = 2; i < 9; i += 2 )
    {      
      if ( vCube[face][(i-1)%3][(i-1)/3] == colour )
      {
        if ( ignoreFPosition ) //I dont care if it's in final position i just want a piece
        {
          y = (i-1)/3; 
          break;
        }
        else                 //I care if it's in final position, so imma check
        {            
          if ( isFinalPosition(face, (i-1)%3, (i-1)/3) == false )
          {              
            y = (i-1)/3;
            break;
          }
        }
      }
    }
    return y;
  }
  else
  {
    for ( int i = 1; i < 9; i += 2 )
    {      
      if ( vCube[face][(i-1)%3][(i-1)/3] == colour )
      {
        y = (i-1)/3;
        break;
      }
    }
    return y;
  }
}





/*
Name: FindColourOnFaces
 Parameters: 
 - int[] faces : the faces to check
 - int x : the x coordinate on the faces to check
 - int y : the y coordinate on the faces to check
 - int colour : the colour to check for
 Returns: the face the piece is on, otherwise -1
 */
public int findColourOnFaces( int[] faces, int x, int y, int colour )
{
  for ( int i = 0; i < faces.length; i++ )
  {
    if ( vCube[faces[i]][x][y] == colour )
    {
      return faces[i];
    }
  }
  return -1;
}





/*
Name: IsFinalPosition (only side pieces)
 Parameters: 
 - int face : the face to check
 - int x : the x of the piece on face
 - int y : the y of the piece on face
 Returns: True or False if the piece is in the final position
 */
public boolean isFinalPosition( int face, int x, int y )
{
  int num = ((1+x+y*3)/2);
  int oppface = faceMatch[face][num];
  int num2 = 0;
  for ( int i = 1; i <= 4; i++ )
  {
    if ( faceMatch[oppface][i] == face )
    {
      num2 = i;
    }
  }   
  num2 = (num2)*2 - 1;
  int x2 = num2%3;
  int y2 = num2/3;
  return (vCube[faceMatch[face][num]][x2][y2] == faceMatch[face][num] && vCube[face][x][y] == face);
}





public void rotateFace( int face )
{     
  switch( face )
  {
  case RED:
    vRight();
    break;
  case GREEN:
    vBack();
    break;
  case BLUE:
    vFront();
    break;
  case ORANGE:
    vLeft();
    break;
  case YELLOW:
    vUp();
    break;
  case WHITE:
    vDown();
    break;
  }
}





