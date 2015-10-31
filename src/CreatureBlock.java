import javax.swing.*;
import java.util.Random;

/**
 * Abstract class CreatureBlock - Blocks of this type are animates and move according to their own algorithms. 
 * 
 * @author Solveig Osborne
 * @version 05/22/2015
 */

public abstract class CreatureBlock extends Block
{ 

   
   public CreatureBlock(int position, ImageIcon image)
   {
   super(true, true, position, image);
   }
   
   /**
     * Moves the block to the location specified. 
     * 
     * @param int The integer position of new location.
     */
    public abstract void move(String direction);
    
    /**
     * Makes the block perform its action.
     * 
     * @param int The integer input. In general, this information is provided to convey information about the 
     * direction of the motion which caused the action. 
     */
    public abstract void act(String direction);
    
    
    
}
