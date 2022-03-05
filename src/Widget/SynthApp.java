package Widget;

import AudioComponent.AudioClip;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;

public class SynthApp extends Application {

    @Override
        public void start(Stage primaryStage) {

            primaryStage.setTitle("My Synthesizer");

            // Main UI in the center
            mainCanvas_ = new AnchorPane();
            mainCanvas_.setStyle("-fx-background-color: hotpink");
            speaker_ = new Speaker(mainCanvas_);
            widgets_.add(speaker_);

            // HBox at the top
            topBar_ = new HBox();
            topBar_.setAlignment(Pos.BASELINE_CENTER);
            topBar_.setPadding(new Insets(20));
            topBar_.setSpacing(20);
            String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4"};
            ArrayList<Button> buttons = new ArrayList<>();
            for (String note : notes) {
                Button btn = new Button();
                btn.setPadding(new Insets(10));
                btn.setText(note);
                btn.setOnAction(e -> {
                    try {
                        handleButtonPress(btn);
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                });
                buttons.add(btn);
            }
            for (Button btn: buttons) {
                topBar_.getChildren().add(btn);
            }

            
            // VBox to the right
            rightPane_ = new VBox();
            rightPane_.setAlignment(Pos.CENTER);
            Text paneTitle = new Text();
            paneTitle.setText("Audio Component");
            String[] buttonNames = {"SoundWaveAC", "SquareWaveAC", "WhiteNoiseAC",
                                    "VolumeAC", "ReverbAC", "MixerAC"};
            for (String btnName: buttonNames) {
                Button newACWGenBtn = new Button();
                newACWGenBtn.setText(btnName);
                newACWGenBtn.setPadding(new Insets(10));
                newACWGenBtn.setOnMousePressed(e -> genWidget(mainCanvas_, btnName));
                acwGeneratingBtns.add(newACWGenBtn);
            }
            rightPane_.getChildren().add(paneTitle);
            for (Button acwGenBtn: acwGeneratingBtns) {rightPane_.getChildren().add(acwGenBtn);}
            rightPane_.setPadding(new Insets(10));
            rightPane_.setSpacing(30);

            // Play Button at the bottom
            bottomBar_ = new HBox();
            bottomBar_.setAlignment(Pos.CENTER);
            Button playBtn = new Button();
            playBtn.setText("Play");
            playBtn.setPadding(new Insets(20));
            playBtn.setOnMouseClicked(e -> {
                try {
                    playSound();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            });
            bottomBar_.getChildren().add(playBtn);
            bottomBar_.setPadding(new Insets(30));


            // Add everything together
            baseLayout_ = new BorderPane();
            baseLayout_.setTop(topBar_);
            baseLayout_.setBottom(bottomBar_);
            baseLayout_.setRight(rightPane_);
            baseLayout_.setCenter(mainCanvas_);
            primaryStage.setScene(new Scene(baseLayout_, 1600, 1200));
            primaryStage.show();

        }

    private void playSound() throws LineUnavailableException {

        System.out.println("Play the sound.");

        AudioClip.play(speaker_.getAudioComponent());
    }

    private AudioComponentWidget genWidget(AnchorPane parent, String componentName) {
        AudioComponentWidget widget = null;
        switch (componentName) {
            case "SoundWaveAC":
                widget = new SoundWaveACW(parent);
                break;
            case "SquareWaveAC":
                widget = new SquareWaveACW(parent);
                break;
            case "VolumeAC":
                widget = new VolumeACW(parent);
                break;
            case "WhiteNoiseAC":
                widget = new WhiteNoiseACW(parent);
                break;
            case "MixerAC":
                widget = new MixersACW(parent);
                break;
            case "ReverbAC":
                widget = new ReverbACW(parent);
                break;
            default:
                assert true: "Invalid widget name";
        }
        widgets_.add(widget);
        return widget;
    }

    private void handleButtonPress(Button btn) throws LineUnavailableException {
        System.out.println(btn.getText() + " is pressed");
        AudioClip.play(btn.getText());
    }

    public static AudioComponentWidget speaker_;
    public static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>();
    public static AnchorPane mainCanvas_;
    private HBox topBar_;
    public static VBox rightPane_;
    private HBox bottomBar_;
    private BorderPane baseLayout_;
    public static Text numWidgetsExceededMsg_ = new Text("You can't have\nmore than 8 widgets\nat the same time.");
    private ArrayList<Button> acwGeneratingBtns = new ArrayList<>();
}

