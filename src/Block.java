import javax.swing.*;


/**
 * The block describes block objects.
 * 
 * @author Solveig Osborne
 * @version CS162 Final 05/22/2015
 */
public abstract class Block
{   
    private boolean deadly;
    private boolean walkable;
    private boolean pushable;
    private ImageIcon icon;
    private int position;
    
    private static Game game;
    
    public static final ImageIcon SOLID_ICON = new ImageIcon("images/solid.png");
    public static final ImageIcon EMPTY_ICON = new ImageIcon("images/blank.jpg");
    public static final ImageIcon WATER_ICON = new ImageIcon("images/water.png");
    public static final ImageIcon GENERATOR_ICON = new ImageIcon("images/generator.png");
    public static final ImageIcon MOVEABLE_ICON = new ImageIcon("images/moveable.png");
    public static final ImageIcon LOCK_ICON = new ImageIcon("images/lock.png");
    public static final ImageIcon FINISH_ICON = new ImageIcon("images/finish2.jpg");
    public static final ImageIcon KEY_ICON = new ImageIcon("images/key.png");
    public static final ImageIcon MUD_ICON = new ImageIcon("images/mud.png");
    public static final ImageIcon GIRL_ICON = new ImageIcon("images/person.jpg");
    public static final ImageIcon DROWNING_ICON = new ImageIcon("images/splash.png");
    public static final ImageIcon BLOODY_WATER_ICON = new ImageIcon("images/bloodyWater.png");
    public static final ImageIcon DEATH_BY_SHARK_ICON = new ImageIcon("images/deathByShark.png");
    public static final ImageIcon SHARK_ICON = new ImageIcon("images/shark.png");
    
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
    public Block(boolean canWalkOn, boolean canMove, int position, ImageIcon icon)
    {
        this.walkable = canWalkOn;
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
     * Sets the for a specific block. 
     * 
     * @param ImageIcon The new image for that block.
     */
    public void setImage(ImageIcon image)
    {
        icon = image;
    }
    
    /**
     * Returns whether the block is walkable or not.
     * 
     * @return boolean Whether the block is walkable or not. 
     */
    protected boolean getWalkable()
    {
        return walkable;
    }
    
    /**
     * Returns the integer position of the block, if it has one. If it does not currently have a position, the
     * position is set to -1. 
     * 
     * @return int The position of the block. 
     */
    protected int getPosition()
    {
        return position;
    }
    
    /**
     * Sets the integer position of the block. 
     * 
     * @param int The position of the block. 
     */
    protected void setPosition(int newPosition)
    {
        position = newPosition;
    }
    
    /**
     * Sets the game field. 
     * 
     * @return Game The game. 
     */
    public static void setGame(Game theGame)
    {
        game = theGame;
    }
    
     /**
     * Returns the game field. 
     * 
     * @return Game The static game field shared by all the blocks. 
     */
    public static Game getGame()
    {
        return game;
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
            target = target - game.getBoardWidth(); 
        }
        else if(direction == "down") {
            target = target + game.getBoardWidth();
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
        game.getGui().updateBlock(position);
    }
}
