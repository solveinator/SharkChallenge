package blocks;
import javax.swing.*;

import mechanics.Game;
import mechanics.Sound;

/**
 * The LockBlock acts as a locked door. If the player has not already picked up the key,
 * the player will be unble to walk on a LockBlock square. 
 * 
 * Locks and Keys are non-specific, so once the player has picked up a key, the player 
 * will be able to unlock any LockBlock space that she chooses and it will stay unlocked 
 * for the rest of the game. However, each key can only be used once.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/26/2015
 */
public class LockBlock_L extends ActionBlock
{
	private boolean walkable;
    /**
     * Constructor for objects of class LockBlock
     * 
     * @param int The integer position of the block.  
     */
    public LockBlock_L(int position)
    {
        super(false, position, Block.PICTURES.get("LOCK_ICON"));
    }
    
    public LockBlock_L()
    {
        super(false, -1, Block.PICTURES.get("LOCK_ICON"));
    }

    /**
     * Replaces the LockBlock with an EmptyBlock if the player has a key. 
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action. 
     */
    public void act(String direction)
    {
        //Note, the direction is not used here. 
        int position = getPosition();
        Game game = getGame();
        if(game.getBackpack().hasItem(new KeyBlock_K(-1)))
        {
            Sound.playSound("lockClick");
            game.getBackpack().removeBackpackItem(new KeyBlock_K(-1));
            game.changePermanentBoard(position, "E");
            Block replacementBlock = game.getReplacementBlock(position);
            game.setBlock(position, replacementBlock);
            replacementBlock.updateGuiBlock(); 
        }
    }
    
    public Block clone(int position)
    {
        return new LockBlock_L(position);
    }
    
    public boolean getWalkable()
    {
        return walkable;
    }
}