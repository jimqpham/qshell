package AudioComponent;

import AudioComponent.AudioComponent;

public class SquareWave implements AudioComponent {

    private double freq_;

    public SquareWave(double freq) {
        freq_ = freq;
    }

    public void updateProperties(double newFreqValue) {
        freq_ = newFreqValue;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            short sampleValue;
            if(  ( freq_ * i / AudioClip.sampleRate) % 1 > 0.5) { sampleValue = Short.MAX_VALUE;}
            else { sampleValue = Short.MIN_VALUE;}
            clip.setSample(i, sampleValue);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public boolean connectInput(AudioComponent input) {
        System.out.println( "SquareWaveAC does not accept input." );
        return false;
    }

    @Override
    public void removeInput(AudioComponent input) {
        assert true: "SquareWaveAC does not accept input.";
    }
}
