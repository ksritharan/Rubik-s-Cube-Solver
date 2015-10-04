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


