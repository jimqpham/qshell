package AudioComponent;

import AudioComponent.AudioComponent;

public class SineWave implements AudioComponent {

    private double freq_;

    public SineWave(double freq) {
        freq_ = freq;
    }

    public void updateProperties(double newFreqValue) {
        freq_ = newFreqValue;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            short maxValue = Short.MAX_VALUE;
            short sampleValue = (short) (maxValue * Math.sin(2*Math.PI*freq_ * i / clip.sampleRate));
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
        System.out.println( "SineWaveAC does not accept input." );
        return false;
    }

    @Override
    public void removeInput(AudioComponent input) {
        assert true: "SoundWaveAC does not accept input.";
    }
}
