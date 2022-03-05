package Widget;

import AudioComponent.Reverb;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ReverbACW extends AudioComponentWidget {

    public ReverbACW(AnchorPane mainCanvas) {
        super(mainCanvas);
    }

    @Override
    protected void setDefaultAudioComp_() {
        audioComp_ = new Reverb(0.2, 44100);
    }

    @Override
    public void createSlider() {

        sliderValueMsg_ = new Text("Reverb Volume: " + (int) defaultVol_ + "%");
        slider_ = new Slider(0, 100, defaultVol_);
        slider_.valueProperty().addListener((observable, oldValue, newValue) -> {
            ((Reverb) audioComp_).updateProperties(newValue.doubleValue()/100.0, 44100);
            sliderValueMsg_.setText("Reverb Volume: " + newValue.intValue() +  "%");
        });

    }

    @Override
    public void createTitle() {
        componentName_ = "REVERB AC";
        title_ = new Text(componentName_);
        title_.setOnMousePressed(e -> startWidgetMove (e));
        title_.setOnMouseDragged(e -> handleWidgetMove (e));
    }

    protected final double defaultVol_ = 20;
}
