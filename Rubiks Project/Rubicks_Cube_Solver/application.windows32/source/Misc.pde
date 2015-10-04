/*
*
 Variables
 *
 */
String[] splitString;
String[] condensedString;


/*
*
 Methods
 *
 */
public void drawRect(int c, int xC, int yC, int sizeC)
{
  fill(c);
  rect(xC, yC, sizeC, sizeC);
}

public void parseText(String str) //makes sence of the input
{
  splitString = str.split(" "); //splits into individual moves

  for (int i = 0; i < splitString.length; i++ )
  {
    if (splitString[i].equals("R"))
      Right();
    else if (splitString[i].equals("R'"))
      RightPrime();
    else if (splitString[i].equals("L"))
      Left();
    else if (splitString[i].equals("L'"))
      LeftPrime();
    else if (splitString[i].equals("U"))
      Up();
    else if (splitString[i].equals("U'"))
      UpPrime();
    else if (splitString[i].equals("D"))
      Down();
    else if (splitString[i].equals("D'"))
      DownPrime();
    else if (splitString[i].equals("B"))
      Back();
    else if (splitString[i].equals("B'"))
      BackPrime();
    else if (splitString[i].equals("F"))
      Front();
    else if (splitString[i].equals("F'"))
      FrontPrime();
    else if (splitString[i].equals("R2"))
    {
      Right();
      Right();
    }
    else if (splitString[i].equals("L2"))
    {
      Left();
      Left();
    }
    else if (splitString[i].equals("U2"))
    {
      Up();
      Up();
    }
    else if (splitString[i].equals("B2"))
    {
      Back();
      Back();
    }
    else if (splitString[i].equals("D2"))
    {
      Down();
      Down();
    }
    else if (splitString[i].equals("F2"))
    {
      Front();
      Front();
    }
    //else
    //System.out.println("bad letter " + splitString[i]); //tells user a bad string has been inputed
  }
}



public void transformationCondenser ()
{
  int count = 0;
  String character = "";
  condensedString = transformations.split(" ");
  transformations = "";
  //System.out.println(transformations);

  for ( int i = 0; i < condensedString.length; i++ )
  {
    int j = i;
    if ( (i+3) < condensedString.length )
      if ( condensedString[i].equals(condensedString[i+1]) && condensedString[i].equals(condensedString[i+2]) && condensedString[i].equals(condensedString[i+3]) )
      {
        i+=3;
        continue;
      }
    if ( (i+2) < condensedString.length )
      if ( condensedString[i].equals(condensedString[i+1]) && condensedString[i].equals(condensedString[i+2]) )
      {
        transformations += condensedString[i] + "' ";
        i += 2;
        continue;
      }
    if ( (i+1) < condensedString.length )        
      if ( condensedString[i].equals(condensedString[i+1]) )
      {
        transformations += condensedString[i] + "2 ";
        i++;
        continue;
      }
    if ( j == i )
      transformations += condensedString[i] + " ";
  }
  //System.out.println(transformations);
}



/*
Name: ApplySeriesMoves (only side pieces)
 Parameters: 
 - String moves : the series of moves to apply 
 - String isReal : isReal?
 returns: void
 */

public void ApplySeriesMoves( String moves, String isReal )
{
  splitString = moves.split(" "); //splits into individual moves

  if ( isReal.equals("REAL") )
  {
    for (int i = 0; i < splitString.length; i++ )
    {
      if (splitString[i].equals("R"))
        Right();
      else if (splitString[i].equals("R'"))
        RightPrime();
      else if (splitString[i].equals("L"))
        Left();
      else if (splitString[i].equals("L'"))
        LeftPrime();
      else if (splitString[i].equals("U"))
        Up();
      else if (splitString[i].equals("U'"))
        UpPrime();
      else if (splitString[i].equals("D"))
        Down();
      else if (splitString[i].equals("D'"))
        DownPrime();
      else if (splitString[i].equals("B"))
        Back();
      else if (splitString[i].equals("B'"))
        BackPrime();
      else if (splitString[i].equals("F"))
        Front();
      else if (splitString[i].equals("F'"))
        FrontPrime();
      else if (splitString[i].equals("R2"))
      {
        Right();
        Right();
      }
      else if (splitString[i].equals("L2"))
      {
        Left();
        Left();
      }
      else if (splitString[i].equals("U2"))
      {
        Up();
        Up();
      }
      else if (splitString[i].equals("B2"))
      {
        Back();
        Back();
      }
      else if (splitString[i].equals("D2"))
      {
        Down();
        Down();
      }
      else if (splitString[i].equals("F2"))
      {
        Front();
        Front();
      }
    }
  }
  else
  {
    for (int i = 0; i < splitString.length; i++ )
    {
      if (splitString[i].equals("R"))
        vRight();
      else if (splitString[i].equals("R'"))
        vRightPrime();
      else if (splitString[i].equals("L"))
        vLeft();
      else if (splitString[i].equals("L'"))
        vLeftPrime();
      else if (splitString[i].equals("U"))
        vUp();
      else if (splitString[i].equals("U'"))
        vUpPrime();
      else if (splitString[i].equals("D"))
        vDown();
      else if (splitString[i].equals("D'"))
        vDownPrime();
      else if (splitString[i].equals("B"))
        vBack();
      else if (splitString[i].equals("B'"))
        vBackPrime();
      else if (splitString[i].equals("F"))
        vFront();
      else if (splitString[i].equals("F'"))
        vFrontPrime();
      else if (splitString[i].equals("R2"))
      {
        vRight();
        vRight();
      }
      else if (splitString[i].equals("L2"))
      {
        vLeft();
        vLeft();
      }
      else if (splitString[i].equals("U2"))
      {
        vUp();
        vUp();
      }
      else if (splitString[i].equals("B2"))
      {
        vBack();
        vBack();
      }
      else if (splitString[i].equals("D2"))
      {
        vDown();
        vDown();
      }
      else if (splitString[i].equals("F2"))
      {
        vFront();
        vFront();
      }
    }
  }
}


public void randomScramble()
{
  for ( int i = 0; i < 50; i++ )
  {  
    int rand = ((int)(Math.random()*1000))%18;
    switch( rand )
    {
    case 0:
      Right();
      break;
    case 1:
      Back();
      break;
    case 2:
      Front();
      break;
    case 3:
      Left();
      break;
    case 4:
      Up();
      break;
    case 5:
      Down();
      break;
    case 6:
      Right();
      Right();
      break;
    case 7:
      Back();
      Back();
      break;
    case 8:
      Front();
      Front();
      break;
    case 9:
      Left();
      Left();
      break;
    case 10:
      Up();
      Up();
      break;
    case 11:
      Down();
      Down();
      break;
    case 12:
      RightPrime();
      break;
    case 13:
      BackPrime();
      break;
    case 14:
      FrontPrime();
      break;
    case 15:
      LeftPrime();
      break;
    case 16:
      UpPrime();
      break;
    case 17:
      DownPrime();
      break;
    }
  }
}



/*
Name: ApplyBreadnButterMove
 Parameters: 
 - int face : the face to do it on
 - int side : left or right
 Returns: void
 */

public void applyBreadnButterMove( int face, int side)
{
  if ( side == LEFT )
  {    
    switch( face )
    {
    case RED:
      ApplySeriesMoves("F' U' F", "FAKE");
      break;
    case GREEN:
      ApplySeriesMoves("R' U' R", "FAKE");
      break;
    case BLUE:
      ApplySeriesMoves("L' U' L", "FAKE");
      break;
    case ORANGE:
      ApplySeriesMoves("B' U' B", "FAKE");
      break;
    }
  }
  else
  {     
    switch( face )
    {
    case RED:
      ApplySeriesMoves("B U B'", "FAKE");
      break;
    case GREEN:
      ApplySeriesMoves("L U L'", "FAKE");
      break;
    case BLUE:
      ApplySeriesMoves("R U R'", "FAKE");
      break;
    case ORANGE:
      ApplySeriesMoves("F U F'", "FAKE");
      break;
    }
  }
}


public void applyBreadnButterMove( int face, int side, int version, String type)
{
  if ( version == 2 )
  {
    if ( side == LEFT )
    {    
      switch( face )
      {
      case RED:
        ApplySeriesMoves("F' U'", type);
        break;
      case GREEN:
        ApplySeriesMoves("R' U'", type);
        break;
      case BLUE:
        ApplySeriesMoves("L' U'", type);
        break;
      case ORANGE:
        ApplySeriesMoves("B' U'", type);
        break;
      }
    }
    else
    {     
      switch( face )
      {
      case RED:
        ApplySeriesMoves("B U", type);
        break;
      case GREEN:
        ApplySeriesMoves("L U", type);
        break;
      case BLUE:
        ApplySeriesMoves("R U", type);
        break;
      case ORANGE:
        ApplySeriesMoves("F U", type);
        break;
      }
    }
  }
  else if ( version == 3 )
  {      
    if ( side == LEFT )
    {    
      switch( face )
      {
      case RED:
        ApplySeriesMoves("F' U' F", type);
        break;
      case GREEN:
        ApplySeriesMoves("R' U' R", type);
        break;
      case BLUE:
        ApplySeriesMoves("L' U' L", type);
        break;
      case ORANGE:
        ApplySeriesMoves("B' U' B", type);
        break;
      }
    }
    else
    {     
      switch( face )
      {
      case RED:
        ApplySeriesMoves("B U B'", type);
        break;
      case GREEN:
        ApplySeriesMoves("L U L'", type);
        break;
      case BLUE:
        ApplySeriesMoves("R U R'", type);
        break;
      case ORANGE:
        ApplySeriesMoves("F U F'", type);
        break;
      }
    }
  }
  else if ( version == 4 )
  {      
    if ( side == LEFT )
    {    
      switch( face )
      {
      case RED:
        //System.out.println("F' U' F U");
        ApplySeriesMoves("F' U' F U", type);
        break;
      case GREEN:
        //System.out.println("R' U' R U");
        ApplySeriesMoves("R' U' R U", type);
        break;
      case BLUE:
        //System.out.println("L' U' L U");
        ApplySeriesMoves("L' U' L U", type);
        break;
      case ORANGE:
        //System.out.println("B' U' B U");
        ApplySeriesMoves("B' U' B U", type);
        break;
      }
    }
    else
    {     
      switch( face )
      {
      case RED:
        ///System.out.println("B U B' U'");
        ApplySeriesMoves("B U B' U'", type);
        break;
      case GREEN:
        //System.out.println("L U L' U'");
        ApplySeriesMoves("L U L' U'", type);
        break;
      case BLUE:
        //System.out.println("R U R' U'");
        ApplySeriesMoves("R U R' U'", type);
        break;
      case ORANGE:
        //System.out.println("F U F' U'");
        ApplySeriesMoves("F U F' U'", type);
        break;
      }
    }
  }
  else if ( version == 5 )
  {      
    if ( side == LEFT )
    {    
      switch( face )
      {
      case RED:
        //System.out.println("F' U' U' F");
        ApplySeriesMoves("F' U' U' F", type);
        break;
      case GREEN:
        //System.out.println("R' U' U' R");
        ApplySeriesMoves("R' U' U' R", type);
        break;
      case BLUE:
        //System.out.println("L' U' U' L");
        ApplySeriesMoves("L' U' U' L", type);
        break;
      case ORANGE:
        //System.out.println("B' U' U' B");
        ApplySeriesMoves("B' U' U' B", type);
        break;
      }
    }
    else
    {     
      switch( face )
      {
      case RED:
        //System.out.println("B U U B'");
        ApplySeriesMoves("B U U B'", type);
        break;
      case GREEN:
        //System.out.println("L U U L'");
        ApplySeriesMoves("L U U L'", type);
        break;
      case BLUE:
        //System.out.println("R U U R'");
        ApplySeriesMoves("R U U R'", type);
        break;
      case ORANGE:
        //System.out.println("F U U F'");
        ApplySeriesMoves("F U U F'", type);
        break;
      }
    }
  }
}

