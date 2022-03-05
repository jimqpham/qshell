package Widget;

import AudioComponent.Mixers;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MixersACW extends AudioComponentWidget {

    MixersACW(AnchorPane mainCanvas) {
        super(mainCanvas);
    }

    @Override
    protected void setDefaultAudioComp_() {
        audioComp_ = new Mixers();
    }

    @Override
    public void createTitle() {
        componentName_ = "MIXERS AC";
        title_ = new Text(componentName_);
        title_.setOnMousePressed(e -> startWidgetMove (e));
        title_.setOnMouseDragged(e -> handleWidgetMove (e));
    }
}
