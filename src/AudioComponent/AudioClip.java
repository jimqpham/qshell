package AudioComponent;

import Listener.AudioListener;
import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class AudioClip {

    public static final int duration = 2;
    public static final int sampleRate = 44100;
    public static final int TOTAL_SAMPLES = duration * sampleRate;
    private byte[]  byteArray = new byte[2 * TOTAL_SAMPLES];

    public short getSample(int index) {

        // to get rid of the sign extension: first move 8 bits to the left
        // then use unsigned bit shift to move 8 bits to the right
        short lowerByte = (short) (byteArray[2 * index] & 0x00FF); // post-shift lowerByte should look like 0x001A
        short upperByte = (short) (((short) byteArray[2 * index + 1]) << 8); // post-shift upperByte should look like 0x2B00
        short returnValue = (short) (upperByte | lowerByte);
        return returnValue;

    }

    public void setSample(int index, short value) {

        byte lowerByte = (byte) (value & 0x00ff);
        byte upperByte = (byte) ((value >>> 8) & 0x00ff);
        byteArray[ 2 * index ] = lowerByte;
        byteArray[ 2 * index + 1] = upperByte;

    }

    public String toHexString(int numBytes) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < numBytes; i++) {
            hexString.append(String.format("%02X ", byteArray[i]));
        }
        return String.valueOf(hexString);
    }

    public byte[] getData() {
        return Arrays.copyOf(byteArray, byteArray.length);
    }

    public static void play(AudioComponent ac) throws LineUnavailableException {
        Clip c = AudioSystem.getClip();
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
        AudioListener listener = new AudioListener(c);

        AudioClip clip = ac.getClip();

        c.open(format16, clip.getData(), 0, clip.getData().length);
        System.out.println("About to play...");
        c.start();

        c.addLineListener(listener);
    }

    public static void play(String note) throws LineUnavailableException {
        Map<String, Double> noteFreqMap = new HashMap<>();
        String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4"};
        double[] freqs = {261.63, 293.66, 329.63, 349.23, 392.00, 440.00, 493.88};
        for (int i = 0; i < notes.length; i++) { noteFreqMap.put(notes[i], freqs[i]); }
        double freq = noteFreqMap.get(note);
        play(new SineWave(freq));
    }
}

