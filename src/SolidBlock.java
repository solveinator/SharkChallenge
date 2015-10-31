import javax.swing.*;

/**
 * SolidBlocks act as walls. The do not interact in any way and the player cannot walk on them. 
 * 
 * @author Solveig Osborne 
 * @version 05/22/2015
 */
public class SolidBlock extends BackgroundBlock
{
    /**
     * Constructor for objects of class SolidBlock
     * 
     * @param int The integer position of the block.
     */
    public SolidBlock(int position)
    {
        super(false, position, Block.SOLID_ICON);
    }
}
