import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.lang.Integer;

/**
 * A SharkBlock is essentially a WaterBlocks with a shark in it. Each SharkBlock moves randomly at a 
 * set time interval, but they are only able to move into uninhabited WaterBlock spaces. 
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 06/3/2015
 */
public class SharkBlock extends WaterBlock implements ActionListener, Deadly
{
    private Random randNum;
    private Game game;
    private ImageIcon deathImage;

    /**
     * Constructor for objects of class SharkBlock
     * 
     * @param position
     */
    public SharkBlock(int position)
    {
        super(position);
        setImage(Block.PICTURES.get("SHARK_ICON"));   
        game = getGame();
        game.getTimer().addActionListener(this);
        game.addShark(this);
        randNum = new Random();
        deathImage = Block.PICTURES.get("BLOODY_WATER_ICON"); 
    }

    /**
     * Makes the sharks move randomly to a square adjacent to its current position or possibly remain 
     * in its current position. 
     */
    public void moveRandomly()
    {
        String threat = game.getSharkThreat();
        int oldPosition = getPosition();
        ArrayList<Integer> possibleNewPositions = new ArrayList<Integer>();
        int newPosition;

        //All target locations within range
        int up = getTargetLocation(oldPosition, "up");
        possibleNewPositions.add(up - 1);
        possibleNewPositions.add(up);
        possibleNewPositions.add(up + 1);
        int down = getTargetLocation(oldPosition, "down");
        possibleNewPositions.add(down - 1);
        possibleNewPositions.add(down);
        possibleNewPositions.add(down + 1);
        possibleNewPositions.add(getTargetLocation(oldPosition, "right"));
        possibleNewPositions.add(oldPosition);
        possibleNewPositions.add(getTargetLocation(oldPosition, "left"));

        int eatingPosition  = -1;

        Iterator it = possibleNewPositions.iterator();

        while(it.hasNext())
        {
            Integer j = (Integer) it.next();
            int i = j.intValue();
            //Identify and remove locations not on the board
            if(i < 0 || i >= game.getBoardHeight() * game.getBoardWidth())
            {
                it.remove();
            }
            //If playing on medium or high threat level, identify whether the player can be eaten
            else if(i == game.getPlayer().getPosition() && 
                ( threat.equals("medium") || threat.equals("high") ))
            {
                eatingPosition = i; //If the player is within range, store that location for later.
            }
            else
            //Remove all potential target blocks that are not waterblocks or the position of the player.
            {
                Block block = game.getBlock(i);
                if(!(block instanceof WaterBlock))
                {
                    it.remove();
                }
            }
        }

        //If playing on hard, ignore other potential locations and eat the player. 
        if(threat.equals("high") && eatingPosition != -1)
        {
            newPosition = eatingPosition;
        }
        //If playing on medium or easy, select randomly from your valid target locations.
        else
        {        
        int random = randNum.nextInt(possibleNewPositions.size());
        newPosition = possibleNewPositions.get(random);
        }

        if(newPosition == eatingPosition){
            deathImage = Block.PICTURES.get("DEATH_BY_SHARK_ICON");
            game.setDead(true);
        }

        //Update GUI
        if(newPosition != oldPosition)
        {
            setPosition(newPosition);
            game.setBlock(newPosition, this);

            updateGuiBlock();

            Block replacementBlock = new WaterBlock(oldPosition);
            game.setBlock(oldPosition, replacementBlock);
            replacementBlock.updateGuiBlock();
        }

        //Call death sequence if dead.
        if(game.getDead())
        {
            game.death(this);
        }

    }

    /**
     * Moves the block to the location specified. 
     * 
     * @param int The integer position of new location.
     */
    public void move(String direction)
    {
        //Sharks do not move out of the way when pushed. 
    }

    /**
     * Makes the block perform its action.
     * 
     * @param String The direction of the action.
     */
    public void act(String direction)
    {
        //This method is called via the takeStep() method, but SharkBlocks currently do not react
        //to human presence, so it is left blank. 
    }

    /**
     * Makes the sharks swim around randomly. This method is called at regular intervals by the timer.
     * 
     * @param ActionEvent The ActionEvent created by the timer at regular intervals. 
     */
    public void actionPerformed(ActionEvent e)
    {
        if(!game.getDead())
        {
        moveRandomly();
        }
    }

    /**
     * Stops the timer and replaces the normal picture with a death picture if the player dies. 
     */
    public ImageIcon getDeathImage()
    {
        return deathImage; 
    }

    public void playDeathSound()
    {
        Sound.playSound("splash");
        Sound.playSound("bite");
    }

}
