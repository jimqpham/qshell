package Listener;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioListener implements LineListener {

    public AudioListener(Clip c) {
        clip_ = c;
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            System.out.println("Close sound.");
            clip_.close();
        }
    }

    private Clip clip_;

}
