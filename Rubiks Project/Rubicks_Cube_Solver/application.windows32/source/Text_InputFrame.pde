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

