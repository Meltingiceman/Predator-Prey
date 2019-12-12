/*The organism class will need to be consulted for any mthods
that main wants to do to an ant or doodlebug. This class houses
the shared methods among ants and doodlebugs as well as abstract 
methods that the ant and doodlebug classes will need to do on their own*/
import java.util.*;
import java.awt.Color;

public abstract class Organism
{
   protected int timeSinceBreeding; // the time since an Organism has bred
   protected int daysAlive;
   protected Random rand = new Random();
   protected boolean hasMoved;
   protected boolean hasBred;
   protected char id;
   protected Color color;
   
   protected static int[] directions = {1, 2, 3, 4};
   
   public Organism()
   {
      daysAlive = 0;
      timeSinceBreeding = 0;
      hasBred = false;
      hasMoved = false;
      color = Color.BLACK;
   }
   
   public Color getColor()
   {
      return color;
   }
   
   public static boolean checkCell(Organism[][] o, int x, int y)
   {
      return (o[x][y] == null);
   }
   
   public boolean doodleCheckCell(Organism[][] o, int x, int y)
   {
      boolean isEmpty = true;
      if (o[x][y] instanceof DoodleBug)
         isEmpty = false;
      return isEmpty;
   }
   
   public boolean getHasMoved()
   {
      return hasMoved;
   }
   
   public char getId()
   {
      return id;
   }
   
   public void setHasMoved(boolean bool)
   {
      hasMoved = bool;
   }
   
   public void resetBreed()
   {
      timeSinceBreeding = 0;
   }
   
   protected void moveUp(Organism[][] world, int y, int x)
   {
      world[y - 1][x] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveRight(Organism[][] world, int y, int x)
   {
      world[y][x + 1] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveDown(Organism[][] world, int y, int x)
   {
      world[y + 1][x] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveLeft(Organism[][] world, int y, int x)
   {
      world[y][x - 1] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveToBottom(Organism[][] world, int y, int x)
   {
      world[MasterOrg.length - 1][x] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveToFarRight(Organism[][] world, int y, int x)
   {
      world[y][MasterOrg.width - 1] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveToTop(Organism[][] world, int y, int x)
   {
      world[0][x] = world[y][x];
      world[y][x] = null;
   }
   
   protected void moveToFarLeft(Organism[][] world, int y, int x)
   {
      world[y][0] = world[y][x];
      world[y][x] = null;
   }
      
   public int getDirection()
   {
      shuffle();
      int i = 0;
      int direction = 0;
      int check = directions[rand.nextInt(directions.length)];
      if (check == 1)
      {
         //up
         direction = 1;
      }  
      else if (check == 2)
      {  
         //right
         direction = 2;
      }
      else if (check == 3)
      {
         //down
         direction = 3;
      }
      else if (check == 4)
      {  
         //left
         direction = 4;
      }  
      return direction;
   }
   
   public void setHasBred(boolean bool)
   {
      hasBred = bool;
   }
   
   public void shuffle()
   {
      int index1;
      int index2;
      int temp;
      for (int i = 0; i < 500; i++)
      {
         index2 = index1 = rand.nextInt(directions.length);   
         while (index2 == index1)
            index2 = rand.nextInt(directions.length);
         temp = directions[index1];
         directions[index1] = directions[index2];
         directions[index2] = temp;
      }
   }
   
   public void move(Organism[][] world, int y, int x)
   {
      int i = 0;
      while(i < 4 && !hasMoved)
      {
         int direction = rand.nextInt(4) + 1;
         if(direction == 1)// THIS WORKS!!!!!!!
         {//up
            if (y == 0 && checkCell(world, MasterOrg.length - 1, x))
            {
               moveToBottom(world, y, x);
            }
            else if (y != 0 && checkCell(world, y - 1, x))
            {
               moveUp(world, y, x);
            }
         }
         else if (direction == 2) 
         {//right
            if (x == (MasterOrg.width - 1) && checkCell(world, y, 0))
            {
               moveToFarLeft(world, y, x);
            }
            else if (x != MasterOrg.width - 1 && checkCell(world, y, x + 1))
            {
               moveRight(world, y, x);
            }
         }
         else if (direction == 3)
         {//down
            if (y == MasterOrg.length - 1 && checkCell(world, 0, x))
            {
               moveToTop(world, y, x);
            }
            else if (y != MasterOrg.length - 1 && checkCell(world, y + 1, x))
            {
               moveDown(world, y, x);
            }
         }
         else if(direction == 4)
         {//left
            if (x == 0 && checkCell(world, y, MasterOrg.width - 1))
            {
               moveToFarRight(world, y, x);
            }
            else if(x != 0 && checkCell(world, y, x - 1))
            {
               moveLeft(world, y, x);
            }
            i++;
         }
         if (world[y][x] == null)
            hasMoved = true;
      }
//       hasMoved = true;
      timeSinceBreeding++;
   }
   
   protected abstract void breed(Organism[][] world, int y, int x);
   
   public abstract int getBreedFreq();
   
   public abstract boolean canBreed();
}

/*An ant that will represent the prey in this predator-prey simulation
and will move, and breed in a random direction*/

class Ant extends Organism
{
   private static final int BREED_FREQ = 2;
   public Ant()
   {
      super();
      id = 'a';
      color = Color.RED;
   }
   
   public int getBreedFreq()
   {
      return BREED_FREQ;
   }
   
   public void move(Organism[][] world, int y, int x)
   {
      shuffle();
      super.move(world, y, x);
   }
//       shuffle();
//       int i = 0;
//       while(i < 4 || !hasMoved)
//       {
//          int direction = rand.nextInt(4) + 1;
//          if(direction == 1)// THIS WORKS!!!!!!!
//          {//up
//             if (y == 0 && checkCell(world, MasterOrg.width - 1, x))
//             {
//                moveToBottom(world, y, x);
//             }
//             else if (y != 0 && checkCell(world, y - 1, x))
//             {
//                moveUp(world, y, x);
//             }
//          }
//          else if (direction == 2) 
//          {//right
//             if (x == (MasterOrg.length - 1) && checkCell(world, y, 0))
//             {
//                moveToFarLeft(world, y, x);
//             }
//             else if (x != MasterOrg.length - 1 && checkCell(world, y, x + 1))
//             {
//                moveRight(world, y, x);
//             }
//          }
//          else if (direction == 3)
//          {//down
//             if (y == MasterOrg.width - 1 && checkCell(world, 0, x))
//             {
//                moveToTop(world, y, x);
//             }
//             else if (y != MasterOrg.width - 1 && checkCell(world, y + 1, x))
//             {
//                moveDown(world, y, x);
//             }
//          }
//          else if(direction == 4)
//          {//left
//             if (x == 0 && checkCell(world, y, MasterOrg.length - 1))
//             {
//                moveToFarRight(world, y, x);
//             }
//             else if(x != 0 && checkCell(world, y, x - 1))
//             {
//                moveLeft(world, y, x);
//             }
//          }
//          if (world[y][x] == null)
//             hasMoved = true;
//       }
// //       hasMoved = true;
//       timeSinceBreeding++;
//    }
   
   public boolean canBreed()
   {
      boolean bool = false;
      if (timeSinceBreeding >= BREED_FREQ)
      {
         bool = true;
      }
      return bool;
   }
   
   protected void breed(Organism[][] world, int y, int x)
   {
     int direction = getDirection();
     if (direction == 1)
     {
        //up
        if (y == 0 && checkCell(world, MasterOrg.length - 1, x))
        {
           world[MasterOrg.length - 1][x] = new Ant();
           hasBred = true;
        }
        else if (y != 0 && checkCell(world, y - 1, x))
        { 
           world[y - 1][x] = new Ant();
           hasBred = true;
        }
     }  
     else if (direction == 2)
     {//RIGHT
        if (x == MasterOrg.width - 1 && checkCell(world, y, 0))
        {
           world[y][0] = new Ant();
           hasBred = true;
        }
        else if (x != MasterOrg.width - 1 && checkCell(world, y, x + 1))
        {
           world[y][x + 1] = new Ant();
           hasBred = true;
        }
     }
       else if (direction == 3)
       {
          //down
          if (y == MasterOrg.length - 1 && checkCell(world, 0, x))
          {
             world[0][x] = new Ant();
             hasBred = true;
          }
          else if (y != MasterOrg.length - 1 && checkCell(world, y + 1, x))
          {
             world[y + 1][x] = new Ant();
             hasBred = true;
          }
       }
       else if (direction == 4)
       {  
          // left
          if (x == 0 && checkCell(world, y, MasterOrg.width - 1))
          {
             world[y][MasterOrg.width - 1] = new Ant();
             hasBred = true;
          }
          else if (x != 0 && checkCell(world, y, x - 1))
          {
             world[y][x - 1]  = new Ant();
             hasBred = true;
          }
      }
      if(hasBred)
      {
         timeSinceBreeding = 0;
      }
   }
}

/*A doodlebug that will represent the predator in this predator-prey simulation
that will move after ants if there's an ant in an adjacent cell, otherwise will
move in a random direction as well, much like the ant.
*/
class DoodleBug extends Organism
{
   private static final int BREED_FREQ = 6;
   private static final int STARVATION = 3;
   private int hungerTimer;
   private int x_ = 0;
   private int y_ = 0;
   
   public DoodleBug()
   {
      super();
      hungerTimer = 0;
      id = 'd';
      color = Color.LIGHT_GRAY;
   }
   
   public int getStarvation()
   {
      return STARVATION;
   }
   
   public int getBreedFreq()
   {
      return BREED_FREQ;
   }
   
   public void setBreedFreq(int num) //for debugging
   {
      timeSinceBreeding = num;
   }
   
   public void move(Organism[][] world, int y, int x)
   {//normal move
      shuffle();
      if (!starved())
      {
         doodleEat(world, y, x); // the move and eat method
         if (!hasMoved)
         {
            super.move(world, y, x);
            hungerTimer++;
         }
      }
      else
         die(world, y, x);
   }
      
   private void doodleEat(Organism[][] world, int y, int x)
   {
      shuffle();
      int i = 0;    
      while (!hasMoved && i < directions.length)
      {
         checkCells(directions[i], world, y, x);
         i++;
      }
   }
   
   private void checkCells(int direction, Organism[][] world, int y, int x)
   {
      if (direction == 1)
      {//up
         if (y == 0 && (world[MasterOrg.length - 1][x] instanceof Ant))
         {
            world[MasterOrg.length - 1][x] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
         else if (y > 0 && (world[y - 1][x] instanceof Ant))
         {
            world[y - 1][x] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
      }
      else if (direction == 2)
      {//right
         if (x == MasterOrg.width - 1 && (world[y][0] instanceof Ant))
         {
            world[y][0] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
         else if (x < (MasterOrg.width - 1) && (world[y][x + 1] instanceof Ant))
         {
            world[y][x + 1] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
      }
      else if (direction == 3)
      {//down
         if (y == (MasterOrg.length - 1) && (world[0][x] instanceof Ant))
         {
            world[0][x] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
         else if (y < MasterOrg.length - 1 && (world[y + 1][x] instanceof Ant))
         {
            world[y + 1][x] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
      }
      else if (direction == 4)
      {//left
         if (x == 0 && (world[y][MasterOrg.width - 1] instanceof Ant))
         {
            world[y][MasterOrg.width - 1] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
         else if (x > 0 && (world[y][x - 1] instanceof Ant))
         {
            world[y][x - 1] = world[y][x];
            world[y][x] = null;
            resetHunger();
            hasMoved = true;
            timeSinceBreeding++;
         }
      }
   }
      
   public boolean canBreed()
   {
      return (timeSinceBreeding >= BREED_FREQ);
   }
   
   protected void breed(Organism[][] world, int y, int x)
   {
      int i = 0;
      while(i < 4 && !hasBred)
      {
         int direction = getDirection();
         if (direction == 1)
         {
            //up
            if (y == 0 && checkCell(world, MasterOrg.length - 1, x))
            {
              //  g.addOrg(world, MasterOrg.length - 1, x, new DoodleBug());
               world[MasterOrg.length - 1][x] = new DoodleBug();
               hasBred = true;
            }
            else if (y != 0 && checkCell(world, y - 1, x))
            { 
               //g.addOrg(world, y - 1, x, new DoodleBug());
               world[y - 1][x] = new DoodleBug();
               hasBred = true;
            }
         }  
         else if (direction == 2)
         {//RIGHT
            if (x == MasterOrg.width - 1 && checkCell(world, y, 0))
            {
               //g.addOrg(world, y, 0, new DoodleBug());
               world[y][0] = new DoodleBug();
               hasBred = true;
            }
            else if (x != MasterOrg.width - 1 && checkCell(world, y, x + 1))
            {
               //g.addOrg(world, y, x + 1, new DoodleBug());
               world[y][x + 1] = new DoodleBug();
               hasBred = true;
            }
         }
         else if (direction == 3)
         {
            //down
            if (y == MasterOrg.length - 1 && checkCell(world, 0, x))
            {
               //g.addOrg(world, 0, x, new DoodleBug());
               world[0][x] = new DoodleBug();
               hasBred = true;
            }
            else if (y != MasterOrg.length - 1 && checkCell(world, y + 1, x))
            {
               //g.addOrg(world, y + 1, x, new DoodleBug());
               world[y + 1][x] = new DoodleBug();
               hasBred = true;
            }
         }
         else if (direction == 4)
         {  
            // left
            if (x == 0 && checkCell(world, y, MasterOrg.width - 1))
            {
               //g.addOrg(world, y, MasterOrg.width - 1, new DoodleBug());
               world[y][MasterOrg.width - 1] = new DoodleBug();
               hasBred = true;
            }
            else if (x != 0 && checkCell(world, y, x - 1))
            {
               //g.addOrg(world, y, x - 1, new DoodleBug());
               world[y][x - 1] = new DoodleBug();
               hasBred = true;
            }
         }
         i++;
      }
      if(hasBred)
      {
//       numOfDoodles++;
         timeSinceBreeding = 0;
      }
   }
      
   private void resetHunger()
   {
      hungerTimer = 0;
   }
   
   private boolean starved()
   {
      boolean starved = false;
      if (hungerTimer >= STARVATION)
         starved = true;
      return starved;
   }
   
   public void die(Organism[][] world, int y, int x)
   {
      world[y][x] = null;
   }
}

class SuperDoodleBug extends DoodleBug
{
   private static final int MOVES_PER_TURN = 1;
   private static final int TURNS = 1;
   
   private static int[] directions = {1, 2, 3, 4, 5, 6, 7, 8};
   
   public SuperDoodleBug()
   {
      super();
      id = 'D';
      color = Color.DARK_GRAY;
   }
   
   public void move(Organism[][] world, int y, int x)
   {
      int newY; int newX;
      for (int i = 0; i < TURNS; i++)
      {
         for (int j = 0; j < MOVES_PER_TURN; j++)
         {
            shuffle();
            int iterations = 0;
            int direction;
            while (iterations < directions.length)
            {
               direction = directions[iterations];
               switch(direction)
               {
               case 1:        //up-left
                  if (y == 0) 
                     newY = MasterOrg.length;
                  else 
                     newY = y - 1;
                  
                  if (x == 0) 
                     newX = MasterOrg.width;
                  else  
                     newX = x - 1;
                  break;
               case 2:        //up
                  
                  break;
               case 3:        //up-right
                  
                  break;
               case 4:        //right
                  
                  break;
               case 5:        //down-right
                  
                  break;
               case 6:        //down
                  
                  break;
               case 7:        //down-left
                  
                  break;
               case 8:        //left
                  
                  break;
               }
               iterations++;
            }
         } 
      }
   }
   
   public void shuffle()
   {
      int index1;
      int index2;
      int temp;
      for (int i = 0; i < 500; i++)
      {
         index2 = index1 = rand.nextInt(directions.length);   
         while (index2 == index1)
            index2 = rand.nextInt(directions.length);
         temp = directions[index1];
         directions[index1] = directions[index2];
         directions[index2] = temp;
      }
   }
   
   public void supDirections()
   {
      for (int i = 0; i < directions.length; i++)
      {
         System.out.println(directions[i]);
      }
   }
}

/*A very simple Exception class that will be thrown whenever one
of the organisms become extinct*/
class ExtinctOrganismException extends Exception
{
   public ExtinctOrganismException (String Org)
   {
      super(Org + " have become extinct.");
   }
}