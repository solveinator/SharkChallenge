import javax.swing.*;

/**
 * FinishBlock acts as a finish line for the game. They player is able to walk on it, and if they player 
 * reaches the FinishBlock, they have won the game!
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/13/2015
 */
public class FinishBlock extends ActionBlock
{
    /**
     * Constructor for objects of class FinishBlock
     * 
     * @param int The integer position of the block.
     */
    public FinishBlock(int position)
    {
        super(true, false, position, Block.FINISH_ICON);
    }

    /**
     * Makes the player win the game.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action. 
     */
    public void act(String direction)
    {
       getGame().setWon(true);
       getGame().getTimer().stop();
    }
}