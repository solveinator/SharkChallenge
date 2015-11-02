import java.io.File;
import java.io.*;
import java.util.HashMap;
import javax.swing.*;
import javax.sound.sampled.*;

/**
 * The Sound class imports all game sounds and plays them when promted. 
 * 
 * @author Solveig Oborne 
 * @version CS162 Final 05/22/2015
 */
public class Sound
{
    private static HashMap<String, File> SOUNDS;
    private File currentFile;
    private String prefix;

    /**
     * Constructor for objects of class Sound
     */
    public Sound()
    {
        SOUNDS = new HashMap<String, File>();
        loadSounds();
        currentFile = new File(prefix + "sounds/Water_Balloon-SoundBible.com-1358039219.wav");
                    
        
    }

    /**
     * Loads sounds into map.
     */
    public void loadSounds()
    {
    	String directory = System.getProperty("user.dir");
    	if(!(directory.contains("SharkChallenge" + File.separator + "src"))) {
    	prefix = "src" + File.separator + "sounds" + File.separator;
    	}
    	else {
    	prefix = "sounds" + File.separator;
    	}
        SOUNDS.put("splash",new File(prefix + "Water_Balloon-SoundBible.com-1358039219.wav"));
        SOUNDS.put("scrape", new File(prefix + "neck_snap-Vladimir-719669812.wav"));
        SOUNDS.put("tada", new File(prefix + "Ta_Da-SoundBible.com-1884170640.wav"));
        SOUNDS.put("click", new File(prefix + "switch-SoundBible.com-350629905.wav"));
        SOUNDS.put("ting", new File(prefix + "Shells_falls-Marcel-829263474.wav"));
        SOUNDS.put("lockClick", new File(prefix + "Metal_Reflect-SoundBible.com-977435034.wav"));
        SOUNDS.put("bite", new File(prefix + "Dog_Bite-SoundBible.com-107030898.wav"));
    }

    /**
     * Plays the sound references by the given string.
     * 
     * @param String The name of the sound.
     */
    public static void playSound(String name)
    {
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(SOUNDS.get(name));
            clip.open(ais);
            clip.loop(0);
        }
        catch(LineUnavailableException e)
        {
            System.out.println(e.toString());
        }
        catch(UnsupportedAudioFileException e)
        {
            System.out.println(e.toString());
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
    }

}
