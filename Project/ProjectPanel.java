import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class ProjectPanel extends JPanel
{
   //Creating the JOption pane to display when the game is won
   private JOptionPane winPane= new JOptionPane();
   
   //Intializing tick, ticks, and n
   private int tick, ticks, n=0;

   //Making a string of the file name
   private String fName="Project.txt";
   
   //Making the jump variable a double
   private double jump;
   
   //Creating the two d ArrayList of game objects
   private ArrayList<ArrayList<GameObject>> listOfGameObjects= new ArrayList<ArrayList<GameObject>>();
   
   //Intializing the player 
   private Player player;
   
   //Booleans to use to make the player move
   private boolean left=false, right=false;
   
   //Intializing the victory block
   private Block vicBlock;
   
   //Panel constructor
   public ProjectPanel()
   {
      try
      {
         //creating a scanner to read data from the file. 
         Scanner scan= new Scanner(new File(fName));
         
         //Scanning in th starting block of th player
         int startX= 18+((scan.nextInt())*25);
         int startY= 18+((scan.nextInt())*25);
         
         //Creating the Player at the starting block
         player= new Player(startX, startY, Color.YELLOW);
         
         //Scanning in the numbers of rows and columns from the file 
         int rows=scan.nextInt(), columns=scan.nextInt();
         
         //Making a 2D array of nums to be used to convert to a 2D arrayList of objects
         int [][] nums= new int [rows][columns];
         
         //Looping the map into a two d array of ints
         for(int i=0; i<rows; i++)
         {
            for (int j=0; j<columns; j++)
            {
               nums[i][j]=scan.nextInt();
            }
         }
         
         //Creating a 2D array to go through the array of nums and fill the Array List accordingly
         for(int i=0; i<columns; i++)
         {
            //Creating a Temp array that stores the game object
            ArrayList<GameObject> tempList= new  ArrayList<GameObject>();
            for(int j=0; j<rows; j++)
            {
               //Setting the number in the array at the index i j to a variable to be used
               int num=nums[j][i];
               
               //An if statement that sets the 0's in the array as an emptyobject then draws it and adds it to the array list
               if(num==0)
               {
                  EmptyObject eO=new EmptyObject((i*25)+18,(j*25)+18,Color.GRAY);
                  tempList.add(eO);
               }
               //An if statement that sets the 1's in the array as a block object then draws it and adds it to the array list
               else if(num==1)
               {
                  Block b=new Block((i*25)+18,(j*25)+18,Color.MAGENTA);
                  tempList.add(b);
               }
               else if(num==2)
               {
                  //An if statement that sets the 2 in the array as the victory block then draws it and adds it to the array list
                  vicBlock=new Block((i*25)+18,(j*25)+18,Color.GREEN);
                  tempList.add(vicBlock);
               }
            }
            //Adding the temp list to the arraylist of lists
            listOfGameObjects.add(tempList);
         }
       }

       //Catching the file not found exception
       catch(FileNotFoundException fnfe)
       {
         System.out.println("File Not Found!");
       }
       //Creating a timer to re paint 
       Timer t= (new Timer(10, new TimerListener()));
       //Strating the timer
       t.start();
       
       //Creating a keylistener to read the wasd keys
       addKeyListener(new movePlayer());
   
       //Giving the key listener to focus on this panel
       setFocusable(true);
             
       //Setting the size of the panel to 800x 600
       setPreferredSize(new Dimension(8,6));
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      //Drawing the border on the background of the panel
      g.setColor(Color.BLACK);
      g.setColor(Color.BLACK);
      g.fillRect(0,0,810,610);
      g.setColor(new Color(100,100,100));
      g.fillRect(5,5,800,600);
      
      //Drawing the blocks and empty objects
      for(int i=0; i<listOfGameObjects.size(); i++)
      {
         for(int j=0; j<listOfGameObjects.get(i).size(); j++)
         {
            
            //Drawing the player on the board
            listOfGameObjects.get(i).get(j).draw(g);
         }
      } 
      //Drawing the player
      player.draw(g);
   }
   
   public class TimerListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         //If the player lands on the victory Block ending the run
         if(player.win(vicBlock))
         {
            JOptionPane.showMessageDialog(null, "Victory!", "", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
         }
         
         //and if statemnt to see IF the ticks ==20
         if(tick==20)
         {
            n++;
            //If n is greater than 7 setting it equal to 7
            if(n>7)
            {
               n=7;
            }
            //After 20 ticks the tick counter resets.
            tick=0;
         }
         if(ticks==10)
         {
            //If jump is greater than 0 mkaing the jujmp minus .1 each tick
            jump--;
            if(jump<0)
            {
               jump=0;
            }
            ticks=0;
         }
         
         //If the playe rgoes out of bounds letting the user know they died
         if(player.getBottom()>625)
         {
            JOptionPane.showMessageDialog(null, "Gamne Over! you died!", "", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
         }
         
         //Creating the jump feature
         if(jump>0)
         {
            //Re-setting gravity
            n=1;
            //A for loop that loops through moving the block jump times
            for(int i=0; i<jump; i++)
            {
               //Moving the block
               player.move(0,-1,listOfGameObjects);
               //If the player wins while jumping stopping the program
               if(player.win(vicBlock))
               {
                  JOptionPane.showMessageDialog(null, "Victory!", "", JOptionPane.ERROR_MESSAGE);
                  System.exit(1);
               }
               
               //IF the block is on the cieling making jump=0
               else if(player.isOnCieling(listOfGameObjects))
               {
                  jump=0;
               }
            }
            //For n times making the block move down
            for(int j=0; j<n; j++)
            {
               player.move(0,1,listOfGameObjects);
            }
         }
         
         //Creating the gravity that moves the player down if its not on the ground
         if(!player.isOnGround(listOfGameObjects))
         {
            //For n times making the block move down
            for(int i=0; i<n; i++)
            {
               player.move(0,1,listOfGameObjects);
            }
         }
         
         //Setting n=1 if the playe ris on the ground
         if(player.isOnGround(listOfGameObjects))
         {
            n=1;
         }
         
         //While the left key is pressed the player moves left one pixel
         if(left)
         {
            player.move(-1,0,listOfGameObjects);
         }
         
         //While the right key is pressed the player moves right one pixel
         if(right)
         {
            player.move(1,0,listOfGameObjects);
         }
         
         //keeping track of the ticks
         tick++;
         ticks++;
         //Repainting the figure
         repaint();
      }
   }
   
   public class movePlayer implements KeyListener
   {
      public void keyTyped(KeyEvent e){}
      public void keyReleased(KeyEvent e)
      {
         //SETTING THE BOOLEAN TO FALSE WHEN A or d is released to stop the movement
         if(e.getKeyCode()== KeyEvent.VK_A)
         {
            left=false;  
         }
         if(e.getKeyCode()== KeyEvent.VK_D)
         {
            right=false; 
         }
      }
      
      public void keyPressed(KeyEvent e)
      {
         //If the W key is pressed making the jump variable 7 to move the square up if the square is on the ground
         if(e.getKeyCode()== KeyEvent.VK_W)
         {
            if(player.isOnGround(listOfGameObjects))
            {
               jump=7;
            }  
         }
         
         //Settiong aboolean to true if a or d is pushed to be used in the timer
         if(e.getKeyCode()== KeyEvent.VK_A)
         {
            left=true; 
         }
         if(e.getKeyCode()== KeyEvent.VK_D)
         {
            right=true; 
         }
      }
   }
}