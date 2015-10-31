import javax.swing.*;

/**
 * EmptyBlocks act as floor tiles. They do not interact in any way and the player is able to walk on them.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/22/2015
 */
public class EmptyBlock extends BackgroundBlock
{
    
    /**
     * Constructor for objects of class EmptyBlock.
     *  
     * @param int The integer position of the block.
     */
    public EmptyBlock(int position)
    {
        super(true, position, Block.EMPTY_ICON);
    }
}
