import javax.swing.*;

/**
 * WaterBlocks are able to be walked on but they will kill the player if they are walked on; they 
 * do not interact in any other way. 
 * 
 * Because they are deadly, the WaterBlock class implements the Deadly interface.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/22/2015
 */
public class WaterBlock extends BackgroundBlock implements Deadly
{
    /**
     * Constructor for objects of class EmptyBlock
     *  
     * @param int The integer position of the block.  
     */
    public WaterBlock(int position)
    {
        super(true, position, Block.PICTURES.get("WATER_ICON"));
    }
    
    public ImageIcon getDeathImage()
    {
        return Block.PICTURES.get("DROWNING_ICON");
    }
    
    public void playDeathSound()
    {
        Sound.playSound("splash");
    }
}
