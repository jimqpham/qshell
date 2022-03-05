package Widget;

import AudioComponent.Volume;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class VolumeACW extends AudioComponentWidget {
    public VolumeACW(AnchorPane mainCanvas) {
        super(mainCanvas);
    }

    @Override
    protected void setDefaultAudioComp_() {
        audioComp_ = new Volume(1);
    }

    @Override
    public void createSlider() {

        sliderValueMsg_ = new Text("Volume: " + (int) defaultVol_ + "%");
        slider_ = new Slider(0, 150, defaultVol_);
        slider_.valueProperty().addListener((observable, oldValue, newValue) -> {
            ((Volume) audioComp_).updateProperties(newValue.doubleValue()/100.0);
            sliderValueMsg_.setText("Volume: " + newValue.intValue() +  "%");
        });

    }

    @Override
    public void createTitle() {
        componentName_ = "VOLUME AC";
        title_ = new Text(componentName_);
        title_.setOnMousePressed(e -> startWidgetMove (e));
        title_.setOnMouseDragged(e -> handleWidgetMove (e));
    }

    protected final double defaultVol_ = 100;
}
