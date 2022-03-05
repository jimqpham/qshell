package AudioComponent;

public class Volume implements AudioComponent {

    AudioComponent input_ = null;
    double scale_;

    public Volume (double scale) {
        scale_ = scale;
    }

    public void updateProperties(double newScaleValue) {
        scale_ = newScaleValue;
    }

    @Override
    public AudioClip getClip() {
        AudioClip original = input_.getClip();

        AudioClip result = new AudioClip();

        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {

            // clamping
            int scaledValue = (int) (scale_ * (int) original.getSample(i));
            if (scaledValue > Short.MAX_VALUE) {
                scaledValue = Short.MAX_VALUE;
            } else if (scaledValue < Short.MIN_VALUE) {
                scaledValue = Short.MIN_VALUE;
            }

            result.setSample(i, (short) (scaledValue));
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
