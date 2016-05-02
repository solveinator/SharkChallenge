package mechanics;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.util.HashMap;

import blocks.Block;
import blocks.EmptyBlock_E;
import blocks.FinishBlock_F;
import blocks.GeneratorBlock_G;
import blocks.KeyBlock_K;
import blocks.LockBlock_L;
import blocks.MudBlock_M;
import blocks.PersonBlock_P;
import blocks.PushableBlock_B;
import blocks.SharkBlock_S;
import blocks.SolidBlock_X;
import blocks.WaterBlock_W;

/**
 * The Game class is the traffic controller of the Solveig's Challenge game. The Game class is reposible for
 * reading the input files, transforming the input files into the game board, and repeating that process as 
 * necessary when reseting the level or changing levels. The Game class is also responsible for tracking and 
 * handling all creatures which move autonomously (rather than in response to the player's movement).
 * 
 * Player movement is initiated by a call to the Game's takeStep method from the GUI in response to 
 * the user pressing one of the arrow keys. However, once the takeStep method has been called, whether 
 * movement or action actually take place is handled entirely by the blocks themselves, first by the 
 * PersonBlock and then by whatever Block she would potentially be moving into. Thus, after the 
 * Game and Gui classes, the PersonBlock is the most important block. 
 * 
 * @author Solveig Osborne 
 * @version CS162 Final Project 06/01/2015
 */
public class Game
{
    //Board specifics
    //private ArrayList<String> levels; 
    private int numOfLevels;
    private int level; 
    private ArrayList<String> permanentBoard;
    private ArrayList<Block> board;
    public static String blockPathPrefix;
    private HashMap<String,Block> abvMap;
    private int startPosition;
    private int boardHeight;
    private int boardWidth;
    private Gui gui;

    private Timer timer;
    private Sound sound;

    //Game progress
    private PersonBlock_P player;
    private String sharkThreat;
    private Backpack backpack;
    private ArrayList<SharkBlock_S> sharks;

    private boolean dead;
    private boolean won;

    /**
     * Constructor for objects of class Game.  
     */
    public Game()
    {
        level = 1; //Set default level
        numOfLevels = 8;
        timer = new Timer(1000, null);
        Block.loadImages(); //Initialize all block images
        Block.setGame(this);
        sharkThreat = "medium"; //Set default shark threat level
        sharks = new ArrayList<SharkBlock_S>();
        initAbvMap();
        constructBoard();
        dead = false;
        won = false;        
        gui = new Gui(this, player);
        backpack = new Backpack(gui);
        sound = new Sound();        
        timer.start();
    }

    /**
     * Main method for Solveig's Challenge. Creates game and starts play. 
     */
    public static void main(String[] args)
    {
        Game game = new Game();
    }

    /**
     * Sets the level that will be played.
     * 
     * @param int The level number.
     */
    public void setLevel(int newLevelNum) throws InvalidLevelException  
    {
        if(newLevelNum < 0 || newLevelNum > numOfLevels - 1)
        {
            throw new InvalidLevelException(newLevelNum);
        }
        level = newLevelNum;
        gui.updateLevel(level);
        reset();
    }

    /**
     * Returns level number that is currently being played. 
     * 
     * @return int The level number.
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Changes how bloodthirsty the sharks are. When the setting is "low", the sharks will never attack.
     * When the setting is "medium", the sharks have the option to attack, but doing so will be treated with 
     * the same random approach that governs their normal movements. (i.e. it is just another possible
     * movement). When the setting is "high", sharks will attack every time they are within range.  
     * 
     * @param String The desired threat level that sharks will pose. Valid options are "low", "medium", and "high".
     */
    public void setSharkThreat(String newThreat)
    {
        if(newThreat.equals("low") || newThreat.equals("medium") || newThreat.equals("high"))
        {
            sharkThreat = newThreat;
        }
        //else do nothing because only valid option are accepted. 
    }

    /**
     * Returns the shark threat level of "low", "medium" or "high".
     * 
     * @return String The current threat level. Options are "low", "medium" or "high".
     */
    public String getSharkThreat() 
    {
        return sharkThreat;
    }

    /**
     * Returns total number of levels currently on file. 
     * 
     * @return int The number of levels currently on file.
     */
    public int getNumberOfLevels()
    {
        return numOfLevels;
    }

    /**
     * Returns the game's GUI.
     * 
     * @return Gui The game's GUI.  
     */
    public Gui getGui()
    {
        return gui;
    }

    /**
     * Sets whether the player is dead or not.
     * 
     * @param boolean Whether or not the player is dead or not. 
     */
    public void setDead(boolean isDead)
    {
        dead = isDead;
    }

    /**
     * Returns whether the player is dead or not.
     * 
     * @return boolean Whether or not the player is dead or not. 
     */
    public boolean getDead()
    {
        return dead;
    }

    /**
     * Sets whether the player has won the game.
     * 
     * @param boolean Whether or not the player has won. 
     */
    public void setWon(boolean hasWon)
    {
        won = hasWon;
    }

    /**
     * Adds a shark to the list of all sharks.
     * 
     * @param SharkBlock_S The SharkBlock to be added.
     */
    public void addShark(SharkBlock_S shark)
    {
        sharks.add(shark);
    }

    /**
     * Permanently changes the underlying board at the specified index.
     *
     * @param int The index.
     */
    public void changePermanentBoard(int index, String newString)
    {
        permanentBoard.set(index, newString);
    }

    /**
     * Returns a newly constructed block of the type specified by the permanent board at that index.
     *
     * @return Block A newly created block of the type speified at the given index location 
     * in the permanent board.
     */
    public Block getReplacementBlock(int index)
    {
        return constructBlock(permanentBoard.get(index), index);
    }

    /**
     * Returns the height of the board. 
     * 
     * @return int The height of the playing board.
     */
    public int getBoardHeight()
    {
        return boardHeight;
    }

    /**
     * Returns the width of the board.
     * 
     * @return int The width of the board.
     */
    public int getBoardWidth()
    {
        return boardWidth;
    }

    /**
     * Returns the Timer object which is responsible for animating all autonomous creatures.
     * 
     * @return Timer The game timer.
     */
    public Timer getTimer()
    {
        return timer;
    }

    /**
     * Returns the backpack.
     * 
     * @return Backpack The player's backpack.
     */
    public Backpack getBackpack()
    {
        return backpack;
    }

    /**
     * Returns the player.
     * 
     * @return PersonBlock The player.
     */
    public PersonBlock_P getPlayer()
    {
        return player;
    }

    /**
     * Sets the Block stored at a given index in the board. 
     *
     * @param int The index.
     * @param Block The new block which will be stored at the specified index.
     */
    public void setBlock(int index, Block newBlock)
    {
        board.set(index, newBlock);
    }

    /**
     * Returns the Block stored at a given index in the board. 
     *
     * @param int The index.
     * @return Block The block located at that that index.
     */
    public Block getBlock(int index)
    {
        return board.get(index);
    }

    /**
     * Makes a shark stop moving. It will still kill the player if it steps on it.
     * 
     * @param SharkBlock_S The shark that will be killed.
     */
    public void killShark(int position)
    {
        for(SharkBlock_S shark : sharks)
            if(shark.getPosition() == position)
            {
                timer.removeActionListener(shark);
            }
        //else do nothing.
    }

    /**
     * Pauses the game if the timer is running. Unpauses the game if the timer is paused.
     */
    public void pause()
    {
        if(!timer.isRunning())
        {
            timer.restart();
        }
        else
        {
            timer.stop();
        }
    }

    /**
     * Responds to attempted player movement. If the timer is running, this will result in movement
     * and/or action (or neither) in the specified direction. The exact response is dictated by the 
     * block(s) in that direction. Once the necessary action has been taken, the GUI is updated to 
     * reflect the change. 
     * 
     * @param String The direction of movement. Valid choices are "up", "down", "left", and "right". 
     */
    public void takeStep(String direction)
    {
        if(timer.isRunning())
        {
            player.act(direction);

            if(player.moveableCanMove(player, direction)) {
                player.move(direction);
            }

            if(won) 
            {
                Sound.playSound("tada");
                gui.resetPopUp("Winner!","Congratulations! You won!!! Would you like to continue playing?", "win");
            }
        }
    }

    /**
     * Resets all fields to their original values and retrieves data from original input 
     * file to paint the level from scratch.
     */
    public void reset()
    { 
        //get information about old locations
        int oldLocation = player.getPosition();
        //Remove old sharks
        for(int i = 0; i < sharks.size(); i++)
        {
            timer.removeActionListener(sharks.get(i));
        }
        sharks = new ArrayList<SharkBlock_S>();
        //Remove old items
        won = false;
        dead = false;
        backpack.emptyBackpack();
        //Repaint GUI
        constructBoard();
        player.setPosition(startPosition);
        player.setImage(Block.PICTURES.get("GIRL_ICON"));        
        gui.updateGrid();
        if(oldLocation != startPosition){
            board.set(oldLocation, constructBlock(permanentBoard.get(oldLocation), oldLocation));
            gui.updateBlock(oldLocation);
        }
        gui.updateBlock(startPosition);        
        timer.restart();
        gui.updatePauseButton();
    }

    /**
     * Responds to a players movement into a square implementing the Deadly interface. The GUI is 
     * updated to reflect the method of death, and then the player is given the option to play again.
     * 
     * @param Block The specific block which killed the player. 
     */
    public void death(Block deathBlock)
    {
        //Stop sharks
        timer.stop();
        ImageIcon deathImage =  deathBlock.getDeathImage();
        player.setImage(deathImage);
        player.updateGuiBlock();
        //Call death sound.
        deathBlock.playDeathSound();
        //String[] sounds = deathBlock.getDeathSounds();
        //for(int i = 0; i < sounds.length; i++) {
        //	sound.playSound(sounds[i]);
        //}
        //Prompt gui to ask if player wants to start again. 
        gui.resetPopUp("Game Over.","You have died.\nWould you like to play again.", "dead");
    }

    /**
     * Creates an informational string with details about the game.
     */
    public String about()
    {
        String message = "Solveig's Challenge is a very simplistic puzzle game, inspired by " + 
            "the game Chip's Challenge.\n\nThe point of the game is to navigate to the finish line by " +
            "resolving any challanges which stand in the way.";
        return message;
    }

    /**
     * Reads the appropriate input file, and converts that into the playing board.
     */
    private void constructBoard() 
    {
    	String directory = System.getProperty("user.dir");
    	//System.out.println(directory);
    	
        try{
        	//Make sure the program looks in the correct place for the levels. 
        	String filename;
        	if(!(directory.contains("SharkChallenge" + File.separator + "src"))) {
        	filename = "src" + File.separator + "levels" + File.separator + "level" + level + ".txt";
        	}
        	else {
        	filename = "levels" + File.separator + "level" + level + ".txt";
        	}
        	File filefile = new File(filename);
        	FileReader fileReader = new FileReader(filefile);
            BufferedReader reader = new BufferedReader(fileReader); 

            String line = reader.readLine();
            boardWidth = line.split(" ").length;
            board = new ArrayList<Block>();
            permanentBoard = new ArrayList<String>();

            int j = 0; //This will be the vertical position of the block.
            while(line != null) 
            {
                String[] splitLine = line.split(" ");
                for(int i = 0; i < boardWidth; i++)
                {
                    String letter = splitLine[i];
                    if(letter.equals("P")) {
                        startPosition = i + j*boardWidth;
                        if(player == null)
                        {
                            player = new PersonBlock_P(startPosition);
                        }
                        letter = "E";
                    }

                    board.add(constructBlock(letter, i + j*boardWidth));
                    if(letter.equals("B"))
                    {
                        letter = "E";
                    }
                    else if (letter.equals("S"))
                    {
                        letter = "W";
                    }
                    permanentBoard.add(letter);
                }
                line = reader.readLine();
                j++;
            }

            boardHeight = j;
            reader.close();
        }

        catch(FileNotFoundException e){
            gui.infoPopUp("Error", "Setup file not found.");
        }
        catch(IOException e) {
            gui.infoPopUp("Error", "Something went wrong with reading or closing file.");
        }
    }

    /**
     * Creates a block of the kind specified by the blockSymbol and adds it to the grid. 
     * 
     * @param String A one letter character which represents a specific kind of block. Choices are:
     *      X - A solid wall tile
     *      E - An emtpy tile
     *      W - A water tile (deadly)
     *      G - A "generator tile" 
     *      B - A moveable block.
     *      L - A lock tile.
     *      F - An exit tile.
     *      K - A key tile.
     *      M - A mud tile.
     *      S - A shark tile.
     *      P - An empty tile is constructed. (The "person" tile will be displayed on top of the empty tile.)
     */
    public Block constructBlock(String blockSymbol, int position)
    {
    	System.out.println(blockSymbol);
        Block newBlock = new EmptyBlock_E(position);
        
        if(blockSymbol.startsWith("G")) {
            String prototypeSymbol = blockSymbol.substring(1);
            newBlock = new GeneratorBlock_G(position, abvMap.get(prototypeSymbol));
        }
        else if(blockSymbol.equals("S")) {
            newBlock = new SharkBlock_S(position);
        }
        else {
        	newBlock = abvMap.get(blockSymbol);
        	newBlock = newBlock.clone(position);      	
        }
        //if(blockSymbol.equals("E") || blockSymbol.equals("P")) {
        //    newBlock = new EmptyBlock_E(position);
        //Note: need to accoutn fot the above.        
        	return newBlock;
    }
    
    private void initAbvMap() {
    	 File[] files = new File("src/blocks/").listFiles();
    	 abvMap = new HashMap<String,Block>();
    	 for(int i = 0; i < files.length; i++) {
    		 String fullName = "" + files[i].getName();
    		 fullName = "blocks." + fullName.substring(0, fullName.length() - 5);
    		 System.out.println("FullName: " + fullName);
    		 String[] parsedFileName = fullName.split("_");
    		 
    		 try {
    			 if(parsedFileName.length > 1) {
    				 Object thisBlock = Class.forName(fullName).newInstance();
    				 Block blockBlock = (Block) thisBlock;
    				 System.out.println(blockBlock.getClass());
    				 System.out.println("Created: " + fullName);		 
    				 abvMap.put(parsedFileName[1].substring(0, 1), blockBlock );
    				 System.out.println("Added: (" + parsedFileName[1].substring(0, 1) + "," + fullName + ")");
    			 }
    		 }
    		 catch(IllegalAccessException e1) {System.out.println("Access problem");}
    		 catch(InstantiationException e2) {System.out.println("Problem instanciating");}
    		 catch(ExceptionInInitializerError e3) {System.out.println("Problem initialzing");}
    		 catch(ClassNotFoundException e4) {System.out.println("ClassNotFound");}
    	 }
    	 System.out.println(abvMap.get("X"));
    }
}
