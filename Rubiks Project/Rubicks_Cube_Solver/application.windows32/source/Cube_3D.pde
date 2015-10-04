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
void draw3DCube() 
{
  strokeWeight(3);

  fov = 450/float(width);
  aspect = float(width)/float(height);

  perspective(fov, aspect, cameraZ/10.0, cameraZ*10.0);

  //camera(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0), width/2.0, height/2.0, 0, 0, 1, 0);

  translate(width/2 + 120, height/2, 0);

  if (inertia)
  {
    if (diffX > 0)
      diffX-=0.8;
    else if (diffX < 0)
      diffX+=0.8;

    if (diffY > 0)
      diffY-=0.8;
    else if (diffY < 0)
      diffY+=0.8;
  }  

  if (ti==null)
  {
    draggedX += diffX;
    draggedY += diffY;
  }
  
  rotateX(-2*(PI/3 + draggedY/float(height) * PI));
  rotateY(-2*(PI/3 + draggedX/float(height) * PI));

  inertia = true;

  box(75);

  translate(-25, 25, -37.5);  
  drawFace(redFace, 1);     
  fill(0);
  rotateX(PI);
  text ("R", -33, -17, 1);
  rotateX(PI);

  rotateY(PI/2);
  translate(-12.5, 50, 12.5);
  drawFace(greenFace, -1);
  fill(0);
  rotateZ(PI);
  text ("B", -33, -17, 1);
  rotateZ(PI);

  rotateY(PI/2);
  translate(12.5, 50, -12.5);
  drawFace(orangeFace, 1);
  fill(0);
  rotateX(PI);
  text ("L", -33, -17, 1);
  rotateX(PI);

  rotateY(PI/2);
  translate(-12.5, 50, 12.5);
  drawFace(blueFace, -1);
  fill(0);
  rotateZ(PI);
  text ("F", -33, -17, 1);
  rotateZ(PI);

  rotateX(PI/2);
  translate(50, -62.5, -62.5);
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


void mouseDragged()
{ 
  diffX = mouseX - oldX;
  diffY = mouseY - oldY;

  oldX = mouseX;
  oldY = mouseY;

  inertia = false;
}

