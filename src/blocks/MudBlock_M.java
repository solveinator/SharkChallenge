package blocks;
import javax.swing.*;

/**
 * A MudBlock is created when a PushableBlock is pushed into a WaterBlock. It turns into dry land when walked on.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/13/2015
 */
public class MudBlock_M extends ActionBlock
{
	private static boolean walkable = true;
    /**
     * Constructor for objects of class MoveableBlock
     * 
     * @param int The integer position of the block. 
     */ 
 
    public MudBlock_M(int position)
    {
        super (false, position, Block.PICTURES.get("MUD_ICON"));
    }

    public MudBlock_M()
    {
        super (false, -1, Block.PICTURES.get("MUD_ICON"));
    }
    /**
     * Makes the block perform its action.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action.
     */
    public void act(String direction)
    {
        Block block = new EmptyBlock_E(getPosition());
        getGame().setBlock(getPosition(), block);
        getGame().changePermanentBoard(getPosition(), "E");//Turn into land
    }
    
    public Block clone(int position) {
    	return new MudBlock_M(position);
    }
    
    public boolean getWalkable() {
    	return walkable;
    }
}

