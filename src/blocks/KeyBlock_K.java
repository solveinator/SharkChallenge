package blocks;
import javax.swing.*;

import mechanics.Game;
import mechanics.Sound;

/**
 * A KeyBlock acts as a key object in the game. After being "picked up" (its action), it gives
 * the player the ability to walk on the LockBlock.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/26/2015
 */
public class KeyBlock_K extends ActionBlock
{
	private static boolean walkable = true;
	
    /**
     * Constructor for objects of class KeyBlock
     * 
     * @param int The integer position of the block. 
     */
    public KeyBlock_K(int position)
    {
        super(false, position, Block.PICTURES.get("KEY_ICON"));
    }
    
    public KeyBlock_K()
    {
        super(false, -1, Block.PICTURES.get("KEY_ICON"));
    }
    
    /**
     * Picks up the key, giving the player the ability to unlock the LockBlock later. 
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action. 
     */
    public void act(String direction)
    {
        //Note: the direction parameter is not used.
        Game game = getGame();
        int position = getPosition();
        if(game.getBackpack().addBackpackItem(this)) //will be false if there is no room
        {
        Sound.playSound("ting"); 
        game.changePermanentBoard(position, "E");
        Block replacementBlock = game.getReplacementBlock(position);
        game.setBlock(position, replacementBlock);
        replacementBlock.updateGuiBlock();
        }
        else
        {
        game.changePermanentBoard(position, "K");
        Block replacementBlock = game.getReplacementBlock(position);
        game.setBlock(position, replacementBlock);
        replacementBlock.updateGuiBlock();
        }
    }
    public boolean getWalkable() {
    	return walkable;
    }
    
    public Block clone(int position) {
    	return new KeyBlock_K(position);
    }
}