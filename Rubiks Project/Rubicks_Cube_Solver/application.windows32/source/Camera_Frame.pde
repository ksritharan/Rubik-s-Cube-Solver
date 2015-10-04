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
  void setup() 
  {
    size(640, 480);
  }

  void draw() 
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

  void mousePressed() 
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
