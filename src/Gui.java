import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.Hashtable;

/**
 * The GUI class creates the visible interface of the game and has the necessary methods required to keep the
 * interface updated in response to changes. It implements the KeyListener interface in order
 * to parse key input from the user. In also employs several dialog boxes combined with anonymous 
 * ActionListeners to respond to mouse input.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final Project 06/03/2015
 */
public class Gui implements KeyListener 
{
    private Game game;
    private PersonBlock player;
    private JFrame frame;
    private Container contentPane;
    private JMenuBar menuBar;
    private JPanel right;
    private JTextArea goal;
    private JPanel levelWrapper;
    private JLabel level; //Note that this a JLabel, different from the int level field in the game class.
    private JButton pause;
    private JPanel grid;
    private JPanel threatWrapper;
    private JLabel threat;
    private JPanel buttons;
    private JPanel backpack;
    private JPanel backpackItems;

    /**
     * Constructor for objects of class Gui
     */
    public Gui(Game game, PersonBlock player)
    {
        this.game = game;
        this.player = player;
        makeFrame();
    }

    /**
     * Creates the display. 
     * 
     * In general, the formating code is in this method's body, while code which constructs the actual
     * content is contained in helper methods. 
     * 
     */
    private void makeFrame()
    {
        //Overall frame
        frame = new JFrame("Solveig's Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this); //Allows the keyListener to focus on the frame itself. 
        frame.setFocusable(true);   //Make the frame get the focus whenever frame is activated.

        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());  

        //Menu
        createMenu();

        //Left - None

        //Center - Playing Field
        grid = new JPanel();
        createGrid();
        grid.setLayout(new GridLayout(game.getBoardWidth(), game.getBoardHeight()));
        grid.setSize(720, 720);
        contentPane.add(grid, BorderLayout.CENTER);

        //Right 
        right = new JPanel();
        right.setSize(220, 720);
        right.setBorder(BorderFactory.createLineBorder(Color.GRAY, 10));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(Color.GRAY);
        contentPane.add(right, BorderLayout.EAST);      

        //Right - Top
        levelWrapper = new JPanel();
        levelWrapper.setLayout(new FlowLayout());
        levelWrapper.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
        levelWrapper.setPreferredSize(new Dimension(180,20));
        right.add(levelWrapper);
        level = new JLabel("Level " + game.getLevel());
        level.setBorder(BorderFactory.createLineBorder(new Color(238, 238, 238), 5));
        level.setFont(new Font(null, Font.BOLD, 14));
        levelWrapper.add(level);
        
        goal = new JTextArea();
        goal.setBackground(Color.LIGHT_GRAY);
        goal.setLineWrap(true);
        goal.setWrapStyleWord(true);
        goal.setEditable(false);
        goal.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
        createText();
        goal.setMinimumSize(new Dimension(160,280));
        goal.setMaximumSize(new Dimension(160,280));
        right.add(goal);
        
        threatWrapper = new JPanel();
        threatWrapper.setLayout(new FlowLayout());
        threatWrapper.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
        threatWrapper.setPreferredSize(new Dimension(180,25));
        //threatWrapper.setMinimumSize(new Dimension(180,60));
        right.add(threatWrapper);
        JLabel threatLabel = new JLabel("Shark Threat Level:  ");
        threat = new JLabel (""+ game.getSharkThreat().toUpperCase());
        threatWrapper.add(threatLabel);
        threatWrapper.add(threat);
        
        //right.add(Box.createVerticalGlue());
        
        //Right - Middle        
        JPanel pauseWrapper = new JPanel();
        pauseWrapper.setLayout(new FlowLayout());
        //pauseWrapper.setSize(180,40);
        pauseWrapper.setPreferredSize(new Dimension(180,40));
        pauseWrapper.setBackground(Color.GRAY);
        pauseWrapper.setLayout(new FlowLayout());
        pauseWrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
        right.add(pauseWrapper);
        
        pause = new JButton("Pause");
        //pause.setAlignmentY(Component.CENTER_ALIGNMENT);
        pauseWrapper.add(pause);
        pause.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    game.pause();
                    updatePauseButton();
                }
            });
        
        //right.add(Box.createVerticalGlue());
            
        //Right - Bottom
        backpack = new JPanel();
        backpack.setLayout(new BorderLayout());
        backpack.setLayout(new BoxLayout(backpack, BoxLayout.Y_AXIS));
        backpack.setBorder(BorderFactory.createLineBorder(new Color(3, 40, 0), 30));
        backpack.setMaximumSize(new Dimension(180,240));

        backpackItems = new JPanel();
        backpackItems.setLayout(new GridLayout(3,2));
        for(int i = 0; i < 6; i++)
        {
            JLabel spot = new JLabel();
            spot.setIcon(Block.PICTURES.get("EMPTY_ICON"));
            backpackItems.add(spot);
        }
        backpack.add(backpackItems);
        right.add(backpack);
       
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the menu for the frame.
     */
    private void createMenu()
    {
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //File
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem aboutMenu = new JMenuItem("About");
        aboutMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    infoPopUp("About Solveig's Challenge", game.about());
                } 
            } );
        fileMenu.add(aboutMenu);

        JMenuItem quitMenu = new JMenuItem("Quit");
        quitMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);} });
        fileMenu.add(quitMenu);

        //Levels
        JMenu levelMenu = new JMenu("Level");
        menuBar.add(levelMenu);

        JMenuItem resetMenu = new JMenuItem("Reset");
        resetMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    game.reset();
                }
            });
        levelMenu.add(resetMenu);

        JMenuItem nextMenu = new JMenuItem("Next");
        nextMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                        game.setLevel(game.getLevel()+1);
                    }
                    catch(InvalidLevelException ex){
                        infoPopUp("Error", ex.toString()); // + "\n\nPlease choose from one of the available levels between " +
                        // "level 1 and level " + levels.size() + "."); 
                    }
                }
            });
        levelMenu.add(nextMenu);

        JMenuItem previousMenu = new JMenuItem("Previous");
        previousMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                        game.setLevel(game.getLevel()-1);
                    }
                    catch(InvalidLevelException ex){
                        infoPopUp("Error", ex.toString()); 
                    }
                }
            });
        levelMenu.add(previousMenu);

        JMenuItem skipMenu = new JMenuItem("Skip to Level...");
        skipMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    skip();
                }

            });
        levelMenu.add(skipMenu);

        JMenu systemMenu = new JMenu("System");
        menuBar.add(systemMenu);

        JMenuItem settingsMenu = new JMenuItem("Settings");
        settingsMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    updateSettings();
                }
            });
        systemMenu.add(settingsMenu);
    }

    /**
     * Creates the welcome greating and information text panel. 
     */
    private void createText()
    {
        goal.append("\nWelcome to Solveig's challenge!\n\nYour goal is to make it to the finish flags."+
            "\n\nPlease use the arrow keys to move about the board.\n");
    }

    /**
     * Creates the grid for the game board. 
     */
    private void createGrid(){
        //construct regular board
        for(int i = 0; i < (game.getBoardWidth() * game.getBoardHeight()); i++)
        {
            Block tile = game.getBlock(i);
            JLabel label = new JLabel();
            label.setMaximumSize(new Dimension(56, 56));
            label.setIcon(tile.getImage());
            grid.add(label);
        }

        //place player on the board
        int playerPosition = player.getPosition();
        JLabel newSquare = (JLabel) grid.getComponent(playerPosition);
        newSquare.setIcon(player.getImage());
        frame.requestFocusInWindow();
    }

    /**
     * Creates the grid for the game board. 
     */
    public void updateGrid(){     
        //construct regular board
        for(int i = 0; i < (game.getBoardWidth() * game.getBoardHeight()); i++)
        {
            Block tile = game.getBlock(i);
            JLabel label = (JLabel) grid.getComponent(i);
            label.setIcon(tile.getImage());
        }

        //place player on the board
        frame.requestFocusInWindow();
    }

    /**
     * Updates the image for the panel at the given location.
     * 
     * This allows the gui to update just the blocks which have changed instead of repainting the 
     * entire board. 
     * 
     * @param int The location of the block which needs updated. 
     */
    public void updateBlock(int location)
    {
        JLabel oldSquare = (JLabel) grid.getComponent(location);
        ImageIcon image;
        if(location == player.getPosition()){
            image = player.getImage();
        }
        else
        {
            Block block = game.getBlock(location);
            image = block.getImage();
        }
        oldSquare.setIcon(image);
        frame.requestFocusInWindow();
    }

    /**
     * Takes keyboard input from the arrow pad, translates that into String directions, then calls the game's
     * takeStep method to process the input.  
     * 
     * @param KeyEvent The only key input that causes a response are the up, down, left, and right arrow keys. 
     */
    public void keyPressed(KeyEvent e)
    {
        int input = e.getKeyCode();
        String command = "";
        if(input == 37){
            command = "left";
        }    
        else if(input == 38){
            command = "up";
        }
        else if(input == 39){
            command = "right";
        }
        else if(input == 40){
            command = "down";
        }   
        else {
            command = ""; //don't do anything.
        }
        game.takeStep(command);
    }

    /**
     * Method not used -- only included because required by KeyListener interface. 
     */
    public void keyReleased(KeyEvent e)
    {
        //not used
    }

    /**
     * Method not used -- only included because required by KeyListener interface. 
     */
    public void keyTyped(KeyEvent e)
    {
        //not used
    }

    /**
     * Updates the gui pause button to reflect the paused status. 
     */
    public void updatePauseButton()
    {
        if(game.getTimer().isRunning())
        {
            pause.setText("Pause");
            frame.requestFocusInWindow();
        }
        else
        {
            pause.setText("Continue");
            frame.requestFocusInWindow();
        }
    }
    
    /**
     * Updates the shark threat information. 
     */
    public void updateSharkThreat()
    {
        String sharkThreat = game.getSharkThreat();
        threat.setText("" + sharkThreat.toUpperCase());
        if(sharkThreat.equals("low"))
        {
            levelWrapper.setBorder(BorderFactory.createLineBorder(new Color(3, 80, 0), 10));
            threatWrapper.setBorder(BorderFactory.createLineBorder(new Color(3, 80, 0), 10));
        }
        else if (sharkThreat.equals("high"))
        {
            levelWrapper.setBorder(BorderFactory.createLineBorder(new Color(140, 0, 3), 10));
            threatWrapper.setBorder(BorderFactory.createLineBorder(new Color(140, 0, 3), 10));
        }
        else 
        {
            levelWrapper.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
            threatWrapper.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10));
        }
    }

    /**
     * Creates dialog to allow the user to change game settings including shark threat 
     * level and speed. 
     */
    private void updateSettings()    
    {                
        game.getTimer().stop();
        JDialog dialog = new JDialog(frame, "Settings", true);
        //dialog.setResizable(false);
        dialog.setLayout(new GridLayout(3,1));
        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(null);

        JPanel radioButtonsWrapper = new JPanel();
        radioButtonsWrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 20));
        radioButtonsWrapper.setLayout(new BoxLayout(radioButtonsWrapper, BoxLayout.X_AXIS));
        JLabel threatTitle = new JLabel("  Shark Threat  ");
        radioButtonsWrapper.add(threatTitle);

        JRadioButton low = new JRadioButton("Low");
        low.addActionListener(new ThreatChanger());
        JRadioButton medium = new JRadioButton("Medium");
        medium.addActionListener(new ThreatChanger());
        JRadioButton high = new JRadioButton("High");
        high.addActionListener(new ThreatChanger());
        radioButtonsWrapper.add(low);
        radioButtonsWrapper.add(medium);
        radioButtonsWrapper.add(high);

        ButtonGroup group = new ButtonGroup();       
        group.add(low);
        group.add(medium);
        group.add(high);
        String threat = game.getSharkThreat();
        if(threat.equals("low"))
        {
            low.setSelected(true);
        }
        else if(threat.equals("high"))
        {    
            high.setSelected(true);
        }
        else{
            medium.setSelected(true);
        }

        JPanel sliderWrapper = new JPanel();
        sliderWrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 20));
        sliderWrapper.setLayout(new BoxLayout(sliderWrapper, BoxLayout.X_AXIS));
        JLabel speedTitle = new JLabel("  Speed  ");
        sliderWrapper.add(speedTitle);

        JSlider speed = new JSlider(JSlider.HORIZONTAL, 500, 2500, 3000 - game.getTimer().getDelay());
        /* Note: it is more intuitive to have the game speed increase the further to the right the 
         * slider is. However, smaller delay values produce mroe frequent movement, so the intuitive 
         * option goes against the slider's range capapilities. Therefore, this slider was set up so 
         * that the value returned slider will produce and apropriate speed after being subtracted 
         * from 3000/
         */
        speed.setMajorTickSpacing(500);
        speed.setPaintTicks(true);
        speed.setSnapToTicks(true);
        sliderWrapper.add(speed);
        
        //I was attempting to put lables on my slider, but it wasn't working out...
        //Hashtable labelTable = new Hashtable();
        //labelTable.put( new Integer( 2500 ), new JLabel("Slow") );
        //labelTable.put( new Integer( 400 ), new JLabel("Fast") );        
        //speed.setLabelTable( labelTable );

        JButton okButton = new JButton("OK");
        okButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {          
                    game.getTimer().setDelay(3000 - speed.getValue());
                    dialog.dispose();
                    game.getTimer().restart();
                    updateSharkThreat();
                }
            });
            
        dialog.add(radioButtonsWrapper);
        dialog.add(sliderWrapper);
        dialog.add(okButton);
        dialog.pack();
        dialog.setVisible(true);   

    }

    /**
     * Creates dialog with a spinner allowing the user to select a different level
     * number. 
     */
    public void skip()    
    {                
        JDialog dialog = new JDialog(frame, "Select Level", true);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(200,120);
        dialog.setLocationRelativeTo(null);
        //dialog.setVisible(true);
        //         JFrame popUpFrame = new JFrame("Select Level");
        JPanel spinnerWrapper = new JPanel();
        spinnerWrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 20));
        spinnerWrapper.setLayout(new BoxLayout(spinnerWrapper, BoxLayout.X_AXIS));
        JLabel words = new JLabel("  Level  ");

        SpinnerNumberModel numberModel = new SpinnerNumberModel(game.getLevel(), 0, game.getNumberOfLevels() - 1,1);
        JSpinner spinner = new JSpinner(numberModel);
        spinnerWrapper.add(words);
        spinnerWrapper.add(spinner);

        JButton okButton = new JButton("OK");
        okButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {          
                    int newLevel = numberModel.getNumber().intValue();
                    dialog.dispose();
                    try{
                        game.setLevel(newLevel);
                    }
                    catch(InvalidLevelException ex)
                    {
                        infoPopUp("Error", e.toString()); 
                    }
                }
            });

        dialog.add(spinnerWrapper, BorderLayout.NORTH);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);

    }

    /**
     * Changes the pictures shown in the backpack in response to an item being added or removed. 
     * 
     * @param index The index of the backpack location to be updated. (This is set up to correspond
     * directly with the item at that index in the backpack list held in the Game class.)
     * @param block The block that corresponds to the item being held. 
     */
    public void updateBackpackImage(int index, Block block)
    {
        JLabel item = (JLabel) backpackItems.getComponent(index);
        item.setIcon(block.getImage());
    }

    /**
     * Changes the level display panel to reflect a new level number.
     * 
     * @param int The new level number.
     */
    public void updateLevel(int newLevel)
    {
        level.setText("Level " + game.getLevel());
        frame.requestFocusInWindow();
    }

    /**
     * Displays a message for the viewer via a pop-up message pane. 
     * 
     * @param String The title which will be displayed at the top of the pane.
     * @param String The message which will appear in the body of the window. 
     */
    public void infoPopUp(String title, String message)
    { 
        JOptionPane.showOptionDialog(frame, message, title, 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 2);
    }

    /**
     * Displays message for the viewer and asks if they would like to reset.
     * 
     * @param String The title which will be displayed at the top of the pane.
     * @param String The message which will appear in the body of the window.
     * @param String The reason a reset is required. Valid options are "dead" or "win".
     */
    public void resetPopUp(String title, String message, String reasonForReset)
    { 
        int choice = JOptionPane.showOptionDialog(frame, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, 2);
        if(choice == 0)
        {
            if(reasonForReset == "win")
            {
                try{
                    game.setLevel(game.getLevel()+1);
                }
                catch(InvalidLevelException e)
                {
                    infoPopUp("Error", e.toString());
                }
            }
            game.reset();

        }
        else
        {
            game.getTimer().stop();
            System.exit(0);
        }
    }

    /**
     * This class exists just to handle input from the radio buttons. 
     */
    public class ThreatChanger implements ActionListener
    {
        /**
         * Constructs a new ThreatChanger.
         */
        public ThreatChanger()
        {
            //No construction needed.
        }

        /**
         * When a particular radio button is selected, the threat level is changed to reflect that selection.
         * 
         * @param ActionEvent The ActionEvent produced by a radio button being selected. 
         */
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();
            JRadioButton but = (JRadioButton) source;
            game.setSharkThreat(but.getText().toLowerCase());
        }
    }
}