package blocks;
import javax.swing.*;

/**
 * EmptyBlocks act as floor tiles. They do not interact in any way and the player is able to walk on them.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/22/2015
 */
public class EmptyBlock_E extends BackgroundBlock
{
	private static boolean walkable = true;
	
	public EmptyBlock_E()
    {
        super(-1, Block.PICTURES.get("EMPTY_ICON"));
    }
    
    /**
     * Constructor for objects of class EmptyBlock.
     *  
     * @param int The integer position of the block.
     */
    public EmptyBlock_E(int position)
    {
        super(position, Block.PICTURES.get("EMPTY_ICON"));
    }
    
    public Block clone(int position)
    {
        return new EmptyBlock_E(position);
    }
    
    public boolean getWalkable()
    {
        return walkable;
    }
}
