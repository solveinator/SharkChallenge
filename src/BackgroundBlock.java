import javax.swing.*;

/**
 * BackgroundBlock is an abstract class which provides a framework for blocks which do not move or act.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/22/2015
 */
public abstract class BackgroundBlock extends Block
{
    /**
     * Constructor for objects of class BackgoundBlock
     * 
     * @param boolean Whether the player is able to move onto the block. (This will be true for most blocks)
     * @param int The block's integer position
     * ImageIcon The block image that will be displayed.
     */
    public BackgroundBlock(boolean canWalkOn, int position, ImageIcon image)
    {
        super(canWalkOn, false, position, image);
    }

    /**
     * Moves the block to the location specified. 
     * 
     * @param int The integer position of new location.
     */
    public void move(String direction)
    {
    //This block does not do anything but look pretty.    
    }
    
    /**
     * Makes the block perform its action. This block does not do anything. 
     * 
     * @param int The integer input. 
     */
    public void act(String direction)
    {
    //This block does not do anything but look pretty.
    }
}    