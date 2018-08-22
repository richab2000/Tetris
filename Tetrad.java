import java.awt.Color;
/**
 * Tetrad class contains the four block tetrad and its helper methods.
 * 
 * @author Richa Bhattacharya
 * @version January 13 2017
 */
public class Tetrad
{
    // instance variables - replace the example below with your own
    private Block[] blocks;
    private MyBoundedGrid<Block> liveIn;
    private final static Color [] BLOCK_COLORS = { Color.RED, Color.GRAY, Color.CYAN, 
                                                   Color.YELLOW, Color.MAGENTA, Color.BLUE, 
                                                   Color.GREEN };
    private int randShape;                                                      

    /**
     * Adds the blocks to the grid and the respective locations
     * 
     * @param grid  the grid of the tetris game
     * @param locs  an array of locations
     */
    private void addToLocations(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for (int i = 0; i < locs.length; i ++)
        {
            blocks[i] = new Block();
            blocks[i].putSelfInGrid(grid, locs[i]);
            blocks[i].setColor(BLOCK_COLORS[randShape]);
        }
    }

    /**
     * Constructor for objects of class Tetrad
     * 
     * @param grid  the grid of the tetris game
     */
    public Tetrad(MyBoundedGrid<Block> grid)
    {
        liveIn = grid;
        randShape = (int)( Math.random() * 7);
        Location [] locs = new Location [4];
        blocks = new Block [4];
        int cols = liveIn.getNumCols();

        //0 is Red
        if (randShape== 0)
        {
            locs [1] = new Location (0, cols/2);
            locs [0] = new Location (1, cols/2);
            locs [2] = new Location (2, cols/2);
            locs [3] = new Location (3, cols/2);
        }

        //1 is Gray
        else if (randShape== 1)
        {
            locs [1] = new Location (0, cols/2-1);
            locs [0] = new Location (0, cols/2);
            locs [2] = new Location (0, cols/2+1);
            locs [3] = new Location (1, cols/2);
        }

        //2 is Cyan
        else if (randShape== 2)
        {
            locs [0] = new Location (0, cols/2-1);
            locs [2] = new Location (0, cols/2);
            locs [1] = new Location (1, cols/2-1);
            locs [3] = new Location (1, cols/2);
        }

        //3 is Yellow
        else if (randShape== 3)
        {
            locs [2] = new Location (2, cols/2-1);
            locs [1] = new Location (0, cols/2-1);
            locs [0] = new Location (1, cols/2-1);
            locs [3] = new Location (2, cols/2);
        }

        //4 is Magenta
        else if (randShape== 4)
        {
            locs [1] = new Location (0, cols/2);
            locs [0] = new Location (1, cols/2 );
            locs [2] = new Location (2, cols/2 );
            locs [3] = new Location (2, cols/2-1);
        }

        //5 is Blue
        else if (randShape== 5)
        {
            locs [1] = new Location (0, cols/2+1);
            locs [0] = new Location (0, cols/2 );
            locs [2] = new Location (1, cols/2 );
            locs [3] = new Location (1, cols/2-1);
        }

        //6 is Green
        else if (randShape== 6)
        {
            locs [1] = new Location (0, cols/2 -1);
            locs [0] = new Location (0, cols/2);
            locs [2] = new Location (1, cols/2);
            locs [3] = new Location (1, cols/2 + 1);
        }
        addToLocations(liveIn, locs);
    }

    /**
     * Removes the blocks
     * 
     * @return  the four previous locations of the blocks
     */
    private Location [] removeBlock()
    {
        Location [] temps = new Location [4];
        for (int i = 0; i < blocks.length; i ++)
        {
            temps[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return temps;
    }

    /**
     * Determines whether or not a set of given locations are empty.
     * 
     * @param gr the grid in which to use to determine if the blocks are empty or not
     * @param locs the location array from which to check if empty or not.
     * 
     * @return true if all the positions are null 
     *         otherwise false
     */
    private boolean areEmpty(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for (int i = 0; i < locs.length; i ++)
        {
            Location location = locs[i];
            if (gr.isValid(location))
            {   if (gr.get(location) != null)
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves the blocks from their current position incremented by 
     * deltaRow and deltaCol
     * 
     * @param deltaRow is the amount of rows to translate
     * @param deltaCol  is the amount of columns to translate
     * 
     * @return true if the tetrad rotates 
     *         otherwise fales
     */
    public boolean translate (int deltaRow, int deltaCol)
    {
        Location [] removed = removeBlock();
        Location [] locations = new Location [4];
        for (int i = 0; i <locations.length; i ++)
        {
            locations[i] = new Location (removed[i].getRow() + deltaRow, 
                removed[i].getCol() + deltaCol);
        }
        if (areEmpty (liveIn, locations))
        {
            addToLocations(liveIn, locations);
            return true;
        }
        else
        {
            addToLocations(liveIn, removed);
            return false;
        }

    }

    /**
     * Rotates the tetrad 90 degrees clockwise on the pivot point block[0].
     * 
     * @return true if the tetrad can rotate and rotates 
     *         otherwise return false
     * 
     */
    public boolean rotate()
    {
        if (randShape ==2)
        {
            return true;
        }
        Location rotation = blocks[0].getLocation();
        int pivotRow = rotation.getRow();
        int pivotCol = rotation.getCol();
        Location [] shifting = removeBlock();
        Location [] locations = new Location [4];
        locations[0] = rotation;
        for (int i = 1; i <4; i ++)
        {
            Block current = blocks[i];
            locations[i] = new Location (shifting[i].getCol() -rotation.getCol() 
                + rotation.getRow(),
                rotation.getCol() + rotation.getRow() - shifting[i].getRow());
        }
        if (areEmpty (liveIn, locations))
        {
            addToLocations(liveIn, locations);
            return true;
        }
        else 
        {
            addToLocations (liveIn, shifting);
            return false;
        }
    }
}
