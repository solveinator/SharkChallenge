package blocks;
import javax.swing.*;

import mechanics.Sound;

/**
 * WaterBlocks are able to be walked on but they will kill the player if they are walked on; they 
 * do not interact in any other way. 
 * 
 * Because they are deadly, the WaterBlock class implements the Deadly interface.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/22/2015
 */
public class WaterBlock_W extends BackgroundBlock
{
	private static boolean walkable = true;
	
	public WaterBlock_W()
    {
        super(0, Block.PICTURES.get("WATER_ICON"));
        //Set the death image (which makes the tile deadly)
        this.setDeathImage(Block.PICTURES.get("DROWNING_ICON"));
    }
    /**
     * Constructor for objects of class EmptyBlock
     *  
     * @param int The integer position of the block.  
     */
    public WaterBlock_W(int position)
    {
        super(position, Block.PICTURES.get("WATER_ICON"));
        //Set the death image (which makes the tile deadly)
        this.setDeathImage(Block.PICTURES.get("DROWNING_ICON"));
    }
    
    public void playDeathSound()
    {
        Sound.playSound("splash");
    }
    
    public boolean getWalkable() {
    	return walkable;
    }
    
    public Block clone(int position) {
    	return new WaterBlock_W (position);
    }
}
