import javax.swing.*;

/**
 * The PersonBlock represents the player in the game. The PersonBlock responds to user
 * input from the keyboard and causes other blocks to move or act in response to the 
 * pplayer's movements.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/30/2015
 */
public class PersonBlock extends MoveableBlock
{

    /**
     * Constructor for objects of class PersonBlock.
     *  
     * @param int The integer position of the block.
     */
    public PersonBlock(int position)
    {
        super(false, position, GIRL_ICON);
    }

    /**
     * Moves the block to the location specified, removes it from its previous location,
     * and updates the gui to reflect the change.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action.
     */
    public void move(String direction)
    {
        int oldPosition = getPosition();
        int newPosition = getTargetLocation(this, direction);
        Game game = getGame();
        Block targetBlock = game.getBlock(newPosition);
        if(targetBlock instanceof MoveableBlock)
        {
            targetBlock.move(direction);
        }

        setPosition(newPosition);

        Block replacementBlock = game.getReplacementBlock(oldPosition);
        game.setBlock(oldPosition, replacementBlock);
        replacementBlock.updateGuiBlock();
        
        if(targetBlock instanceof Deadly)
        {
            //Deadly targetBlockk = (Deadly) targetBlock;
            //targetBlockk.setDeadImage();
            //targetBlock.updateGuiBlock();
            //game.getGui().dead(newPosition);
            game.death(targetBlock);
        }
        else{
            game.setBlock(newPosition, this);
            updateGuiBlock();
        }           
    }

    /**
     * Makes the block perform its action.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action.
     */
    public void act(String direction)
    {
        int newPosition = getTargetLocation(this, direction);
        if(newPosition >= 0 || newPosition < getGame().getBoardWidth() * getGame().getBoardWidth())
        {
            Block targetBlock = getGame().getBlock(newPosition);
            targetBlock.act(direction);
        }
    }     
}
