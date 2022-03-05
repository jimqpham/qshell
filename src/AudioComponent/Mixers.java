package AudioComponent;

import java.util.ArrayList;

public class Mixers implements AudioComponent {

    ArrayList<AudioComponent> inputs = new ArrayList<AudioComponent>();

    @Override
    public AudioClip getClip() {
        int numInputs = inputs.size();
        double scale = 1 / (float) numInputs;
        ArrayList<AudioClip> scaledClips = new ArrayList<AudioClip>();

        // scaling before adding samples together
        for (int i = 0; i < numInputs; i++) {
            AudioComponent input = inputs.get(i);
            Volume volume = new Volume(scale);
            volume.connectInput( input);
            AudioClip scaledClip = volume.getClip();
            scaledClips.add(scaledClip);
        }

        // adding sample values together
        AudioClip result = new AudioClip();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            int sampleValue = 0;
            for (int j = 0; j < numInputs; j++) {
                sampleValue += scaledClips.get(j).getSample(i);
            }
            if (sampleValue > Short.MAX_VALUE) {sampleValue = Short.MAX_VALUE;}
            else if (sampleValue < Short.MIN_VALUE) {sampleValue = Short.MIN_VALUE;}

            result.setSample(i, (short) sampleValue);
        }

        return result;
    }

    @Override
    public boolean hasInput() {
        return inputs.size() != 0;
    }

    @Override
    public boolean connectInput(AudioComponent input) {
        inputs.add(input);
        return true;
    }

    @Override
    public void removeInput(AudioComponent input) {
        if (inputs.contains(input)) {
            inputs.remove(input);
        }
    }
}
