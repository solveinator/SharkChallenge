import javax.swing.*;

/**
 * The deadly interface allows certain blocks to be designated as "deadly" without having to maintain a boolean
 * field in the Block class. This is particularly beneficial beccause most blocks are NOT deadly. 
 * 
 * Classes known to implement the Deadly interface are: SharkBlock, WaterBlock.
 * 
 * @author Solveig Osborne
 * @version CS162 Final 05/22/2015
 */
public interface Deadly
{
    /**
     * Returns the image that should be displayed if the player dies on this type of square.  
     * 
     * @return ImageIcon
     */
    ImageIcon getDeathImage();
    
    /**
     * Plays the sound that corresponds to death via this block.
     */
    void playDeathSound();
}
