package com.example.demo;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public abstract class ResultGameController {
    private Label resultLabel;

    @FXML
    private AnchorPane anchorPane;

    public abstract void setResult(String result);

    public abstract void backToMenu();

    public abstract void playAgain();

    public abstract void exit();

    public void switchScene(String sceneName) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(sceneName));

        Scene scene = anchorPane.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateYProperty().set(scene.getHeight());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            stackPane.getChildren().remove(anchorPane);
        });
        timeline.play();
    }
}
