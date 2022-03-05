package AudioComponent;

import AudioComponent.AudioComponent;

import java.util.Random;

public class WhiteNoise implements AudioComponent {

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            short sineValue = (short) (new Random().nextInt(Short.MAX_VALUE - Short.MIN_VALUE) + Short.MAX_VALUE);
            clip.setSample(i, sineValue);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public boolean connectInput(AudioComponent input) {
        System.out.println( "WhiteNoiseAC does not accept input." );
        return false;
    }

    @Override
    public void removeInput(AudioComponent input) {
        assert true: "WhiteNoiseAC does not accept input.";
    }
}
