package AudioComponent;

public class Reverb implements AudioComponent {

    AudioComponent input_ = null;
    double scale_;
    int delay_;

    public Reverb (double scale, int delay) {
        scale_ = scale;
        delay_ = delay;
    }

    public void updateProperties(double newScaleValue, int newDelayValue) {
        scale_ = newScaleValue;
        delay_ = newDelayValue;
    }

    @Override
    public AudioClip getClip() {
        AudioClip original = input_.getClip();

        AudioClip result = new AudioClip();

        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {

            // clamping
            int sampleValue = original.getSample((i + delay_) % AudioClip.TOTAL_SAMPLES);
            sampleValue += scale_*original.getSample(i);
            if (sampleValue > Short.MAX_VALUE) {
                sampleValue = Short.MAX_VALUE;
            } else if (sampleValue < Short.MIN_VALUE) {
                sampleValue = Short.MIN_VALUE;
            }

            result.setSample((i + delay_) % AudioClip.TOTAL_SAMPLES, (short) (sampleValue));
        }

        return result;
    }

    @Override
    public boolean hasInput() {
        return input_ != null;
    }

    @Override
    public boolean connectInput(AudioComponent input) {
        if (!hasInput()) {
            input_ = input;
            return true;
        }
        return false;
    }

    @Override
    public void removeInput(AudioComponent input) {
        if (input_ == input) {
            input_ = null;
        }
    }
}
