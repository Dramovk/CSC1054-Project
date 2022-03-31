import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;   
//Creating the Game object class
public class Player extends GameObject
{
   //Constructing the player by using the Game Object's constructor
   public Player(int x, int y, Color c)
   {
      super(x, y, c);
   }
   
   //Creating an is on Ground method
   public boolean isOnGround(ArrayList<ArrayList<GameObject>> gameObjects)
   {
      //Moving the player 1 pixel down
      this.moveDown();
      
      //Creating a nested loop that checks if the array list has a non null game object there
      for (int i=0; i<gameObjects.size(); i++)
      {
         //looping through the array list to see if there is an instance of the block and if the player hits it or not
         for (int j=0; j<gameObjects.get(i).size(); j++)
         {
            //Checking to see if the game object is a Block
            if((gameObjects.get(i).get(j) instanceof Block))
            {
               //Checking to see if the player collided with the block
               if(this.collides(gameObjects))
               {
                  //Moving back to the original postition then returning true
                  this.moveUp();
                  return true;
               }
               else
               {
                  //Moving back to the original postition then returning false
                  this.moveUp();
                  return false;
               }
            }
         }
      }
      return false;
   }
   
   //Creating an is on Cieling method
   public boolean isOnCieling(ArrayList<ArrayList<GameObject>> gameObjects)
   {
      //Moving the player 1 pixel up
      this.moveUp();
      
      //Creating a nested loop that checks if the array list has a non null game object there
      for (int i=0; i<gameObjects.size(); i++)
      {
         //looping through the array list to see if there is an instance of the block and if the player hits it or not
         for (int j=0; j<gameObjects.get(i).size(); j++)
         {
            //Checking to see if the game object is an instance of a BLock object
            if((gameObjects.get(i).get(j) instanceof Block))
            {
               //If it collides with the game object
               if(this.collides(gameObjects))
               {
                  //Move back to original postion and return true
                  this.moveDown();
                  return true;
               }
               else
               {
                  //Move back to original postion and return false
                  this.moveDown();
                  return false;
               }
            }
         }
      }
      return false;
   }
   
   //Creating a move method
   public boolean move(int xStep, int yStep, ArrayList<ArrayList<GameObject>> gameObjects)
   {
      //Adding the step to the boxes postion
      this.moveHorizontal(xStep);
      this.moveVerticle(yStep);
      
      //Looping through the array list 
      for(int i=0; i<gameObjects.size(); i++)
      {
         for(int j=0; j<gameObjects.get(i).size(); j++)
         {
            //Checking to see if the object is an instance of a block
            if(gameObjects.get(i).get(j) instanceof Block)
            {
               //If the player collides with a block
               if(this.collides(gameObjects))
               {
                  //if the yStep is greater than 0 move the player back then check to see if the player is on the ground
                  if(yStep>0)
                  {
                     this.moveHorizontal(-xStep);
                     this.moveVerticle(-yStep);
                     return this.isOnGround(gameObjects);
                  }
                  //if the yStep is less than 0 move the player back then check to see if the player is on the cieling
                  else
                  {
                     this.moveHorizontal(-(xStep));
                     this.moveVerticle(-(yStep));
                     return this.isOnCieling(gameObjects);
                  }
               }
               //Ifn the player doesnt collide with a block return false
               else
               {
                  return true;
               }
            }
         }
      }
      return false;
   }
   
   //Creating the collides method
   public boolean collides(ArrayList<ArrayList<GameObject>> gameObjects)
   {
      //Creating a nested loop that checks if the array list has a non null game object theri
      for (int i=0; i<gameObjects.size(); i++)
      {
         for (int j=0; j<gameObjects.get(i).size(); j++)
         {
            //Checking to see if the gameobject is a Block object
            if(gameObjects.get(i).get(j) instanceof Block)
            {
               //If the block collides with the block object returning true
               if(this.collides(gameObjects.get(i).get(j)))
               {
                  return true;
               }
            }
         }
      }
      
      return false;
   }
}