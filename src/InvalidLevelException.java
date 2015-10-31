
/**
 * An InvalidLevelException is thrown if the user attempts to navigate to a level which does not exist.
 * 
 * @author Solveig Osborne 
 * @version CS162 Final 05/21/2015
 */
public class InvalidLevelException extends Exception
{
    private int invalidEntry;

    /**
     * Constructor for objects of class InvalidLevelException
     */
    public InvalidLevelException(int invalidEntry)
    {
        this.invalidEntry = invalidEntry;
    }

    /**
     * Returns the invalid level number. 
     */
    public int getLevel()
    {
    return invalidEntry;
    }
    
    /**
     * Returns a string stating the problem. 
     */
    public String toString()
    {
        return("There is no level #" + invalidEntry + ".");
    }
}
