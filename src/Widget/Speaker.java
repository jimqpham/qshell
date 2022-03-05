package Widget;

import AudioComponent.Mixers;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Speaker extends AudioComponentWidget {

    public Speaker(AnchorPane mainCanvas) {
        super(mainCanvas);

        rightSide_.getChildren().remove(closeBtn_);
        baseLayout_.getChildren().remove(leftSide_);
        baseLayout_.setStyle("-fx-background-color: transparent");

        AnchorPane.setTopAnchor(this, 300.0);
        AnchorPane.setLeftAnchor(this, 1200.0);
    }

    protected void createOutputCircle() {
        outputCircle_ = new Circle(30);
        outputCircle_.setFill(Color.WHITE);
        outputCircle_.setOnMousePressed( e -> startLine(e));
        outputCircle_.setOnMouseDragged( e -> moveLine(e));
        outputCircle_.setOnMouseReleased(e -> stopLine(e));
    }

    public void setDefaultAudioComp_() {
        audioComp_ = new Mixers();
    }

    protected void createTitle() {
        componentName_ = "SPEAKER";
        title_ = new Text(componentName_);
    }
}
