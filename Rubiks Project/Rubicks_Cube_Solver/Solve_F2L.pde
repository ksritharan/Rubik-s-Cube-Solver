
/*
 *
 Constants
 *
 */

final int cornerMatch[][] =
{
  //[X][0] is -1 since num is 1,2,3,4,5
  //-1  TopLeft TopRight Center BotLeft BotRight 
  /*Red   */
  {
    -1, 9, 3, -1, 3, 9
  }
  , 
  /*Green */
  {
    -1, 3, 1, -1, 9, 7
  }
  , 
  /*Blue  */
  {
    -1, 7, 9, -1, 1, 3
  }
  , 
  /*Orange*/
  {
    -1, 1, 7, -1, 7, 1
  }
  , 
  /*Yellow*/
  {
    -1, 3, 1, -1, 1, 3
  }
  , 
  /*White */
  {
    -1, 7, 9, -1, 9, 7
  }
};

final int cornerMatch2[][] =
{
  //[X][0] is -1 since num is 1,2,3,4,5
  //-1  TopLeft TopRight Center BotLeft BotRight 
  /*Red   */
  {
    -1, BLUE, GREEN, -1, BLUE, GREEN
  }
  , 
  /*Green */
  {
    -1, RED, ORANGE, -1, RED, ORANGE
  }
  , 
  /*Blue  */
  {
    -1, ORANGE, RED, -1, ORANGE, RED
  }
  , 
  /*Orange*/
  {
    -1, GREEN, BLUE, -1, GREEN, BLUE
  }
  , 
  /*Yellow*/
  {
    -1, ORANGE, RED, -1, ORANGE, RED
  }
  , 
  /*White */
  {
    -1, ORANGE, RED, -1, ORANGE, RED
  }
};

/*
 *
 Variables
 *
 */

boolean[] solvedCorners = new boolean[4]; //0 RED BLUE, 1 BLUE ORANGE, 2 ORANGE GREEN, 3 GREEN RED


/*
 *
 Methods
 *
 */

public void solveF2L()
{
  int x = 0;
  do
  {  
    x++;
    //insert if available
    insertIfAvailable();

    int face = findColourOnFaces( faces, 0, 0, WHITE );
    updateSolved();
    if ( face != -1 )
    {
      System.out.println("hehehe");
      //System.out.println( " yo diggity dawg the white side of the corner piece is on the (0,0) of : "+ stringColour[face] );
      String info = findSidePiece( reverseFaceMatch1( face, 0, 0 ), reverseFaceMatch2( face, 0, 0 )); //Side piece information  

      //System.out.println(" I'm manipulating the: "+ stringColour[reverseFaceMatch1( face, 0, 0 )]+"-"+stringColour[reverseFaceMatch2( face, 0, 0 )]);

      int colourA = reverseFaceMatch1( face, 0, 0 );
      int colourB = reverseFaceMatch2( face, 0, 0 );
      //System.out.println(faces.length);
      if ( colourA == WHITE || colourA == YELLOW || colourB == WHITE || colourB == YELLOW )
      {
        continue;
      }  
      if ( (info.charAt(4)-'0') == 1 ) //Dawg the side piece is in the middle layer
      {
        int sideface = info.charAt(0)-'0';
        if ( (info.charAt(2)-'0') == 0 )
        {
          sideface = faceMatch[(info.charAt(0)-'0')][((1+(info.charAt(2)-'0')+(info.charAt(4)-'0')*3)/2)]; //Face the side piece is on where the side piece is on the right side
        }
        alignCornerandSidePiece( faceMatch[face][2], sideface );
        applyBreadnButterMove( faceMatch[sideface][3], LEFT );

        if ( vCube[faceMatch[sideface][3]][1][0] == reverseFaceMatch1( faceMatch[sideface][3], 0, 2 ) )
        {
          rotateFace(YELLOW);
          applyBreadnButterMove( faceMatch[faceMatch[sideface][3]][2], RIGHT, 3, "FAKE");

          int topColour = reverseFaceMatch(faceMatch[faceMatch[faceMatch[sideface][3]][2]][2], 1, 0);
          int sideColour = vCube[faceMatch[faceMatch[faceMatch[sideface][3]][2]][2]][1][0];

          matchF2L( topColour, sideColour );
          insertF2L( topColour, topColour, sideColour );

          System.out.println("Case 1 Complete!");
        }
        else
        {
          //System.out.println("yolo");
          //System.out.println(stringColour[faceMatch[faceMatch[sideface][3]][2]] );

          int faceF2LISON = faceMatch[faceMatch[sideface][3]][2];

          applyBreadnButterMove( faceMatch[faceMatch[sideface][3]][2], RIGHT, 4, "FAKE");
          applyBreadnButterMove( faceMatch[faceMatch[sideface][3]][2], RIGHT, 5, "FAKE");

          int topColour = reverseFaceMatch(faceF2LISON, 1, 0);
          int sideColour = vCube[faceF2LISON][1][0];

          matchF2L(topColour, sideColour);
          insertF2L(topColour, topColour, sideColour); 

          System.out.println("Case 2 Complete");
        }
      }
      else //Side Piece is top layer
      {       
        int topColour = reverseFaceMatch((info.charAt(0)-'0'), (info.charAt(2)-'0'), (info.charAt(4)-'0'));
        int sideColour = vCube[(info.charAt(0)-'0')][(info.charAt(2)-'0')][(info.charAt(4)-'0')];

        int sideFace = (info.charAt(0)-'0');
        int oldFace = face;
        face = checkCorner( face, LEFT );
        applyBreadnButterMove( face, LEFT, 4, "FAKE");
        if ( sideFace+oldFace == 3 )
        {
          applyBreadnButterMove(face, LEFT, 4, "FAKE");
          applyBreadnButterMove(face, LEFT, 4, "FAKE");
          face = faceMatch[face][2];
          rotateUntilSidePieceAligned(face, topColour, sideColour);
          if ( reverseFaceMatch1(face, 2, 2 ) == sideColour )
          {
            //System.out.println("lol");
            rotateFace(YELLOW);
            rotateFace(YELLOW);
            rotateFace(YELLOW);
            applyBreadnButterMove(faceMatch[face][3], LEFT, 3, "FAKE");
            matchF2L(topColour, sideColour);
            insertF2L(topColour, topColour, sideColour);
          }
          else
          {
            //System.out.println("sol");
            applyBreadnButterMove( faceMatch[face][3], LEFT, 4, "FAKE");
            applyBreadnButterMove( faceMatch[face][3], LEFT, 5, "FAKE");
            matchF2L(topColour, sideColour);
            insertF2L(topColour, topColour, sideColour);
          }
        }
        else
        {      
          rotateUntilSidePieceAligned(face, topColour, sideColour);
          if ( reverseFaceMatch1(face, 0, 2) == sideColour )
          {
            //System.out.println("bol");    
            rotateFace(YELLOW);
            applyBreadnButterMove(faceMatch[face][2], RIGHT, 3, "FAKE");
            matchF2L(topColour, sideColour);
            insertF2L(topColour, topColour, sideColour);
          }
          else
          {
            //System.out.println("dol");
            applyBreadnButterMove( faceMatch[face][2], RIGHT, 4, "FAKE");
            applyBreadnButterMove( faceMatch[face][2], RIGHT, 5, "FAKE");
            matchF2L(topColour, sideColour);
            insertF2L(topColour, topColour, sideColour);
          }
        }
        System.out.println("Case 5 Complete " + stringColour[topColour] + " " + stringColour[sideColour] );
      }
    }
    else
    {
      face = findColourOnFaces( faces, 2, 0, WHITE );
      if ( face != -1 )
      {
        System.out.println("mememe");
        //System.out.println( " yo diggity dawg the white side of the corner piece is on the (2,0): "+ stringColour[face] );
        String info = findSidePiece( reverseFaceMatch1( face, 2, 0 ), reverseFaceMatch2( face, 2, 0 )); //Side piece information

        //System.out.println(" I'm manipulating the: "+ stringColour[reverseFaceMatch1( face, 2, 0 )]+"-"+stringColour[reverseFaceMatch2( face, 2, 0 )] + " at (2,0)");
        if ( (info.charAt(4)-'0') == 1 ) //dawg the side piece is in the middle layer
        {
          int sideface = info.charAt(0)-'0';
          if ( (info.charAt(2)-'0') == 0 )
          {
            sideface = faceMatch[(info.charAt(0)-'0')][((1+(info.charAt(2)-'0')+(info.charAt(4)-'0')*3)/2)]; //Face the side piece is on where the side piece is on the right side
            //System.out.println(" Dawg this side piece is on the left side so imma take it to the right side.");
            //System.out.println(" k dawg now it's on the right side of "+stringColour[sideface]);
          }
          alignCornerandSidePiece( face, sideface );
          // System.out.println( "do breadnbutter on " + stringColour[sideface] + " on right");
          applyBreadnButterMove( sideface, RIGHT );
          //System.out.println( stringColour[vCube[sideface][1][0]] + " " + stringColour[reverseFaceMatch1( sideface, 2, 2 )] );
          if ( vCube[sideface][1][0] == reverseFaceMatch1(sideface, 2, 2) )
          {
            //System.out.println("yo just move right and do bread n butter on white case 3:");
            rotateFace(YELLOW);
            rotateFace(YELLOW);
            rotateFace(YELLOW);
            //System.out.println(sideface);
            int face2 = faceMatch[sideface][3];
            int topColour = vCube[face2][0][2];
            int sideColour = vCube[face2][1][0];
            applyBreadnButterMove(face2, LEFT, 3, "FAKE");
            matchF2L(topColour, sideColour);
            insertF2L(topColour, topColour, sideColour);
            System.out.println("Case 3 Complete!");
          }
          else
          {
            //System.out.println("yo just do bread n butter on the other side of white case 4: ");
            int face2 = faceMatch[sideface][3];
            int topColour = reverseFaceMatch(sideface, 1, 0);
            int sideColour = vCube[face2][0][2];
            applyBreadnButterMove(face2, LEFT, 4, "FAKE");
            applyBreadnButterMove(face2, LEFT, 5, "FAKE");
            matchF2L(topColour, sideColour);
            insertF2L(topColour, topColour, sideColour);
            System.out.println("Case 4 Complete!");
          }
        }
        else //dawg the side piece is in the top layer
        {
          //System.out.println(((info.charAt(0)-'0')+face));
          int sideColour = vCube[(info.charAt(0)-'0')][(info.charAt(2)-'0')][(info.charAt(4)-'0')];
          int topColour = reverseFaceMatch( (info.charAt(0)-'0'), (info.charAt(2)-'0'), (info.charAt(4)-'0') );

          int sideFace = (info.charAt(0)-'0');
          int oldFace = face;
          face = checkCorner(face, RIGHT);
          applyBreadnButterMove( face, RIGHT );

          if ( sideFace+face == 3 )
          {
            applyBreadnButterMove(face, RIGHT, 4, "FAKE");
            applyBreadnButterMove(face, RIGHT, 4, "FAKE"); 
            rotateUntilSidePieceAligned(face, topColour, sideColour);
            if ( reverseFaceMatch1(faceMatch[face][3], 0, 2 ) == sideColour )
            {
              //System.out.println("pol");
              applyBreadnButterMove(face, RIGHT, 3, "FAKE");
              matchF2L(topColour, sideColour);
              insertF2L(topColour, topColour, sideColour);
            }
            else
            {
              //System.out.println("kol");
              rotateFace(YELLOW);
              rotateFace(YELLOW);
              rotateFace(YELLOW);
              applyBreadnButterMove(face, RIGHT, 4, "FAKE");
              applyBreadnButterMove(face, RIGHT, 5, "FAKE");
              matchF2L(topColour, sideColour);
              insertF2L(topColour, topColour, sideColour);
            }
          }
          else
          { 
            rotateUntilSidePieceAligned(face, topColour, sideColour);
            if ( reverseFaceMatch1(faceMatch[face][3], 0, 2 ) == sideColour )
            {
              //System.out.println("mol");

              rotateFace(YELLOW);
              rotateFace(YELLOW);
              rotateFace(YELLOW);

              applyBreadnButterMove(faceMatch[face][3], LEFT, 3, "FAKE");
              matchF2L(topColour, sideColour);
              insertF2L(topColour, topColour, sideColour);
            }
            else
            {
              //System.out.println("rol");
              applyBreadnButterMove(faceMatch[face][3], LEFT, 4, "FAKE");
              applyBreadnButterMove(faceMatch[face][3], LEFT, 5, "FAKE");
              matchF2L(topColour, sideColour);
              insertF2L(topColour, topColour, sideColour);
            }
          }
          System.out.println("Case 6 Complete " + stringColour[topColour] + " " + stringColour[sideColour] );
        }
      }
      else
      {           
        //check for white on other places
        //Checking for White on Yellow
        int facex = findWhiteCorner();
        if ( facex != -1) // WHITE IS ON THE YELLOW FACE
        { 
            updateSolved();
            System.out.println("loool");
          for ( int i = 0; i < 4; i++ )
          {
            if( reverseFaceMatch1(facex,0,0) == WHITE && vCube[facex][0][0] == faceMatch[facex][2]  && vCube[faceMatch[facex][2]][2][0] == facex)
            {
                break;
            }
            rotateFace(YELLOW);
          }
          System.out.println(stringColour[facex]);
          applyBreadnButterMove(facex, LEFT, 4, "FAKE");
          applyBreadnButterMove(facex, LEFT, 4, "FAKE");
          applyBreadnButterMove(facex, LEFT, 4, "FAKE");
        }
        else
        {       //WHITE IS ON WHITE FACE
          int corner = -1;
          for ( int i = 0; i < 4; i++ )
          {
            System.out.println(i + " " + solvedCorners[i]);
            if ( solvedCorners[i] == false )
            {
              corner = i;              
              break;
            }
          }
          if ( corner != -1 )
          {

            System.out.println("UnsolvedCorner = "+corner);
            System.out.println("HEHEHEHHEHEHEHE");
            //figure out which corner and f2l it out and breadnbutter the corner out
            //0 RED BLUE, 1 BLUE ORANGE, 2 ORANGE GREEN, 3 GREEN RED
            if ( corner == 0 )
            {
              applyBreadnButterMove(BLUE, RIGHT, 3, "FAKE");
            }
            else if ( corner == 1 )
            {
              applyBreadnButterMove(ORANGE, RIGHT, 3, "FAKE");
            }
            else if ( corner == 2 )
            {
              applyBreadnButterMove(GREEN, RIGHT, 3, "FAKE");
            }
            else if ( corner == 3 )
            {
              applyBreadnButterMove(RED, RIGHT, 3, "FAKE");
            }
          }
          else
          {
            //all are probably solved
          }
        }
      }
    }
    updateSolved();
  }
  while ( ( !solvedCorners[0] || !solvedCorners[1] ||!solvedCorners[2] || !solvedCorners[3] ) && x < 16);
}

/*
  Name: ReverseFaceMatch1 ( only for corner piece matching )
 Parameters: 
 - int face : the current face
 - int x : the x coordinate of the current piece
 - int y : the y coordinate of the current piece
 Returns: the colour of either top or bottom
 */
public int reverseFaceMatch1( int face, int x, int y )
{
  //0,0 1 1
  //2,0 2 3
  //1,1 3 5
  //0,2 4 7
  //2,2 5 9
  int num = ((1+x+y*3)/2)+1;
  int num2 = cornerMatch[face][num]-1;
  if ( num2 == -1 )
  {
    return -1;
  }
  int newX = num2%3;
  int newY = num2/3;
  if ( y == 0 )
  {
    if ( face == YELLOW )
    {
      return vCube[GREEN][newX][newY];
    }
    else if ( face == WHITE )
    {
      return vCube[BLUE][newX][newY];
    }
    return vCube[YELLOW][newX][newY];
  }
  else if ( y == 2 )
  {
    if ( face == YELLOW )
    {
      return vCube[BLUE][newX][newY];
    }
    else if ( face == WHITE )
    {
      return vCube[GREEN][newX][newY];
    }
    return vCube[WHITE][newX][newY];
  }
  return -1;
}
/*
Name: ReverseFaceMatch2 ( only for corner piece matching )
 Parameters: 
 - int face : the current face
 - int x : the x coordinate of the current piece
 - int y : the y coordinate of the current piece
 Returns: the colour of either left or right
 */

public int reverseFaceMatch2( int face, int x, int y )
{  
  int num = ((1+x+y*3)/2)+1;
  int oFace = cornerMatch2[face][num];
  if ( (face == YELLOW && y != 0) || (face == WHITE && y != 2) )
  {
    return vCube[oFace][ (x+2)%4 ][ (y+2)%4 ];
  }
  else if ( face == YELLOW || face == WHITE )
  {
    return vCube[oFace][x][y];
  }
  return vCube[oFace][(x+2)%4][y];
}

/*
Name: findSidePiece
 Parameters: 
 - int colour1 : a colour
 - int colour2 : the other colour
 Returns: the colour of either top or bottom
 */
public String findSidePiece( int colour1, int colour2 )
{
  int x = -1;
  int y = -1;
  int face = -1;
  face = findColourOnFaces( faces, 1, 0, colour1, colour2);
  if ( face != -1 )
  {
    return face + " 1 0";
  }
  else
  {
    face = findColourOnFaces( faces, 1, 0, colour2, colour1 );
    if ( face != -1 )
    {
      return face + " 1 0";
    }
    else
    {
      face = findColourOnFaces( faces, 0, 1, colour1, colour2 );
      if ( face != -1 )
      {
        return face + " 0 1";
      }
      else
      {
        face = findColourOnFaces( faces, 0, 1, colour2, colour1 );
        if ( face != -1 )
        {
          return face + " 0 1";
        }
        else
        {
          return "-1";
        }
      }
    }
  }
  /* working code
   for ( int i = 0; i < 4 && x == -1; i++ )
   {     
   x = findXOnFace( i, colour1, SIDE, true );
   y = findYOnFace( i, colour1, SIDE, true );
   if ( x != -1 )
   {
   if ( !(reverseFaceMatch( i, x, y ) == colour2) )
   {
   x = -1;
   }
   else       
   {
   face = i;
   }
   }
   }
   if ( x == -1 )
   {
   return "-1";
   }
   else
   {
   return stringColour[face] + " " + x + " " + y;
   }*/
}


public void alignCornerandSidePiece( int cornerFace, int sideFace )
{    
  for ( int i = 0; i < 4; i++ )
  {    
    if ( cornerFace == sideFace )
    {
      return;
    }
    else
    {
      rotateFace(YELLOW);
      cornerFace = faceMatch[cornerFace][2];
    }
  }
}

/*
Name: FindColourOnFaces
 Parameters: 
 - int[] faces : the faces to check
 - int x : the x coordinate on the faces to check
 - int y : the y coordinate on the faces to check
 - int colour1 : the colour to check for
 - int colour2 : the 2nd colour to match with
 Returns: the face the piece is on, otherwise -1
 */
public int findColourOnFaces( int[] faces, int x, int y, int colour1, int colour2 )
{
  for ( int i = 0; i < faces.length; i++ )
  {
    if ( vCube[faces[i]][x][y] == colour1 && reverseFaceMatch(i, x, y) == colour2  )
    {
      return i;
    }
  }
  return -1;
}

/*
Name: FindColourOnFaces
 Parameters: 
 - int colour - top colour
 Returns: the face the piece is on, otherwise -1
 */
public void insertF2L( int face, int topColour, int sideColour )
{
  System.out.println("k@k");
  int side = whichF2L( face, topColour, sideColour );  


  if ( side == LEFT )
  {
    //System.out.println("LEFT");   
    switch( face )
    {
    case RED:
      // System.out.println("F' U F");
      ApplySeriesMoves("F' U F", "FAKE");
      break;
    case GREEN:
      // System.out.println("R' U R");
      ApplySeriesMoves("R' U R", "FAKE");
      break;
    case BLUE:
      // System.out.println("L' U L");
      ApplySeriesMoves("L' U L", "FAKE");
      break;
    case ORANGE:
      // System.out.println("B' U B");
      ApplySeriesMoves("B' U B", "FAKE");
      break;
    }
  }
  else
  {     
    // System.out.println("RIGHT");
    switch( face )
    {
    case RED:
      // System.out.println("B U' B'");
      ApplySeriesMoves("B U' B'", "FAKE");
      break;
    case GREEN:
      // System.out.println("L U' L'");
      ApplySeriesMoves("L U' L'", "FAKE");
      break;
    case BLUE:
      //System.out.println("R U' R'");
      ApplySeriesMoves("R U' R'", "FAKE");
      break;
    case ORANGE:
      //System.out.println("F U' F'");
      ApplySeriesMoves("F U' F'", "FAKE");
      break;
    }
  }
  //updateSolved(topColour, sideColour);
  updateSolved();
  System.out.println("Solved "+ stringColour[topColour] + " " + stringColour[sideColour] );
}


public void matchF2L( int topColour, int sideColour )
{
  for ( int i = 0; i < 4; i++ )
  {
    if ( vCube[topColour][1][0] == sideColour && reverseFaceMatch( topColour, 1, 0 ) == topColour )
    {
      break;
    }
    rotateFace(YELLOW);
  }
}

public int whichF2L( int face, int topColour, int sideColour )
{
  if ( vCube[face][0][0] == sideColour && reverseFaceMatch2(face, 0, 0) == WHITE && reverseFaceMatch1(face, 0, 0) == topColour )
  {
    return RIGHT;
  }
  return LEFT;
}

public void rotateUntilSidePieceAligned(int face, int topColour, int sideColour)
{
  for ( int i = 0; i < 4; i++ )
  {
    if ( vCube[face][1][0] == sideColour && reverseFaceMatch(face, 1, 0) == topColour )
    {
      break;
    }
    rotateFace(YELLOW);
  }
}

public void updateSolved()// int topColour, int sideColour )
{
  if ( vCube[BLUE][2][2] == BLUE && vCube[RED][0][2] == RED && vCube[WHITE][2][0] == WHITE && vCube[BLUE][2][1] == BLUE && vCube[RED][0][1] == RED)
  {
    solvedCorners[0] = true;
  }
  else
  {
    solvedCorners[0] = false;
  }
  if ( vCube[ORANGE][2][2] == ORANGE && vCube[BLUE][0][2] == BLUE && vCube[WHITE][0][0] == WHITE && vCube[ORANGE][2][1] == ORANGE && vCube[BLUE][0][1] == BLUE )
  {
    solvedCorners[1] = true;
  }
  else
  {
    solvedCorners[1] = false;
  }
  if ( vCube[GREEN][2][2] == GREEN && vCube[ORANGE][0][2] == ORANGE && vCube[WHITE][0][2] == WHITE && vCube[GREEN][2][1] == GREEN && vCube[ORANGE][0][1] == ORANGE )
  {
    solvedCorners[2] = true;
  }
  else
  {
    solvedCorners[2] = false;
  }
  if ( vCube[RED][2][2] == RED && vCube[GREEN][0][2] == GREEN && vCube[WHITE][2][2] == WHITE && vCube[RED][2][1] == RED && vCube[GREEN][0][1] == GREEN)
  {
    solvedCorners[3] = true;
  }
  else
  {
    solvedCorners[3] = false;
  }
  /*
  if ( (topColour == BLUE && sideColour == RED) || (sideColour == BLUE && sideColour == RED) )
   {
   solvedCorners[0] = true;
   }
   else if ( (topColour == BLUE && sideColour == ORANGE) || (sideColour == BLUE && topColour == ORANGE) )
   {
   solvedCorners[1] = true;
   }
   else if ( (topColour == GREEN && sideColour == ORANGE) || (sideColour == GREEN && topColour == ORANGE) )
   {
   solvedCorners[2] = true;
   }
   else if ( (topColour == GREEN && sideColour == RED) || (sideColour == GREEN && topColour == RED) )
   {
   solvedCorners[3] = true;
   }
   */
}

public boolean checkSolved( int topColour, int sideColour )
{
  if ( (topColour == BLUE && sideColour == RED) || (sideColour == BLUE && sideColour == RED) )
  {
    return solvedCorners[0];
  }
  else if ( (topColour == BLUE && sideColour == ORANGE) || (sideColour == BLUE && topColour == ORANGE) )
  {
    return solvedCorners[1];
  }
  else if ( (topColour == GREEN && sideColour == ORANGE) || (sideColour == GREEN && topColour == ORANGE) )
  {
    return solvedCorners[2];
  }
  else if ( (topColour == GREEN && sideColour == RED) || (sideColour == GREEN && topColour == RED) )
  {
    return solvedCorners[3];
  }
  return false;
}


public int checkCorner( int face, int side )
{
  if ( side == LEFT )
  {
    while ( checkSolved (face, faceMatch[face][2]) )
    {
      rotateFace(YELLOW);
      face = faceMatch[face][2];
      /*if( solvedCorners[0] && solvedCorners[1] && solvedCorners[2] && solvedCorners[3] )
      {
          return -1;
      }*/
      System.out.print("ye left side of "+ stringColour[face] );
    }
    return face;
  }
  else
  {
    while ( checkSolved (face, faceMatch[face][3]) )
    {
      rotateFace(YELLOW);
      face = faceMatch[face][3];
      /*if( solvedCorners[0] && solvedCorners[1] && solvedCorners[2] && solvedCorners[3] )
      {
          return -1;
      }*/
     // System.out.println(stringColour[face]+" "+stringColour[faceMatch[face][3]] );
    }
    return face;
  }
}

public void resetSolvedCorners()
{  
  for ( int i = 0; i < 4; i++ )
  {
    solvedCorners[i] = false;
  }
}

public void insertIfAvailable()
{
  for ( int i = 0; i < 4; i++ )
  {
    if ( (vCube[i][0][0] == vCube[i][1][0] && vCube[faceMatch[i][2]][2][0] == WHITE && reverseFaceMatch(i, 1, 0) == reverseFaceMatch1(i, 0, 0) ) || ( vCube[i][2][0] == vCube[i][1][0] && vCube[faceMatch[i][3]][0][0] == WHITE && reverseFaceMatch(i, 1, 0) == reverseFaceMatch1(i, 2, 0) ))
    {
      System.out.println("k@k");
      int topColour = (int)reverseFaceMatch(i, 1, 0);
      int sideColour = (int)vCube[i][1][0];
      System.out.println(stringColour[topColour]+" "+stringColour[sideColour]);
      matchF2L(topColour, sideColour);
      insertF2L(topColour, topColour, sideColour);
    }
  }
}

public int findWhiteCorner()
{
  for( int i = 0; i < 4; i++)
  {
      if( reverseFaceMatch1(i,0,0) == WHITE )
      {
          return reverseFaceMatch2(i,0,0);
      }
  }
  return -1;
}

