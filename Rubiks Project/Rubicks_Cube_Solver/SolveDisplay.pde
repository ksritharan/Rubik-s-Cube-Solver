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
          arduino = new Arduino(Rubicks_Cube_Solver.this, Arduino.list()[comPort], 57600);
        
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

