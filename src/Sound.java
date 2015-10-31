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
    private static HashMap<String, File> sounds;
    private File currentFile;

    /**
     * Constructor for objects of class Sound
     */
    public Sound()
    {
        sounds = new HashMap<String, File>();
        loadSounds();
        currentFile = new File("sounds/Water_Balloon-SoundBible.com-1358039219.wav");
                    
        
    }

    /**
     * Loads sounds into map.
     */
    public void loadSounds()
    {
        sounds.put("splash",new File("sounds/Water_Balloon-SoundBible.com-1358039219.wav"));
        sounds.put("scrape", new File("sounds/neck_snap-Vladimir-719669812.wav"));
        sounds.put("tada", new File("sounds/Ta_Da-SoundBible.com-1884170640.wav"));
        sounds.put("click", new File("sounds/switch-SoundBible.com-350629905.wav"));
        sounds.put("ting", new File("sounds/Shells_falls-Marcel-829263474.wav"));
        sounds.put("lockClick", new File("sounds/Metal_Reflect-SoundBible.com-977435034.wav"));
        sounds.put("bite", new File("sounds/Dog_Bite-SoundBible.com-107030898.wav"));
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
            AudioInputStream ais = AudioSystem.getAudioInputStream(sounds.get(name));
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
