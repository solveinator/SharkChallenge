import javax.swing.*;
import java.util.ArrayList;

/**
 * MoveableBlock is an abstract class which provides a framework for blocks which move.
 * Blocks of this type may or may not act in addition to moving.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/30/2015
 */
public abstract class MoveableBlock extends Block
{
    /**
     * Constructor for objects of class MoveableBlock
     * 
     * @param boolean Whether the player is able to move onto the block. (This will be true for most blocks)
     * @param int The integer position of the block.
     * @param ImageIcon The image associated with this type of block.
     */
    public MoveableBlock(boolean canWalkOn, int position, ImageIcon image)
    {
        super(canWalkOn, true, position, image);
    }

    /**
     * Moves the block to the location specified, removes it from its previous location,
     * and updates the gui to reflect the change.
     * 
     * @param String The direction of movement.
     */
    public void move(String direction)
    {
        int oldPosition = getPosition();
        int newPosition = getTargetLocation(this, direction);
        
        setPosition(newPosition);
        getGame().setBlock(newPosition, this);
        updateGuiBlock();
        
        Block replacementBlock = getGame().getReplacementBlock(oldPosition);
        getGame().setBlock(oldPosition, replacementBlock);
        replacementBlock.updateGuiBlock(); 
    }
    
    /**
     * Makes the block perform its action. This block does not do anything. 
     * 
     * @param String The direction of movement.
     */
    public abstract void act(String direction);
    
    /**
     * Tests whether the player is able to move into a new square based on whether the block currently in that
     * positino is walkable or deadly. If the block is walkable, the method will return true. However, if the
     * block is deadly, the player will be killed, and the game will be terminated. 
     * 
     * @param String The direction of movement. 
     */
    public boolean moveableCanMove (Block block, String direction)
    {
        int newLocation = getTargetLocation(block, direction);
        if(newLocation < 0 || newLocation >= getGame().getBoardWidth() * getGame().getBoardWidth())
        {
            return false;
        }
        
        Block targetBlock = getGame().getBlock(newLocation);
        if(!targetBlock.getWalkable())
        {
            return false;
        }

        //If the new square containts a moveable block, you can only move there if that block 
        //has somewhere to move to...
        if(block instanceof PersonBlock && targetBlock instanceof MoveableBlock)
        {
            return moveableCanMove(targetBlock, direction);
        }
        else if(block instanceof MoveableBlock && targetBlock instanceof MoveableBlock)
        {
        return false;
        }
        else{
        return true; 
        }
    }  
    
}