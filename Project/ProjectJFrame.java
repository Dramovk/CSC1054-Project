import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class ProjectJFrame extends JFrame
{ 
   public ProjectJFrame()
   {
      //Calling the Frame Constructor to construct the inital frame
      super("Project");
      
      //Creating a container in the frame to add the panel too
      Container contents= getContentPane();
      
      //Adding the panel to the frame
      contents.add(new ProjectPanel());
      
      //Setting the size of the frame to 800 x 600 and making it visible
      setSize(810,638);
      setVisible(true);
   }
   
   public static void main(String[] args)
   {
      //Creating the frame in the main method
      ProjectJFrame theFrame= new ProjectJFrame();
      
      //Making the application end the run when the x on the frame is hit
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}