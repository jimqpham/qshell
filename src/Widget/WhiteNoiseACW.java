package Widget;

import AudioComponent.WhiteNoise;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class WhiteNoiseACW extends AudioComponentWidget {
    public WhiteNoiseACW(AnchorPane mainCanvas) {
        super(mainCanvas);
    }

    @Override
    protected void setDefaultAudioComp_() {
        audioComp_ = new WhiteNoise();
    }

    @Override
    public void createTitle() {
        componentName_ = "WHITE NOISE AC";
        title_ = new Text(componentName_);
        title_.setOnMousePressed(e -> startWidgetMove (e));
        title_.setOnMouseDragged(e -> handleWidgetMove (e));
    }
}
