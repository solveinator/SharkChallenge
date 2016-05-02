package blocks;
import java.io.File;
import java.util.HashMap;

import javax.swing.*;

import mechanics.Game;


/**
 * The block describes block objects.
 * 
 * @author Solveig Osborne
 * @version CS162 Final 05/22/2015
 */
public abstract class Block
{   
    private boolean pushable;
    private ImageIcon icon;
    private ImageIcon deathImage;
    private int position;
    
    private static String PREFIX = "";
    private static Game GAME;
    
    public static HashMap<String, ImageIcon> PICTURES = new HashMap<String, ImageIcon>();
    
    /**
     * Constructor for an abstract block. 
     *  
     * @param boolean Whether the player is able to move onto the block. (This will be true for most blocks)
     * @param boolean Whether the block can move or not. 
     * @param int The integer position of the  block.
     * @param ImageIcon The image associated with that particular tile. Image can be selected from the public 
     * images fields stored in this class. 
     * 
     */
    public Block(boolean canMove, int position, ImageIcon icon)
    {
        this.pushable = canMove;
        this.icon = icon;     
        this.position = position;
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
    
    /**
     * Returns the image for that type of block. 
     * 
     * @returns ImageIcon The image for that type of block.
     */
    public ImageIcon getImage()
    {
        return icon;
    }
    
    /**
     * Sets the death image for a specific block. 
     * 
     * @param ImageIcon The new death image for that block.
     */
    public void setImage(ImageIcon image)
    {
        icon = image;
    }
    
    public ImageIcon getDeathImage()
    {
        return deathImage;
    }
    
    /**
     * Sets the death image for a specific block. A block can only be deadly if
     * it has a deathImage assigned to it. 
     * If the deathImage is null, the block is not deadly. 
     * 
     * @param ImageIcon The new death image for that block.
     */
    public void setDeathImage(ImageIcon image)
    {
        deathImage = image;
    }
    
    /**
     * Returns whether the block is walkable or not.
     * 
     * @return boolean Whether the block is walkable or not. 
     */
    public abstract boolean getWalkable();
    
    /**
     * Creates a non-shallow copy of a block, and gives it the specified position. 
     * @param position
     * @return The cloned block
     */
    public abstract Block clone(int position);    
    
    /**
     * Returns the integer position of the block, if it has one. If it does not currently have a position, the
     * position is set to -1. 
     * 
     * @return int The position of the block. 
     */
    public int getPosition()
    {
        return position;
    }
    
    /**
     * Sets the integer position of the block. 
     * 
     * @param int The position of the block. 
     */
    public void setPosition(int newPosition)
    {
        position = newPosition;
    }
    
    /**
     * Gets whether the block is deadly or not. A block is deadly iff it has a death image assigned to it. 
     * 
     * @return boolean Whether the block will kill you. 
     */
    public boolean getDeadly(){
    	return !(deathImage == null); 
    }
    
    public void playDeathSound(){
    }
    
    /**
     * Sets the game field. 
     * 
     * @return Game The game. 
     */
    public static void loadImages()
    {
    	String directory = System.getProperty("user.dir");
    	if(!(directory.contains("SharkChallenge" + File.separator + "src"))) {
        	PREFIX = "src" + File.separator + "images" + File.separator;
        	}
        else {
        	PREFIX = "images" + File.separator;
        	}
    	PICTURES.put("SOLID_ICON", new ImageIcon(PREFIX + "solid.png"));
    	PICTURES.put("EMPTY_ICON", new ImageIcon(PREFIX + "blank.jpg"));
    	PICTURES.put("WATER_ICON", new ImageIcon(PREFIX + "water.png"));
    	PICTURES.put("GENERATOR_ICON", new ImageIcon(PREFIX + "generator.png"));
    	PICTURES.put("MOVEABLE_ICON", new ImageIcon(PREFIX + "moveable.png"));
    	PICTURES.put("LOCK_ICON", new ImageIcon(PREFIX + "lock.png"));
    	PICTURES.put("FINISH_ICON", new ImageIcon(PREFIX + "finish2.jpg"));
    	PICTURES.put("KEY_ICON", new ImageIcon(PREFIX + "key.png"));
    	PICTURES.put("MUD_ICON", new ImageIcon(PREFIX + "mud.png"));
    	PICTURES.put("GIRL_ICON", new ImageIcon(PREFIX + "person.jpg"));
    	PICTURES.put("DROWNING_ICON", new ImageIcon(PREFIX + "splash.png"));
    	PICTURES.put("BLOODY_WATER_ICON", new ImageIcon(PREFIX + "bloodyWater.png"));
    	PICTURES.put("DEATH_BY_SHARK_ICON", new ImageIcon(PREFIX + "deathByShark.png"));
    	PICTURES.put("SHARK_ICON", new ImageIcon(PREFIX + "shark.png"));
    	
    }
    public static void setGame(Game theGame)
    {
        GAME = theGame;
    }
    
     /**
     * Returns the game field. 
     * 
     * @return Game The static game field shared by all the blocks. 
     */
    public static Game getGame()
    {
        return GAME;
    }
    
     /**
     * Returns the integer location that a block will go to if it is moved in the specified 
     * direction.
     * 
     * @param int The integer representation of the starting location. 
     * @param String The direction of movement. Options are "up" "down" "left" or "right".
     * @return int The integer location where movement in the specified direction will end.
     */
    public int getTargetLocation(int target, String direction)
    {
        if(direction == "up") {
            target = target - GAME.getBoardWidth(); 
        }
        else if(direction == "down") {
            target = target + GAME.getBoardWidth();
        }
        else if(direction == "left") {
            target = target - 1;
        }
        else if(direction == "right") {
            target = target + 1;
        }
        return target;
    }
    
     /**
     * Returns the integer location that a block will go to if it is moved in the specified 
     * direction.
     * 
     * @param Block The block that is moving. 
     * @param String The direction of movement. Options are "up" "down" "left" or "right".
     * @return int The integer location where movement in the specified direction will end.
     */
    public int getTargetLocation(Block block, String direction)
    {
        int oldPosition = block.getPosition();
        if(direction.equals(""))
        {
        return 0; 
        }
        else 
        {
        int target = getTargetLocation(oldPosition, direction);
        return target;
        }
    }
    
    /**
     * Tells Gui to update block onces it has been changed.
     * 
     * @param int The integer location of the tile which needs updated. 
     */
    public void updateGuiBlock()
    {
        GAME.getGui().updateBlock(position);
    }
    
    }
