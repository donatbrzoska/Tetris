/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remains;

import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 *
 * @author donatdeva
 */
public class MusicThread extends Thread{
    
    private boolean execute;
    
    MusicThread(){
        super();
        execute = true;
    }
    
    void stopExecuting(){
        execute = false;
    }
    
    @Override
    public void run(){
        int s = INDEFINITE;
        AudioClip audio = new AudioClip(getClass().getResource("sound/original.mp3").toExternalForm());
        audio.setVolume(0.5f);
        audio.setCycleCount(s);
        audio.play();
        while(execute){System.out.println();}
        audio.stop();
    }
    
    void startAudio(){
        int s = INDEFINITE;
        AudioClip audio = new AudioClip(getClass().getResource("sound/original.mp3").toExternalForm());
        audio.setVolume(0.5f);
        audio.setCycleCount(s);
        audio.play();
    }
}
