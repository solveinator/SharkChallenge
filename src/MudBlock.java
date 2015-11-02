import javax.swing.*;

/**
 * A MudBlock is created when a PushableBlock is pushed into a WaterBlock. It turns into dry land when walked on.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/13/2015
 */
public class MudBlock extends ActionBlock
{
    /**
     * Constructor for objects of class MoveableBlock
     * 
     * @param int The integer position of the block. 
     */ 
 
    public MudBlock(int position)
    {
        super (true, false, position, Block.PICTURES.get("MUD_ICON"));
    }

    /**
     * Makes the block perform its action.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action.
     */
    public void act(String direction)
    {
        Block block = new EmptyBlock(getPosition());
        getGame().setBlock(getPosition(), block);
        getGame().changePermanentBoard(getPosition(), "E");//Turn into land
    }
}

