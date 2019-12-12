/*The grid that controls the placement as well as the initial
checking of the grid when creating organisms
*/
public class Grid
{
   int width;
   int length;
   public Grid(int width, int length)
   {
      this.width = width;
      this.length = length;
   }
   
   public boolean checkCell(Organism[][] o, int x, int y)
   {
      boolean isEmpty = true;
      if (o[x][y] != null)
         isEmpty = false;
      return isEmpty;
   }
   /*The actual placement of the Organism had to be done in a different class to
   avoid making copys of references to the same Organisms*/ 
   public void addOrg(Organism[][] world, int y, int x, Organism org)
   {
      world[y][x] = org;
   }
}