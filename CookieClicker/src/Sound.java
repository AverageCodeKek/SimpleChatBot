

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[5]; // URL Array, um die File Paths zu speichern
    FloatControl vol;

    public Sound(){
        soundURL[0] = getClass().getResource("audio/Bye Bye Sound Effect.wav");
        soundURL[1] = getClass().getResource("audio/MLG HORNS.wav");
        soundURL[2] = getClass().getResource("audio/Iron Cookie Clicker Theme.wav");
        soundURL[3] = getClass().getResource("audio/Duck Quack  Sound Effect.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream aIS = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(aIS);
            vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // Reguliering der Lautstärke
            vol.setValue(-30); // -30 Decibel, da sonst zu laut
        } 
        catch(Exception e){
            System.out.println("Something went wrong!"); 
        }
    }
    public void playFile(){
        clip.start();
    }
    public void loopFile(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopFile(){
        clip.stop();
    }
}
