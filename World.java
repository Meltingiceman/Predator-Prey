/*My name is Jacob Jones I'm part of the 10 AM CSC 110 class
This program is a predator-prey simulation that shows the 
population of Ants vs. DoodleBugs. This prorgam makes use of polymorphism
among the ants and doodleBugs using the Organism class to do any action that the
two share in common. This program also makes use of GUI's displaying the 
"world" in a GUI where you can advance the number of time steps the user wants.
In the event that an Organism becomes extinct an exception is thrown displaying
a message indicating which organism became extinct and ends the program.*/
import java.util.*;
import javax.swing.*;

class MasterOrg
{
   public static int length = 20;
   public static int width = 20;
   
   public MasterOrg(int wid, int len)
   {
      length = len;
      width = wid;
   }
}

public class World
{
   public static final int INITIAL_ANTS = 50;
   public static final int INITIAL_DOODLES = 6;
   public static int length = 20;
   public static int width = 20; 
   public static int numOfAnts = 0;
   public static int numOfDoodles = 0;
   public static int numOfTimeSteps = 0;
   public static MasterOrg master = new MasterOrg(length, width);
   
   public static Organism[][] world = new Organism[master.width][master.length];
   
   public static void main(String[] args) throws ExtinctOrganismException, InterruptedException
   {//Main
      CountDoodles doodleCounter = new CountDoodles();
      CountAnts antCounter = new CountAnts();
      HonorsGUI gui = new HonorsGUI(master.length, master.width);
      boolean OrgExtinct = false;
      int timeSteps = 0;
      int totalTimeSteps = 0;
      
      makeAnts();
      makeDoodles();
      
      //moving
      int b;
      while(!OrgExtinct)
      {
         b = 0;
         while(b < numOfTimeSteps)
         {
            try
            {
               for (int i = 0; i < master.width; i++)
               {
                  for (int j = 0; j < master.length; j++)
                  {
                     if (world[i][j] != null && !world[i][j].getHasMoved())
                     {
                        world[i][j].move(world, i, j);
                     }
                  }
               }
               reset();
               for (int i = 0; i < master.width; i++)
               {
                  for (int j = 0; j < master.length; j++)
                  {
                     if (world[i][j] != null && world[i][j].canBreed())
                         world[i][j].breed(world, i, j);
                  }
               }

               antCounter.run();
               doodleCounter.run();
               
               if (numOfAnts <= 0)
               {
                  throw new ExtinctOrganismException("Ants");
               }
               else if (numOfDoodles <= 0)
               {
                  throw new ExtinctOrganismException("DoodleBugs");
               }
            }
            catch(ExtinctOrganismException e)
            {
               JOptionPane.showMessageDialog(null, e.getMessage());
               OrgExtinct = true;
               System.exit(0);
            }
            b++;
            timeSteps++;
         }
         gui.update(timeSteps, world, numOfAnts, numOfDoodles);
         while(!gui.getOver())
         {
            Thread.sleep(200);
         }
         totalTimeSteps += numOfTimeSteps = gui.getTimeSteps();
      }
   }
   
   public static void reset()
   {
      for(int i = 0; i < master.width; i++)
      {
         for (int j = 0; j < master.length; j++)
         {
            if (world[i][j] != null)
            {
               world[i][j].setHasMoved(false);
               world[i][j].setHasBred(false);
            }
         }
      }
   }
   
   public static void makeDoodles()
   {
      Random rand = new Random();
      int xcord, ycord;
      for (int j = 0; j < INITIAL_DOODLES; j++)
      {
         xcord = rand.nextInt(master.width);
         ycord = rand.nextInt(master.length);
         while(!Organism.checkCell(world, ycord, xcord))//g.checkCell(world, xcord, ycord))
         {
            xcord = rand.nextInt(master.width);
            ycord = rand.nextInt(master.length);
         }
         world[xcord][ycord] = new DoodleBug();
         numOfDoodles++;
      }
   }
   
   public static void makeAnts()
   {
      Random rand = new Random();
      int xcord, ycord;
      for (int i = 0; i < INITIAL_ANTS; i++)
      {
         xcord = rand.nextInt(master.width);
         ycord = rand.nextInt(master.length);
         while(!Organism.checkCell(world, ycord, xcord))//g.checkCell(world, xcord, ycord))
         {
            xcord = rand.nextInt(master.width);
            ycord = rand.nextInt(master.length);
         }
         world[xcord][ycord] = new Ant();
         numOfAnts++;
      }
   }
   /*A sepereate thread to count the number of ants*/
   static class CountAnts extends Thread
   {
      public void run()
      {
         numOfAnts = 0;
         for (int i = 0; i < master.width; i++)
         {
            for (int j = 0; j < master.length; j++)
            {
               if (world[i][j] instanceof Ant)
                  numOfAnts++;
            }
         }
      }
   }
   /*A seperate thread to count the number of DoodleBugs*/
   static class CountDoodles extends Thread
   {
      public void run()
      {
         numOfDoodles = 0;
         for (int i = 0; i < master.width; i++)
         {
            for (int j = 0; j < master.length; j++)
            {
               if (world[i][j] instanceof DoodleBug)
                  numOfDoodles++;
            }
         }
      }
   }
}