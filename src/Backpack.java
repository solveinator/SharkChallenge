import java.util.ArrayList;

/**
 * The Backpack class contains all items and methods relating to the player's backpack. All items in the backpack
 * are Block items.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 5/29/2015 
 */
public class Backpack
{
    private Gui gui;
    private Block[] itemList;

    /**
     * Constructor for objects of class BackPack
     */
    public Backpack(Gui gui)
    {
        this.gui = gui;
        //create empty backpack
        itemList = new Block[6];
        for(int i = 0; i < itemList.length ; i++)
        {
        itemList[i] = new EmptyBlock(-1);
        }
    }

    /**
     * Checks whether the backpack contains an item without making any changes to the backpack. 
     * 
     * @param Block The type of item you are looking for.
     * @return boolean Whether or not thay type of item was found.
     */
    public boolean hasItem(Block block)
    {
    for(int i = 0; i < itemList.length ; i++)
        {
            if(itemList[i].getClass().equals(block.getClass()))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Adds a item to the packback (if there is room) and updates the gui to reflect the new item.
     * 
     * @param Block The type of block that corresponds to that item.
     * @return boolean Whether the item was successfully added to the backpack. If there is room, the return 
     * should be true. If the backpack is already full, the items will not be added and the return will be false.
     */
    public boolean addBackpackItem(Block block)
    {
        for(int i = 0; i < itemList.length; i++)
        {
            if(itemList[i] instanceof EmptyBlock)
            {
                itemList[i] = block;
                gui.updateBackpackImage(i, block);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an item from the backpack list.
     * 
     * @param Block The type of block that corresponds to that item.
     */
    public boolean removeBackpackItem(Block block)
    {
        for(int i = 0; i < itemList.length; i++)
        {   
            if(itemList[i].getClass().equals(block.getClass()))
            {
                EmptyBlock replacement = new EmptyBlock(-1);
                itemList[i] = replacement;
                gui.updateBackpackImage(i, replacement);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Removes all items in the backpack so the backpack is empty.
     */
    public void emptyBackpack()
    {
        for(int i = 0; i < itemList.length; i++)
        {
            Block replacement = new EmptyBlock(-1);
            itemList[i] = replacement;
            gui.updateBackpackImage(i, replacement);
        }
    }
}
