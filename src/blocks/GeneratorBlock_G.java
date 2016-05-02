package blocks;
import javax.swing.*;

import mechanics.Game;
import mechanics.Sound;

import java.util.HashMap;
import java.util.*;

/**
 * A GeneratorBlock produces PushableBlocks when touched.
 * 
 * @param <E> the type of the value being generate
 * @author Solveig Osborne 
 * @version CS162 Final 05/30/2015
 */
public class GeneratorBlock_G extends ActionBlock
{
	private static boolean walkable = false;	
    private Block prototypeBlock;
    //private HashMap<String, String> imageAddresses = new HashMap <String, ImageIcon>();
    
    //imageAddresses.put("Default", "images/generator.png");
    //imageAddresses.put("K", "images/generatorKey.png");
    
    /**
     * Constructor for objects of class EmptyBlock
     * 
     * @param int The integer position of the block.  
     */
    public GeneratorBlock_G(int position, Block prototypeBlock)
    {
        super(false, position, Block.PICTURES.get("GENERATOR_ICON"));
        this.prototypeBlock = prototypeBlock;
        ImageIcon newImage = Block.PICTURES.get("GENERATOR_ICON");
        if(prototypeBlock instanceof KeyBlock_K)
        {
        newImage = new ImageIcon("images/generatorKey.png");
        }       
        setImage(newImage);
    }

    public GeneratorBlock_G()
    {
        super(false, -1, Block.PICTURES.get("GENERATOR_ICON"));
        ImageIcon newImage = Block.PICTURES.get("GENERATOR_ICON");
    }
    /**
     * Makes the block perform its action.
     * 
     * @param String The direction of the action. This value is not actually used in any
     * way by this class, so ANY string input will result in the same action.
     */
    public void act(String direction)
    {   
        Sound.playSound("click");
        int newPosition = getTargetLocation(this, direction);
        Game game = getGame();
        Block targetBlock = game.getBlock(newPosition);
        if(targetBlock.getWalkable() == true) 
        {
            //Block newBlock = new PushableBlock(newPosition);
            //getGame().setBlock(newPosition, newBlock);
            game.setBlock(newPosition, prototypeBlock.clone(newPosition));
            prototypeBlock.clone(newPosition).updateGuiBlock();
            if(prototypeBlock instanceof SharkBlock_S)
            {
                game.pause();
            }
        }
    }

    public boolean getWalkable() {
    	return walkable;
    }
    
    public Block clone(int position) {
    	return new GeneratorBlock_G(position, prototypeBlock);
    }
}
