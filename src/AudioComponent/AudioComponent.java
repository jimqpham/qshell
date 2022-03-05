package AudioComponent;

public interface AudioComponent {

    AudioClip getClip();

    boolean hasInput();

    boolean connectInput( AudioComponent input);

    void removeInput (AudioComponent input);

}
