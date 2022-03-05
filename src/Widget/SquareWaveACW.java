package Widget;

import AudioComponent.SquareWave;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class SquareWaveACW extends AudioComponentWidget {

    public SquareWaveACW(AnchorPane mainCanvas) {
        super(mainCanvas);
    }

    @Override
    protected void setDefaultAudioComp_() {
        audioComp_ = new SquareWave(defaultFreq_);
    }

    @Override
    public void createSlider() {
        sliderValueMsg_ = new Text("Frequency: " + (int) defaultFreq_);
        slider_ = new Slider(100, 2000, defaultFreq_);
        slider_.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (output_ != null) { output_.getAudioComponent().removeInput(audioComp_); }
            ((SquareWave) audioComp_).updateProperties(newValue.intValue());
            sliderValueMsg_.setText("Frequency: " + newValue.intValue());
//            if (output_ != null) { output_.getAudioComponent().connectInput(audioComp_); }
        });
    }

    @Override
    public void createTitle() {
        componentName_ = "SQUARE WAVE AC";
        title_ = new Text(componentName_);
        title_.setOnMousePressed(e -> startWidgetMove (e));
        title_.setOnMouseDragged(e -> handleWidgetMove (e));
    }

    protected final double defaultFreq_ = 440;
}
