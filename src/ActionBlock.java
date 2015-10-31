import javax.swing.*;

/**
 * ActionBlock is an abstract class which provides a framework for blocks which perform an action but
 * do not move.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/17/2015
 */
public abstract class ActionBlock extends Block
{
    /**
     * Constructor for objects of class ActionBlock
     * 
     * @param boolean Whether the player is able to move onto the block. (This will be true for most blocks)
     * @param boolean Whether the block can move or not. 
     * @param int The integer position of the  block.
     * @param ImageIcon The image associated with that particular tile. Image can be selected from the public 
     * images fields stored in this class. 
     */
    public ActionBlock(boolean canWalkOn, boolean canMove, int position, ImageIcon image)
    {
        super(canWalkOn, canMove, position, image);
    }

    /**
     * Moves the block to the location specified. 
     * 
     * @param int The integer position of new location.
     */
    public void move(String direction)
    {
    //This block does not move.    
    }
    
    /**
     * Makes the block perform its action. This block does not do anything. 
     * 
     * @param int The integer input. 
     */
    public abstract void act(String direction);
}    