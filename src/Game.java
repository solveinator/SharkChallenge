import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

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
    private ArrayList<String> levels; 
    private int level; 
    private ArrayList<String> permanentBoard;
    private ArrayList<Block> board;
    private int startPosition;
    private int boardHeight;
    private int boardWidth;
    private Gui gui;

    private Timer timer;
    private Sound sound;

    //Game progress
    private PersonBlock player;
    private String sharkThreat;
    private Backpack backpack;
    private ArrayList<SharkBlock> sharks;

    private boolean dead;
    private boolean won;

    /**
     * Constructor for objects of class Game.  
     */
    public Game()
    {
        loadLevels();
        level = 1;
        timer = new Timer(1000, null);
        Block.setGame(this);
        sharkThreat = "medium";
        sharks = new ArrayList<SharkBlock>();
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
        if(newLevelNum < 0 || newLevelNum > levels.size() - 1)
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
        return levels.size();
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
     * @param SharkBlock The SharkBlock to be added.
     */
    public void addShark(SharkBlock shark)
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
    public PersonBlock getPlayer()
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
     * @param SharkBlock The shark that will be killed.
     */
    public void killShark(int position)
    {
        for(SharkBlock shark : sharks)
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
        sharks = new ArrayList<SharkBlock>();
        //Remove old items
        won = false;
        dead = false;
        backpack.emptyBackpack();
        //Repaint GUI
        constructBoard();
        player.setPosition(startPosition);
        player.setImage(Block.GIRL_ICON);        
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
        Deadly deadlyBlock = (Deadly) deathBlock;
        ImageIcon deathImage =  deadlyBlock.getDeathImage();
        player.setImage(deathImage);
        player.updateGuiBlock();
        //Call death sound.
        deadlyBlock.playDeathSound();
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
        try{
            BufferedReader reader = new BufferedReader(new FileReader(levels.get(level))); 

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
                            player = new PersonBlock(startPosition);
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
     *      P - An emtpy tile is constructed. (The "person" tile will be displayed on top of the empty tile.)
     */
    public Block constructBlock(String blockSymbol, int position)
    {
        Block newBlock;
        if(blockSymbol.equals("E") || blockSymbol.equals("P")) {
            newBlock = new EmptyBlock(position);
        }
        else if(blockSymbol.equals("X")) {
            newBlock = new SolidBlock(position);
        }
        else if(blockSymbol.equals("W")) {
            newBlock = new WaterBlock(position);
        }
        else if(blockSymbol.startsWith("G")) {
            String prototypeSymbol = blockSymbol.substring(1);
            if (prototypeSymbol.length() != 1)
            {
                prototypeSymbol = "B";
            }
            newBlock = new GeneratorBlock(this, position, prototypeSymbol);
        }
        else if(blockSymbol.equals("B")) {
            newBlock = new PushableBlock(position);
        }
        else if(blockSymbol.equals("L")) {
            newBlock = new LockBlock(position);
        }
        else if(blockSymbol.equals("F")) {
            newBlock = new FinishBlock(position);
        }
        else if(blockSymbol.equals("K")) {
            newBlock = new KeyBlock(position);
        }
        else if(blockSymbol.equals("M")) {
            newBlock = new MudBlock(position);
        }
        else if(blockSymbol.equals("S")) {
            newBlock = new SharkBlock(position);
        }
        else {
            newBlock = new EmptyBlock(position);
        }
        return newBlock;
    }

    /**
     * Loads the files for each level into the levels ArrayList. Should be called before the game begins. 
     */
    private void loadLevels()
    {
        levels = new ArrayList<String>();
        levels.add("levels/level0.txt");
        levels.add("levels/level1.txt");
        levels.add("levels/level2.txt");
        levels.add("levels/level3.txt");
        levels.add("levels/level4.txt");
        levels.add("levels/level5.txt");
        levels.add("levels/level6.txt");
        levels.add("levels/level7.txt");
        levels.add("levels/last_level.txt");
    }
}
