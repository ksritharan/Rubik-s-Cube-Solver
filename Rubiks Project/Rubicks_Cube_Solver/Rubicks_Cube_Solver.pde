import JMyron.*;
int comPort = 1; //Change based on COM PORT of Arduino in output


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

import processing.serial.*;
import cc.arduino.*;
import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;
import processing.opengl.*;
import javax.swing.text.*;
import java.io.*;

PFont font; //fonts

JMyron m;//a camera object

Arduino arduino;

boolean validCube;

/*
*
 Main 
 *
 */
void setup()
{
  frame.setTitle("Rubik's Cube Solver");
  size(1020, 480, OPENGL);
  m = new JMyron(); //make a new instance of the object
    
  m.start(640, 480); //start a capture at 640x480
  strokeJoin(ROUND);
  //smooth(6);//anti-aliasing
  
  for( int i = 0; i < Arduino.list().length; i++ )
    System.out.println( "[" + i + "] " + Arduino.list()[i] );


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


void mousePressed() //gets users x and y locations, to be used for button detection
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


void keyPressed()
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
