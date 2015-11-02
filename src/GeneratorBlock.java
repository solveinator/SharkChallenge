import javax.swing.*;
import java.util.HashMap;

/**
 * A GeneratorBlock produces PushableBlocks when touched.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/30/2015
 */
public class GeneratorBlock extends ActionBlock
{
    private String prototypeCode;
    //private HashMap<String, String> imageAddresses = new HashMap <String, ImageIcon>();
    
    //imageAddresses.put("Default", "images/generator.png");
    //imageAddresses.put("K", "images/generatorKey.png");
    
    /**
     * Constructor for objects of class EmptyBlock
     * 
     * @param int The integer position of the block.  
     */
    public GeneratorBlock(Game game, int position, String prototypeCode)
    {
        super(false, false, position, Block.PICTURES.get("GENERATOR_ICON"));
        this.prototypeCode = prototypeCode;
        ImageIcon newImage = Block.PICTURES.get("GENERATOR_ICON");
        if(prototypeCode.equals("K"))
        {
        newImage = new ImageIcon("images/generatorKey.png");
        }       
        setImage(newImage);
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
            game.setBlock(newPosition, game.constructBlock(prototypeCode, newPosition));
            game.constructBlock(prototypeCode, newPosition).updateGuiBlock();
            if(prototypeCode.equals("S"))
            {
                game.pause();
            }
        }
    }

}
