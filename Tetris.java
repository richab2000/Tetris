
/**
 * Tetris class lets the user play a game of tetris.
 * 
 * @author Richa Bhattacharya 
 * With assistance from Tanay Kamat and Mrs. King
 * @version March 13, 2017
 */
public class Tetris implements ArrowListener
{
    // instance variables
    private MyBoundedGrid<Block> track;
    private BlockDisplay display;
    private Tetrad tetrad;

    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        track = new MyBoundedGrid<Block>(20,10);
        display = new BlockDisplay(track);
        display.setArrowListener(this); 
        display.setTitle("Tetris");
        tetrad = new Tetrad (track);
        display.showBlocks();

    }

    /**
     * Turns the tetrad 90 degrees clockwise for each press of the "up" 
     * arrow button.
     */
    public void upPressed()
    {
        tetrad.rotate();
        display.showBlocks();
    }

    /**
     * Makes the tetrad move one row down
     */
    public void downPressed()
    {
        tetrad.translate( 1 , 0);
        display.showBlocks();
    }

    /**
     * Makes the tetrad move to the left
     */
    public void leftPressed()
    {
        tetrad.translate(0, -1);
        display.showBlocks();
    }

    /**
     * Makes the tetrad move to the right
     */
    public void rightPressed()
    {
        tetrad.translate(0, 1);
        display.showBlocks();
    }

    /**
     * Checks if a row has all of its spaces occupied
     * 
     * @param row is the index of the row 
     * 
     * @return true if the row is full otherwise false
     */
    public boolean isCompletedRow(int row )
    {
        for (int i = 0; i <track.getNumCols(); i ++)
        {
            Location next = new Location (row, i);
            if (track.get(next) ==null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears the row if its completed
     * 
     * @param row is the row which is completely filled
     */
    public void clearRow(int row)
    {
        for (int i = 0; i < track.getNumCols(); i ++)
        {
            Location next = new Location (row, i);
            track.get(next).removeSelfFromGrid();

        }
        for (int i = row; i >=1; i--)
        {
            for (int j = 0;j <track.getNumCols(); j++)
            {
                Location next = new Location (i, j);
                Location next1 = new Location (i +1, j);
                if (track.get(next) !=null)
                {
                    track.get(next).moveTo(next1);
                }
            }
            
        }
    }

    /**
     * Keeps track of how many rows have been completed 
     * (method of "keeping score" for the game)
     * 
     * @return the number of rows completed
     */
    public int clearCompletedRows()
    {
        int count = 0;
        for (int i = track.getNumRows() -1; i >=0; i--)
        {
            if(isCompletedRow(i))
            {
                clearRow(i);
                i++;
                count++;
            }
        }
        return count;

    }

    /**
     * Plays the game tetris
     */
    public void play()
    {
        boolean gameOver = false;
        int score = 0;
        int level = 1;
        try
        {
            while (!gameOver)
            {
                display.setTitle("Level: " + level + " Score: " + score);
                int fixed = level - 1;
                Thread.sleep(1000-100*(fixed));
                boolean nextMove = tetrad.translate(1,0);
                if (!nextMove)
                {
                    int count = clearCompletedRows();
                    if (count > 0)
                    {
                        score = score + count;
                    }
                    if (score == 10*level)
                    {
                        level ++;
                        if (level >= 10)
                        {
                            gameOver = true;
                            display.setArrowListener(null);
                            display.setTitle("YOU WIN");
                            System.out.println("Great Job! You won! ");
                            System.out.println("Here's your final score: " + score);

                        }
                    }
                    tetrad = new Tetrad (track);
                    if (!tetrad.translate(1,0))
                    {
                        gameOver = true;
                        display.setArrowListener(null);
                        display.setTitle("GAME OVER!");
                        System.out.println("Nice try! ");
                        System.out.println("Here's your final score: " + score);

                    }
                }
                display.showBlocks();
            }
        }
        catch (InterruptedException e)
        {
            //ignore
        }
    }

    /**
     * Runs the code 
     */
    public static void main ()
    {
        Tetris bob = new Tetris ();
        bob.play();
    }
}
