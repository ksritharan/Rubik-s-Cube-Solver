/*
 *
 Constants
 *
 */


/*
 *
 Variables
 *
 */
int sum[] = new int[4];
int[] cases = new int[57];

int[][][] backupVCube = new int[6][3][3];


/*
 *
 Methods
 *
 */

public void solveTop()
{

  for ( int i = 0; i < 4; i++ )
  {
    sum[i] = getSum();
    rotateFace(YELLOW);
  }
  System.out.println("Sum = "+sum[0] + " "+ sum[1] + " " + sum[2] + " " + sum[3] );
  oll();
  pll();
}

public int getSum()
{
  int sum = 0;
  int faces[] = {
    GREEN, RED, BLUE, ORANGE
  };
  int value = 2;

  for ( int i = 0 ; i < faces.length; i++ )
  {
    for ( int j = 2; j >= 0; j-- )
    {
      if ( vCube[faces[i]][j][0] == YELLOW )
      {
        sum += value;
      }
      value *= 2;
    }
  }

  for ( int i = 0; i < 3; i++ )
  {
    for (int j = 0; j < 3; j++)
    {
      if ( vCube[YELLOW][j][i] == YELLOW )
      {
        sum += value;
      }
      value *= 2;
    }
  }

  return sum;
}

public void oll()
{    
  BufferedReader reader = createReader("oll.txt");
  String temp = "";
  int Case = 0;
  String move = "";
  int i = 0;
  int rotations = 0;
  try
  {
    while ( (temp = reader.readLine ()) != null )
    {
      String info[] = temp.split(""+(char)9);
      Case = int(info[0]);
      for ( i = 0; i < 4; i++ )
      {
        if ( Case == sum[i] )
        {
          move = info[1];
          System.out.println(Case);
          System.out.println(i);
          rotations = i;
          break;
        }
      }
    }
  }
  catch(Exception e )
  {
    e.printStackTrace();
  }
  for ( int c = 0; c < rotations; c++ )
  {
    rotateFace(YELLOW);
    System.out.println("U ");
  }
  System.out.println(move);
          ApplySeriesMoves(move, "VIRTUAL");
  //transformations += move;
}

public void pll()
{
  BufferedReader reader = createReader("pll.txt");
  String temp = "";
  int Case = 0;
  String move = "";
  int i = 0;
  int rotations = 0;
  String backupTransformations = (String)transformations;
  saveVCube();
  boolean solved = checkIfSolved();
  try
  {
    while ( (temp = reader.readLine ()) != null && !solved )
    {
      for ( i = 1; i <= 4 && !solved; i++ )
      {
          transformations = (String)backupTransformations;
          resetVCube();
          for( int j = 0; j < i; j++ )
          {
              rotateFace(YELLOW);
          }
          ApplySeriesMoves(temp, "VIRTUAL");
          System.out.println(temp);
          solved = checkIfSolved();
          if( !solved )
          {
            for( int k = 0; k < 4 && !solved; k++ )
            {              
              rotateFace(YELLOW);
              solved = checkIfSolved();
            }
          }
          if( solved )
          {
             return;
          }
      }
    }
  }
  catch(Exception e )
  {
    e.printStackTrace();
  }
}

public void saveVCube()
{
    for( int i = 0; i < 6; i++ )
    {
        for( int j = 0; j < 3; j++ )
        {
            for ( int k = 0; k < 3; k++ )
            {
                backupVCube[i][j][k] = (int)vCube[i][j][k];
            }
        }
    }
}

public void resetVCube()
{    
    for( int i = 0; i < 6; i++ )
    {
        for( int j = 0; j < 3; j++ )
        {
            for ( int k = 0; k < 3; k++ )
            {
                vCube[i][j][k] = (int)backupVCube[i][j][k];
            }
        }
    }
}

public boolean checkIfSolved()
{
    boolean solved = true;
        
    for( int i = 0; i < 6 && solved; i++ )
    {
        for( int j = 0; j < 3 && solved; j++ )
        {
            for ( int k = 0; k < 3 && solved; k++ )
            {
                solved &= vCube[i][j][k] == vCube[i][1][1];
            }
        }
    }
    return solved;
}
