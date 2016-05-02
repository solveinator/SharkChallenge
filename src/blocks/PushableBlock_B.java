package blocks;
import javax.swing.*;

import mechanics.Game;
import mechanics.Sound;

import java.util.ArrayList;

/**
 * A PushableBlock is a block which can be pushed by the player as the player moves around. It can only be pushed 
 * onto tiles that are "walkable." If the player tries to push a PushableBlock onto a tile where it cannot move,
 * both the player and the PushableBlock will not move.
 * 
 * @author Solveig Osborne 
 * @version 05/13/2015
 */
public class PushableBlock_B extends MoveableBlock
{
	private static boolean walkable = true;
    
    /**
     * Constructor for objects of class PushableBlock
     * 
     * @param int The integer position of the block. 
     */
    public PushableBlock_B(int position)
    {
        super(position, Block.PICTURES.get("MOVEABLE_ICON"));
    }
    
    public PushableBlock_B()
    {
        super(-1, Block.PICTURES.get("MOVEABLE_ICON"));
    }

    /**
     * Moves the block to the location specified. 
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action.
     */
    public void move(String direction)
    {
        Sound.playSound("scrape");
        int oldPosition = getPosition();
        int newPosition = getTargetLocation(this, direction);
        
        setPosition(newPosition);
        Game game = getGame();
        Block targetBlock = game.getBlock(newPosition);
        if(targetBlock instanceof WaterBlock_W)
        {
            targetBlock = new MudBlock_M(newPosition);
            game.setBlock(newPosition, targetBlock);
            game.changePermanentBoard(newPosition, "E");
            game.killShark(newPosition); //only happens if there was actually a shark there to begin with.
        }
        else
        {
            game.setBlock(newPosition, this);
        }
        
        updateGuiBlock();
        
        Block replacementBlock = getGame().getReplacementBlock(oldPosition);
        getGame().setBlock(oldPosition, replacementBlock);
        replacementBlock.updateGuiBlock();         
    }
    
    /**
     * Makes the block perform its action.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action. 
     */
    public void act(String direction)
    {
    //This block does not act.
    }
    
    public boolean getWalkable() {
    	return walkable;
    }
    
    public Block clone(int position) {
    	return new PushableBlock_B (position);
    }
}
