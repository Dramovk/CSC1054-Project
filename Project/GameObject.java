import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;   
//Creating the Game object class
public class GameObject extends JPanel
{
   //Intializing variables
   private int positionX, positionY;
   private Color pColor;
   public GameObject(int x, int y, Color c)
   //X anfd Y are middle so top =Y-13 and bottom= Y+12
   //Left = X-13 Right = X+12
   {
      //Intializing variables as the parameters passed into the game Object
      positionX=x;
      positionY=y;
      pColor=c;
      
   }
   //MAKE THE PLAYER HIT THE TOP SQUARE NO GAP
   // an accesor that gets an int to represent the top of the square
   public int getTop()
   {
      return positionY-12;
   }
   
   // an accesor that gets an int to represent the bottom of the square
   public int getBottom()
   {
      return positionY+12;
   }
   
   // an accesor that gets an int to represent the left side of the square
   public int getLeft()
   {
      return positionX-12;
   }
   
   // an accesor that gets an int to represent the right side of the square
   public int getRight()
   {
      return positionX+12;
   }
   
   //Collides Method
   public boolean collides(GameObject gO)
   {
      //Setting up variables to represent the sides
      int top, bottom, left, right, otherTop, otherBottom, otherLeft, otherRight;
      
      //setting the sides of the main game object 
      top=this.getTop();
      bottom=this.getBottom();
      left=this.getLeft();
      right=this.getRight();
      
      //Setting the sides of the parameter object
      otherTop=gO.getTop();
      otherBottom=gO.getBottom();
      otherLeft=gO.getLeft();
      otherRight=gO.getRight();
      
      //Making sure that the square isnt checkling agianst itself returning false if it is
      if(this==gO)
      {
         return false;
      }
      else if(bottom<otherTop || top>otherBottom || left>otherRight || right<otherLeft)
      {
         return false;
      }
      
      //Checking if any side equals another and if it does returning false
      else if(top==otherBottom||bottom==otherTop ||left==otherRight||right==otherLeft)
      {
         return true;
      }
      return false;
   }
      
   //Draw method which draws on panel
   public void draw(Graphics g)
   {
      super.paintComponent(g);
      g.setColor(pColor);
      g.fillRect(this.positionX-12,this.positionY-12,25,25);
   }
   
   //Making a method to set X plus the value entered
   public void moveHorizontal(int xStep)
   {
      positionX+=xStep;
   }
   
   //Making a method to set y minus 1
   public void moveDown( )
   {
      positionY++;
   }
   
   // A move method that moves the position up 1
   public void moveUp()
   {
      positionY--;
   }
   
   //A move method that moves the postiotion down by the yStep
   public void moveVerticle(int yStep)
   {
      positionY+=yStep;
   }
   
   //MAking a method which returns a color
   public Color getColor() 
   {
      return pColor;
   }
   
   public String toString()
   {
      return positionX+" "+positionY+" "+pColor;
   }  
   
   //Win method
   public boolean win(GameObject gO)
   {
      //Setting up variables to represent the sides
      int top, bottom, left, right, otherTop, otherBottom, otherLeft, otherRight;
      
      //setting the sides of the main game object 
      top=this.getTop();
      bottom=this.getBottom();
      left=this.getLeft();
      right=this.getRight();
      
      //Setting the sides of the parameter object plus 1 to put on surface
      otherTop=gO.getTop()-1;
      //Cgange to make hit cieling
      otherBottom=gO.getBottom()+1;
      otherLeft=gO.getLeft()-1;
      otherRight=gO.getRight()+1;
      
      
      if(bottom<otherTop || top>otherBottom || left>otherRight || right<otherLeft)
      {
         return false;
      }
      
      //Checking if any side equals another and if it does returning false
      else if(top==otherBottom||bottom==otherTop ||left==otherRight||right==otherLeft)
      {
         return true;
      }
      return false;
   }
}