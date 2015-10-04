import processing.core.*; 
import processing.xml.*; 

import JMyron.*; 
import processing.serial.*; 
import cc.arduino.*; 
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 
import processing.opengl.*; 
import javax.swing.text.*; 
import java.io.*; 

import JMyron.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Rubicks_Cube_Solver extends PApplet {




/******************* Update Log *************************
 Main frame and camera frame working.
 fixed cf.dispose null pointer error
 added text field
 parsing text done
 2 frame buttons finished
 3D Cube added
 Repositioned and resized 3D 
 Added cube intertia
 Changed colour layout
 Validator done
 Fixed frame issues
 whitecross solved
 starting F2L
 ********************************************************/



 






PFont font; //fonts

JMyron m;//a camera object

Arduino arduino;

boolean validCube;

/*
*
 Main 
 *
 */
public void setup()
{
  frame.setTitle("Rubik's Cube Solver");
  size(1020, 480, OPENGL);
  m = new JMyron(); //make a new instance of the object
    
  m.start(640, 480); //start a capture at 640x480
  strokeJoin(ROUND);
  //smooth(6);//anti-aliasing

  //reseting
  resetCube();
  validCube = true;
  font = createFont("Arial", 22, true);
}


public void draw() 
{
  strokeWeight(1);
  background(204);  

  if (shrunk)
  {
    frame.setSize(1026, 512);
    shrunk = false;
  }

  drawRect(0, 324, 169, 147); //outlines
  drawRect(0, 169, 324, 147);
  drawRect(0, 169, 14, 147);
  drawRect(0, 14, 169, 147);
  drawRect(0, 479, 169, 147);
  drawRect(0, 169, 169, 147);

  for ( int i = 0; i < 3; i++ ) //face colours
    for ( int j = 0; j < 3; j++ )
    {
      drawRect(redFace[i][j], 325+i*50, 170+j*50, 45);
      drawRect( whiteFace[i][j], 170+i*50, 325+j*50, 45);
      drawRect(yellowFace[i][j], 170+i*50, 15+j*50, 45);
      drawRect(orangeFace[i][j], 15+i*50, 170+j*50, 45);
      drawRect(greenFace[i][j], 480+i*50, 170+j*50, 45);
      drawRect(blueFace[i][j], 170+i*50, 170+j*50, 45);
    }
  strokeWeight(3);
  fill(0);

  textFont (font, 36); //cube notations
  text ("F", 231, 257); 
  text ("U", 229, 102); 
  text ("R", 385, 257); 
  text ("L", 77, 257); 
  text ("B", 540, 257);
  text ("D", 229, 411); 

  if (mouseX > 365 && mouseX <(365+230) && mouseY > 345 && mouseY < (345+35) && ti==null && cf == null) //highlights in a colour for each of the frames
    fill (255, 0, 0);
  else
    fill(136, 136, 136);
  rect(365, 345, 230, 35); //series of moves

  if (mouseX > 400 && mouseX <(400+155) && mouseY > 395 && mouseY < (395+35) && ti==null && cf == null)
    fill(255, 0, 0);
  else
    fill(136, 136, 136);
  rect(400, 395, 155, 35); //open camera

  if (mouseX > 20 && mouseX <(20+155) && mouseY > 380 && mouseY < (380+35))
    fill(255, 0, 0);
  else
    fill(136, 136, 136);
  rect(20, 380, 135, 35); //reset cube

  fill(0);
  textFont (font, 22); 

  text ("Apply Series of Moves", 370, 370); 
  text ("Open Camera", 410, 420); 
  text ("Reset Cube", 30, 405);

  if (validCube)
    text("Cube is Valid.", 415, 150);
  else
  {
    fill(210, 0, 0);
    text("Cube is not Valid!", 395, 150);
  }

  if (ti!=null)
  {
    stroke(0);
    fill(190, 190, 190, 140);
    rect(-5, -5, 1030, 490);
    ti.setLocationRelativeTo(frame);
  }

  //frame positioning
  if (cf!=null)
    cf.setLocation(frame.getX() + 650, frame.getY() + 29);
  else if (sd!=null)
    sd.setLocation(frame.getX() + 0, frame.getY() + 509);

  draw3DCube();
  perspective();
}


public void mousePressed() //gets users x and y locations, to be used for button detection
{
  if (mouseX > 365 && mouseX <(365+230) && mouseY > 345 && mouseY < (345+35) && ti==null && cf == null) //click must be within box and frame must not already be open
    ti = new textInput();

  else if (mouseX > 400 && mouseX <(400+150) && mouseY > 395 && mouseY < (395+35)&& cf==null && ti == null)
  {
    cf = new CFrame();
    frame.setSize(1293, 512);
  }
  else  if (mouseX > 20 && mouseX <(20+155) && mouseY > 380 && mouseY < (380+35))
    resetCube();

  oldX = mouseX; //used to correct a 3d rotating issue
  oldY = mouseY;
}


public void keyPressed()
{
  if (sd != null) 
  {
    sd.dispose(); //closes solver if any key is pressed
  }
  if (ti == null)
  {
    if ( key == 'u')    
      Up();

    else if ( key == 'd' )    
      Down();

    else if ( key == 'r' )    
      Right();

    else if ( key == 'f' )    
      Front();

    else if ( key == 'l' )    
      Left();

    else if ( key == 'b' )    
      Back();

    else if ( key == 'U')    
      UpPrime();

    else if ( key == 'D' )    
      DownPrime();

    else if ( key == 'R' )    
      RightPrime();

    else if ( key == 'F' )    
      FrontPrime();

    else if ( key == 'L' )    
      LeftPrime();

    else if ( key == 'B' )    
      BackPrime();

    else if ( key == 'q' )
      resetCube();
  }

  if ( key == 't' && ti == null && cf == null)  //only one frame can be open at a time //text input
    ti = new textInput();
    
  else if ( key == 'c' && cf == null && ti == null) //camera
  {
    cf = new CFrame();
    frame.setSize(1293, 512);
  }
  else if ( key == 's' && ti == null && cf == null && validCube) //solver
  {
    updateVCube();
    solveCross();
    solveF2L();
    solveF2L();
    solveTop();
    sd = new solveDisplay();
  }
  else if ( key == 'p' && ti == null && cf == null)
  {
      for( int i = 0; i < 5000; i ++ )
      {
        randomScramble();
        resetSolvedCorners();
        updateVCube();
        solveCross();
        solveF2L();
        solveF2L();
        parseText(transformations);
        if( !(solvedCorners[0] && solvedCorners[1] && solvedCorners[2] && solvedCorners[3]) )
        {
            System.out.println("DIED AT "+i);
            break;
        }
      }
      System.out.println("boom");
  }
  else if ( key == 'w' && ti == null && cf == null)
  {
    randomScramble();
    resetSolvedCorners();
  }
  else if ( key == '1' )
  {
    applyBreadnButterMove(RED, LEFT);
  }
  else if ( key == '2' )
  {
    applyBreadnButterMove(GREEN, LEFT);
  }
  else if ( key == '3' )
  {
    applyBreadnButterMove(BLUE, LEFT);
  }
  else if ( key == '4' )
  {
    applyBreadnButterMove(ORANGE, LEFT);
  }
  else if ( key == '5' )
  {
    applyBreadnButterMove(RED, RIGHT);
  }
  else if ( key == '6' )
  {
    applyBreadnButterMove(GREEN, RIGHT);
  }
  else if ( key == '7' )
  {
    applyBreadnButterMove(BLUE, RIGHT);
  }
  else if ( key == '8' )
  {
    applyBreadnButterMove(ORANGE, RIGHT);
  }

  //camera controlls
  if (cf != null) //activated camera controlls only when camera is open
    if (key == ' ')
    {
      for ( int c = 0; c < 6; c++ )
        if ( detectColor(colour[1][1]) == COLOURS[c] )
          setArray(c);
    } 
    else if (key == 'z' ) //shuts down frame
    {
      cf.dispose();
      ca.dispose();
      cf = null;
      validCube = cubeValidate(); //checks if the cube is valid or not after the use clsoes the camera frame
      shrunk = true;
    }
}


//Initialize Cube
public void resetCube()
{
  for ( int i = 0; i < 3; i++ )  
    for ( int j = 0; j < 3; j++ )    
    {
      redFace[i][j] = COLOURS[0]; 
      greenFace[i][j] = COLOURS[1];
      blueFace[i][j] = COLOURS[2];
      orangeFace[i][j] = COLOURS[3];
      yellowFace[i][j] = COLOURS[4];
      whiteFace[i][j] = COLOURS[5];
    }

  validCube=true;
}
/*
*
 Variables
 *
 */
CFrame cf; //camera frame
CApplet ca; //camera app

boolean shrunk;

/*
*
 Classes
 *
 */
public class CFrame extends JWindow
{
  public CFrame() 
  { 
    //setTitle ("Cube Camera");
    setBounds(100, 100, 640, 480); //left insets 4+2 top insets 30+2
    //setResizable (false);
    this.setAlwaysOnTop(true);
    //this.setUndecorated(true);
    //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    ca = new CApplet();
    add(ca);
    ca.init();
    show();
  }
}

public class CApplet extends PApplet
{
  public void setup() 
  {
    size(640, 480);
  }

  public void draw() 
  {
    m.update();
    int[] img = m.cameraImage(); //get the normal image of the camera
    int a = m.average(0, 0, 100, 100);
    loadPixels();

    for (int i = 0; i < width * height; i++) //loop through all the pixels
    {    
      pixels[i] = img[i]; //draw each pixel to the screen
    }
    updatePixels();

    line(80, 0, 80, 480); //guidelines for cube aiming
    line(240, 0, 240, 480);
    line(400, 0, 400, 480);
    line(560, 0, 560, 480);
    line(80, 160, 560, 160);
    line(80, 320, 560, 320);


    for ( int i = 0; i < 3; i++ ) //draws detected colors at bottom right of screen
      for ( int j = 0; j < 3; j++ )
      {
        colour[i][j] = m.average(120+i*160, 40+j*160, 160+i*160, 80+j*160);
        realColor[i][j] = detectColor(colour[i][j]);
        fill(realColor[i][j]);
        rect(565+i*25, 405+j*25, 20, 20);
      }

    if (mouseX > 560 && mouseX <(560+80) && mouseY > 5 && mouseY < (5+30))
      fill(255, 0, 0);
    else
      fill(136);
    rect(562, 5, 76, 30); //exit


    if (mouseX > 560 && mouseX <(560+80) && mouseY > 40 && mouseY < (40+30))
      fill(255, 0, 0);
    else
      fill(136); //settings
    rect(562, 40, 76, 30);

    fill(0);
    textFont (font, 18); 

    text ("Exit", 585, 28);
    text ("Settings", 568, 63);
  }

  public void mousePressed() 
  {
    if (mouseX > 560 && mouseX <(560+80) && mouseY > 5 && mouseY < (5+30))
    {
      cf.dispose();
      ca.dispose();
      cf = null;
      validCube = cubeValidate(); //checks if the cube is valid or not after the use clsoes the camera frame
      shrunk=true;
    }
    if (mouseX > 560 && mouseX <(560+80) && mouseY > 40 && mouseY < (40+30) )
    {
      m.settings();
    }
  }


  public void stop() 
  {
    //m.stop();//stop the object
    super.stop();
  }
}
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

/*
*
 Variables
 *
 */
int draggedX = -3;
int draggedY = 128;

int oldX;
int oldY;

int diffX;
int diffY;

float fov;
float cameraZ = 600f;
float aspect;

boolean inertia;

/*
*
 Methods
 *
 */
public void draw3DCube() 
{
  strokeWeight(3);

  fov = 450/PApplet.parseFloat(width);
  aspect = PApplet.parseFloat(width)/PApplet.parseFloat(height);

  perspective(fov, aspect, cameraZ/10.0f, cameraZ*10.0f);

  //camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);

  translate(width/2 + 120, height/2, 0);

  if (inertia)
  {
    if (diffX > 0)
      diffX-=0.8f;
    else if (diffX < 0)
      diffX+=0.8f;

    if (diffY > 0)
      diffY-=0.8f;
    else if (diffY < 0)
      diffY+=0.8f;
  }  

  if (ti==null)
  {
    draggedX += diffX;
    draggedY += diffY;
  }
  
  rotateX(-2*(PI/3 + draggedY/PApplet.parseFloat(height) * PI));
  rotateY(-2*(PI/3 + draggedX/PApplet.parseFloat(height) * PI));

  inertia = true;

  box(75);

  translate(-25, 25, -37.5f);  
  drawFace(redFace, 1);     
  fill(0);
  rotateX(PI);
  text ("R", -33, -17, 1);
  rotateX(PI);

  rotateY(PI/2);
  translate(-12.5f, 50, 12.5f);
  drawFace(greenFace, -1);
  fill(0);
  rotateZ(PI);
  text ("B", -33, -17, 1);
  rotateZ(PI);

  rotateY(PI/2);
  translate(12.5f, 50, -12.5f);
  drawFace(orangeFace, 1);
  fill(0);
  rotateX(PI);
  text ("L", -33, -17, 1);
  rotateX(PI);

  rotateY(PI/2);
  translate(-12.5f, 50, 12.5f);
  drawFace(blueFace, -1);
  fill(0);
  rotateZ(PI);
  text ("F", -33, -17, 1);
  rotateZ(PI);

  rotateX(PI/2);
  translate(50, -62.5f, -62.5f);
  rotateZ(PI);
  drawFace(yellowFace, 1);
  fill(0);
  rotateZ(PI);
  text ("U", 17, -17, -1);
  rotateZ(PI);

  translate(-50, 0, 75);
  rotateZ(PI);
  drawFace(whiteFace, -1);
  fill(0);
  rotateZ(PI);
  text ("D", -33, -17, 1);
  rotateZ(PI);
}


//Draw each face
public void drawFace( int[][] face, int reflect )
{

  fill(face[0][0]);
  box(25, 25, 1);

  fill(face[1][0]);
  translate(25*reflect, 0, 0);
  box(25, 25, 1);

  fill(face[2][0]);
  translate(25*reflect, 0, 0);
  box(25, 25, 1);

  fill(face[0][1]);
  translate(-50*reflect, -25, 0);
  box(25, 25, 1);

  fill(face[1][1]);
  translate(25*reflect, 0, 0);
  box(25, 25, 1);  

  fill(face[2][1]);
  translate(25*reflect, 0, 0);
  box(25, 25, 1);

  fill(face[0][2]);
  translate(-50*reflect, -25, 0);
  box(25, 25, 1);

  fill(face[1][2]);
  translate(25*reflect, 0, 0);
  box(25, 25, 1);

  fill(face[2][2]);
  translate(25*reflect, 0, 0);
  box(25, 25, 1);
}


public void mouseDragged()
{ 
  diffX = mouseX - oldX;
  diffY = mouseY - oldY;

  oldX = mouseX;
  oldY = mouseY;

  inertia = false;
}

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

int R = 5;
int RG = 3;
int B = 9;
int BG = 6;
int O = 10;
int OG = 11;

public void applyRealMoves(String str)
{
  String[] moves = str.split(" "); //splits into individual moves

  for (int i = 0; i < moves.length; i++ )
  {
    println(moves[i]);
    if (moves[i].equals("R"))
      mRight();
    else if (moves[i].equals("R'"))
      mRightPrime();
    else if (moves[i].equals("L"))
      mLeft();
    else if (moves[i].equals("L'"))
      mLeftPrime();
    else if (moves[i].equals("U"))
      mUp();
    else if (moves[i].equals("U'"))
      mUpPrime();
    else if (moves[i].equals("D"))
      mDown();
    else if (moves[i].equals("D'"))
      mDownPrime();
    else if (moves[i].equals("B"))
      mBack();
    else if (moves[i].equals("B'"))
      mBackPrime();
    else if (moves[i].equals("F"))
      mFront();
    else if (moves[i].equals("F'"))
      mFrontPrime();
    else if (moves[i].equals("R2"))
    {
      mRight2();
    }
    else if (moves[i].equals("L2"))
    {
      mLeft2();
    }
    else if (moves[i].equals("U2"))
    {
      mUp2();
    }
    else if (moves[i].equals("B2"))
    {
      mBack2();
    }
    else if (moves[i].equals("D2"))
    {
      mDown2();
    }
    else if (moves[i].equals("F2"))
    {
      mFront2();
    }
    delay(2000);
  }
}


public void mFront()
{
    arduino.analogWrite(RG, 250);
    arduino.analogWrite(R, 100);
    delay(800);
  arduino.analogWrite(RG, 90);
  arduino.analogWrite(R, 190);
  delay(50);
  arduino.analogWrite(RG, 0);
  delay(500);
  arduino.analogWrite(RG, 90);
    arduino.analogWrite(R, 250);
    delay(600);
  arduino.analogWrite(RG, 90);
  arduino.analogWrite(R, 190);
  stop(RG);
  stop(R);
}

public void mFrontPrime()
{
  arduino.analogWrite(RG, 30);
  delay(150);
  arduino.analogWrite(RG, 90);
    arduino.analogWrite(R, 100);
    delay(400);
  arduino.analogWrite(RG, 245);
  delay(500);
    arduino.analogWrite(R, 250);
    delay(800);
  arduino.analogWrite(RG, 90);
  arduino.analogWrite(R, 190);
  stop(RG);
  stop(R);
}

public void mFront2()
{
    mFront();
    mFront();
}

public void grip()
{  
  arduino.analogWrite(RG, 250);
  arduino.analogWrite(BG, 215);
  arduino.analogWrite(OG, 200);
}

public void mRight()
{
    arduino.analogWrite(OG, 200);
    arduino.analogWrite(O, 130);
    delay(700);
  arduino.analogWrite(OG, 254);
  arduino.analogWrite(O, 90);
  delay(50);
  arduino.analogWrite(OG, 30);
  delay(100);
  arduino.analogWrite(OG, 254);

    arduino.analogWrite(O, 30);
    delay(500);
  arduino.analogWrite(OG, 254);
  arduino.analogWrite(O, 90);
  stop(OG);
  stop(O);
}

public void mRight2()
{
  mRight();
  delay(1000);
  mRight();
}

public void mRightPrime()
{
    arduino.analogWrite(OG, 30);
    delay(100);
  arduino.analogWrite(OG, 254);
    arduino.analogWrite(O, 130);
    delay(600);
  arduino.analogWrite(OG, 200);
  delay(300);
    arduino.analogWrite(O, 30);
  delay(1000);
  arduino.analogWrite(OG, 254);
  arduino.analogWrite(O, 90);
  stop(OG);
  stop(O);
}

public void mLeft()
{
    arduino.analogWrite(BG, 215);
    arduino.analogWrite(B, 100);
    delay(1100);
  arduino.analogWrite(BG, 210);
  arduino.analogWrite(B, 205);
  delay(50);
  arduino.analogWrite(BG, 160);
  delay(300);
  arduino.analogWrite(BG, 180);
    arduino.analogWrite(B, 250);
    delay(800);
  arduino.analogWrite(BG, 180);
  arduino.analogWrite(B, 205);
  stop(BG);
  stop(B);
}

public void mLeft2()
{
  mLeft();
  mLeft();
}

public void mLeftPrime()
{
  arduino.analogWrite(BG, 160);
  delay(100);
  arduino.analogWrite(BG, 180);
    arduino.analogWrite(B, 100);
    delay(900);
  arduino.analogWrite(BG, 210);
  arduino.analogWrite(B, 205);
  delay(50);
  arduino.analogWrite(BG, 215);
    arduino.analogWrite(B, 250);
    delay(600);
  arduino.analogWrite(BG, 180);
  arduino.analogWrite(B, 205);
  stop(BG);
  stop(B);
  
  
}

public void mUp()
{
 flip();
  delay(100);
  stopAll();
  delay(1000);
  mFront();
  delay(4000);
  flipPrime();
  delay(2000);
  stop(RG);
  stop(R);
  stop(BG);
  stop(B);  
  stop(OG);
  println("done");
}

public void mUpPrime()
{
flip();
  delay(100);
  stopAll();
  delay(1000);
  mFront();
  delay(4000);
  flipPrime();
  delay(2000);
  stop(RG);
  stop(R);
  stop(BG);
  stop(B);  
  stop(OG);
  println("done");
}

public void mUp2()
{
  flip();
  delay(100);
  stopAll();
  delay(1000);
  mFront2();
  delay(4000);
  flipPrime();
  delay(2000);
  stop(RG);
  stop(R);
  stop(BG);
  stop(B);  
  stop(OG);
  println("done");
}

public void flip()
{
  arduino.analogWrite(BG, 160);
  arduino.analogWrite(OG, 30);
  delay(400);
  stop(BG);
  stop(OG);
  delay(100);
    arduino.analogWrite(B, 100);
    arduino.analogWrite(O, 130);
    delay(900);
  arduino.analogWrite(BG, 215);
  arduino.analogWrite(OG, 200);
  delay(2000);
    arduino.analogWrite(O, 30);
    arduino.analogWrite(B, 250);
    delay(1000);
  arduino.analogWrite(BG, 180);
  arduino.analogWrite(B, 205);
  arduino.analogWrite(OG, 254);
  arduino.analogWrite(O, 90);
  delay(100);
  stopAll();
}

public void flipPrime()
{ 
    arduino.analogWrite(BG, 215);
    arduino.analogWrite(OG, 200);
    arduino.analogWrite(B, 100);
    arduino.analogWrite(O, 130);
    delay(1100);
  arduino.analogWrite(BG, 210);
  arduino.analogWrite(B, 205);
  arduino.analogWrite(OG, 254);
  arduino.analogWrite(O, 90);
  delay(50);
  arduino.analogWrite(BG, 160);
  arduino.analogWrite(OG, 30);
  delay(300);
  arduino.analogWrite(BG, 180);
    arduino.analogWrite(B, 250);
  arduino.analogWrite(OG, 254);

    arduino.analogWrite(O, 30);
  stop(OG);
    delay(500);
    arduino.analogWrite(BG, 215);
    arduino.analogWrite(OG, 200);
    delay(500);
  stop(BG);
  stop(B);
  stop(O);
}

public void mBack()
{
  flip();
  delay(1000);
  flip();
  delay(1000);
  mFront();
}


public void mBack2()
{
  flip();
  delay(1000);
  flip();
  delay(1000);
  mFront2();
  delay(1000);
  flipPrime();
  delay(1000);
  flipPrime();
}

public void mBackPrime()
{  
  flip();
  delay(1000);
  flip();
  delay(1000);
  mFrontPrime();
  delay(1000);
  flipPrime();
  delay(1000);
  flipPrime();
}

public void mDown()
{
  flipPrime();
  delay(1000);
  flipPrime();
  delay(1000);
  mFront();
  delay(1000);
  flip();
  delay(1000);
  flip();
  delay(1000);
  
}

public void mDown2()
{
  flipPrime();
  delay(1000);
  flipPrime();
  delay(1000);
  mFront2();
  delay(1000);
  flip();
  delay(1000);
  flip();
  delay(1000);
}

public void mDownPrime()
{
  flipPrime();
  delay(1000);
  flipPrime();
  delay(1000);
  mFrontPrime();
  delay(1000);
  flip();
  delay(1000);
  flip();
  delay(1000);
  
}
public void stop(int port)
{
    arduino.analogWrite(port, 0);
}

public void stopAll()
{
  stop(O);
  stop(OG);
  stop(BG);
  stop(B);
  stop(R);
  stop(RG);
}


/*
 *
 Variables
 *
 */

JTextArea textArea;
JLabel label;

solveDisplay sd;
/*
 *
 Methods
 *
 */

public class solveDisplay extends JDialog implements ActionListener
{
  public solveDisplay()
  {
    Container container = getContentPane ();
    container.setLayout (new FlowLayout ());    
    this.getContentPane().setBackground(new Color(204, 204, 204));

    label = new JLabel();
    label.setText("Solved Cube:");
    container.add(label);

    transformationCondenser();
    textArea = new JTextArea(transformations, 5, 20);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    container.add(textArea);    


    JScrollPane scrollPane = new JScrollPane(textArea);
    container.add(scrollPane);

    JButton button = new JButton("Close"); 
    button.addActionListener(this);
    container.add(button);


    JButton applyButton = new JButton("Apply Moves"); 
    applyButton.addActionListener(this);
    container.add(applyButton);

    setSize (250, 150);
    this.setUndecorated(true);
    this.setAlwaysOnTop(true);
    setVisible (true);
    setResizable (false);
  }

  public void actionPerformed (ActionEvent e)
  {
    if (e.getActionCommand ().equals("Close"))
    {
      this.dispose();//closes the frame
      sd = null;
    }
    
    if (e.getActionCommand ().equals("Apply Moves"))
    {
      parseText(transformations);
      this.dispose();//closes the frame
      sd = null;
      
        if( arduino == null && Arduino.list().length > 0 )
          arduino = new Arduino(Rubicks_Cube_Solver.this, Arduino.list()[0], 57600);
        
        arduino.pinMode(R, Arduino.OUTPUT);  
        arduino.pinMode(RG, Arduino.OUTPUT);  
        arduino.pinMode(B, Arduino.OUTPUT);  
        arduino.pinMode(BG, Arduino.OUTPUT);   
        arduino.pinMode(O, Arduino.OUTPUT);  
        arduino.pinMode(OG, Arduino.OUTPUT);

        applyRealMoves(transformations);
      
   }
  }
}

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
      Case = PApplet.parseInt(info[0]);
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
/*
*
 Variables
 *
 */
textInput ti; //text input frame
String inputString;


/*
*
 Methods
 *
 */
public class textInput extends JDialog implements ActionListener
{   
  JTextField itext;

  public textInput ()
  {
    Container container = getContentPane ();
    container.setLayout (new FlowLayout ());
    
    this.getContentPane().setBackground(new Color(204, 204, 204));

    itext = new JTextField (40);
    itext.addActionListener (this);
    container.add (itext);

    JButton button = new JButton("Close"); 
    button.addActionListener(this);
    container.add(button);

    setTitle ("Apply Move");
    setSize (460, 60);
    this.setUndecorated(true);
    this.setAlwaysOnTop(true);
    setVisible (true);
    setResizable (false);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }

  public void actionPerformed (ActionEvent e)
  {
    inputString = e.getActionCommand ();
    //System.out.println(inputString); //prints the received text
    parseText(inputString);
    this.dispose();//closes the frame
    ti = null;
  }
} 

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

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "Rubicks_Cube_Solver" });
  }
}
