package Widget;

import AudioComponent.AudioComponent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class AudioComponentWidget  extends Pane {

    AudioComponentWidget(AnchorPane mainCanvas) {

        // Pass the argument
        mainCanvas_ = mainCanvas;
        setDefaultAudioComp_(); // set the default audio component

        // Set up right side components: close button and output circle
        rightSide_ = new VBox();
        createCloseButton();    // stored in variable: closeBtn_
        createOutputCircle();   // stored in variable: outputCircle_
        rightSide_.getChildren().addAll(closeBtn_, outputCircle_);
        // Format the right side
        rightSide_.setAlignment(Pos.CENTER);
        rightSide_.setPadding(new Insets(20));
        rightSide_.setSpacing(15);

        // Set up left side components: slider and title
        leftSide_ = new VBox();
        createTitle();      // stored in variable: title_
        createSlider(); // slider stored in: slider_ and slider info stored in: sliderValueMsg_
        if (slider_ != null) {
            leftSide_.getChildren().addAll(title_, slider_, sliderValueMsg_);
        }
        else {
            leftSide_.getChildren().add(title_);
        }

        // Format the left side
        leftSide_.setAlignment(Pos.CENTER);
        leftSide_.setPadding(new Insets(20));
        leftSide_.setSpacing(10);

        // Add in all the pieces
        baseLayout_.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2");
        baseLayout_.getChildren().add(leftSide_);
        baseLayout_.getChildren().add(rightSide_);


        // Set us on the screen
        this.getChildren().add(baseLayout_);
        mainCanvas_.getChildren().add(this);
        AnchorPane.setTopAnchor(this, 50.0);
        AnchorPane.setLeftAnchor(this, 50.0);

    }

    /*
    =============================================
    ============= CREATE WIDGET PARTS ===========
    =============================================
    */

    protected void createTitle() {
        componentName_ = "NAME NOT SET";
        title_ = new Text(componentName_);
        title_.setOnMousePressed(e -> startWidgetMove (e));
        title_.setOnMouseDragged(e -> handleWidgetMove (e));
    }

    protected void createOutputCircle() {
        outputCircle_ = new Circle(20);
        outputCircle_.setFill(Color.NAVY);
        outputCircle_.setOnMousePressed( e -> startLine(e));
        outputCircle_.setOnMouseDragged( e -> moveLine(e));
        outputCircle_.setOnMouseReleased(e -> stopLine(e));
    }

    private void createCloseButton() {
        closeBtn_ = new Button ("x");
        closeBtn_.setOnAction(e -> close());
    }

    public void createSlider() {} // implement in child classes

    protected void setDefaultAudioComp_() {audioComp_ = null;}


    /*
    ===============================================
    ============= HANDLE CLOSE BUTTON =============
    ===============================================
    */

    private void close() {
        disconnectOutput();
        mainCanvas_.getChildren().remove(this);
        SynthApp.widgets_.remove(this);

        if (line_ != null) {
            mainCanvas_.getChildren().remove(line_);
        }
        for (AudioComponentWidget wid: SynthApp.widgets_) {
            if (wid.output_ == this) {
                wid.disconnectOutput();
            }
        }
    }

    /*
    =====================================================
    ============= CREATE AND HANDLE LINE MOVE ===========
    =====================================================
    */

    protected void startLine(MouseEvent e) {
        if (connectedToOutput_) {
            disconnectOutput();
        }

        line_ = new Line();
        line_.setStrokeWidth(3);
        line_.setStroke(Paint.valueOf("#CCCCCC"));

        // Set up so that the line starts at the center of the output circle
        double xCenterInScene = outputCircle_.localToScene(outputCircle_.getBoundsInLocal()).getCenterX();
        double yCenterInScene = outputCircle_.localToScene(outputCircle_.getBoundsInLocal()).getCenterY();
        line_.setStartX(xCenterInScene - getBoundsMinX());
        line_.setStartY(yCenterInScene - getBoundsMinY());
        line_.setEndX(e.getSceneX() - getBoundsMinX());
        line_.setEndY(e.getSceneY() - getBoundsMinY());

        mainCanvas_.getChildren().add(line_);
    }

    protected void moveLine(MouseEvent e) {
        if (line_ != null) {
            boolean stickToCenter = false;
            for (AudioComponentWidget wid: SynthApp.widgets_) {
                if (wid == this) {
                    continue;
                }
                Bounds targetBounds = wid.getCirclesSceneBound();
                double dist = Math.sqrt(Math.pow(targetBounds.getCenterX() - e.getSceneX(), 2.0) +
                        Math.pow(targetBounds.getCenterY() - e.getSceneY(), 2.0));
                // Add the magnetic effect when the end of the line automatically
                // sticks to the center of the circle
                if (dist <= 30) {
                    line_.setEndX(targetBounds.getCenterX() - getBoundsMinX());
                    line_.setEndY(targetBounds.getCenterY() - getBoundsMinY());
                    stickToCenter = true;
                }
            }
            if (!stickToCenter) {
                line_.setEndX(e.getSceneX() - getBoundsMinX());
                line_.setEndY(e.getSceneY() - getBoundsMinY());
            }
        }
    }

    protected void stopLine(MouseEvent e) {
        if (line_ != null) {
            for (AudioComponentWidget wid: SynthApp.widgets_) {
                if (wid == this) {
                    continue;
                }
                Bounds targetBounds = wid.getCirclesSceneBound();
                // If end of line == center of circle when mouse is released
                // connect the widgets
                if (line_.getEndX() == targetBounds.getCenterX() - getBoundsMinX() &&
                    line_.getEndY() == targetBounds.getCenterY() - getBoundsMinY()) {
                    connectToOutput(wid);
                }
            }
            if (!connectedToOutput_) {
                mainCanvas_.getChildren().remove(line_);
            }
        }
    }

    /*
    ==============================================
    ============= HANDLE WIDGET DRAG =============
    ==============================================
    */

    private double getBoundsMinX() {
        return SynthApp.mainCanvas_.getBoundsInParent().getMinX();
    }

    private double getBoundsMinY() {
        return SynthApp.mainCanvas_.getBoundsInParent().getMinY();
    }

    protected void startWidgetMove(MouseEvent e) {

        xWidgetOffset_ = (e.getSceneX() - getBoundsMinX()) - AnchorPane.getLeftAnchor(this);
        yWidgetOffset_ = (e.getSceneY() - getBoundsMinY()) - AnchorPane.getTopAnchor(this);

        Bounds circleBounds = outputCircle_.localToScene(outputCircle_.getBoundsInLocal());
        double x = circleBounds.getCenterX();
        double y = circleBounds.getCenterY();
        // Calculate the offset values
        // which represent the distance between the mouse position and the top left widget pixel
        xCircleOffset_ = e.getSceneX() - x;
        yCircleOffset_ = e.getSceneY() - y;

    }

    protected void handleWidgetMove(MouseEvent e) {
        AnchorPane.setLeftAnchor(this, e.getSceneX() - getBoundsMinX() - xWidgetOffset_);
        AnchorPane.setTopAnchor(this, e.getSceneY() - getBoundsMinY() - yWidgetOffset_);
        if (line_ != null) {
            line_.setStartX(e.getSceneX() - getBoundsMinX() - xCircleOffset_);
            line_.setStartY(e.getSceneY() - getBoundsMinY() - yCircleOffset_);
        }
        for (AudioComponentWidget wid: SynthApp.widgets_) {
            if (wid.output_ == this) {
                wid.line_.setEndX(e.getSceneX() - getBoundsMinX() - xCircleOffset_);
                wid.line_.setEndY(e.getSceneY() - getBoundsMinY() - yCircleOffset_);
            }
        }
    }

    /*
    ===========================================
    ========== APP HELPER FUNCTIONS ===========
    ===========================================
    */

    public AudioComponent getAudioComponent() {
        return audioComp_;
    }

    public Bounds getCirclesSceneBound() {
        return outputCircle_.localToScene(outputCircle_.getBoundsInLocal());
    }

    public void connectToOutput(AudioComponentWidget output) {
        output_ = output;
        connectedToOutput_ = output_.audioComp_.connectInput(this.getAudioComponent());
        if (connectedToOutput_) {
            System.out.println("Successful Connection: " + this.componentName_ + " > " + output.componentName_);
        }
    }

    public void disconnectOutput () {
        if (output_ != null) {
            if (line_ != null) {
                mainCanvas_.getChildren().remove(line_);
                line_ = null;
            }
            output_.audioComp_.removeInput(this.getAudioComponent());
            connectedToOutput_ = false;
        }
    }

    /*
    =========================================
    ========== MEMBER VARIABLES =============
    =========================================
    */

    // LAYOUT
    protected AnchorPane mainCanvas_;
    protected HBox baseLayout_ = new HBox();
    // WIDGET PARTS
    protected Line line_ = null;
    protected Button closeBtn_;
    protected Circle outputCircle_;
    protected Text title_, sliderValueMsg_;
    protected Slider slider_;
    protected VBox leftSide_, rightSide_;
    // AC PARTS
    protected String componentName_;
    protected AudioComponent audioComp_;
    protected boolean connectedToOutput_ = false;
    protected AudioComponentWidget output_;
    // OFFSET COORDINATES FOR DRAG-AND-DROP ACTIONS
    protected double xWidgetOffset_, yWidgetOffset_, xCircleOffset_, yCircleOffset_;
}
