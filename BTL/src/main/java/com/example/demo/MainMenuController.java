package com.example.demo;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button switchToScene1Button;
    @FXML
    private Button switchToScene2Button;
    @FXML
    private AnchorPane anchorPane;

    public void initialize() {
        // Initialize your controller, if needed
    }

    @FXML
    private void switchToScene1() throws IOException {
        // Code to switch to Scene 1

        Parent root = FXMLLoader.load(Main.class.getResource("AnagramGame.fxml"));

        Scene scene = switchToScene1Button.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(scene.getWidth());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            stackPane.getChildren().remove(anchorPane);
        });
        timeline.play();
    }

    @FXML
    private void switchToScene2() throws IOException {
        // Code to switch to Scene 2

        Parent root = FXMLLoader.load(Main.class.getResource("Hangman.fxml"));

        Scene scene = switchToScene2Button.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(scene.getWidth());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            stackPane.getChildren().remove(anchorPane);
        });
        timeline.play();

    }
}

