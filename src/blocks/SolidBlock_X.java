package blocks;
import javax.swing.*;

/**
 * SolidBlocks act as walls. The do not interact in any way and the player cannot walk on them. 
 * 
 * @author Solveig Osborne 
 * @version 05/22/2015
 */
public class SolidBlock_X extends BackgroundBlock
{
	private static boolean walkable = false;
	
	public SolidBlock_X()
    {
        super(-1, Block.PICTURES.get("SOLID_ICON"));
    }
    /**
     * Constructor for objects of class SolidBlock
     * 
     * @param int The integer position of the block.
     */
    public SolidBlock_X(int position)
    {
        super(position, Block.PICTURES.get("SOLID_ICON"));
    }
    
    public boolean getWalkable() {
    	return walkable;
    }
    
    public Block clone(int position) {
    	return new SolidBlock_X (position);
    }
}
