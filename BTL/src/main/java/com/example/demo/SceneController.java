package com.example.demo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public abstract class SceneController {
    public static final int up = 1;
    public static final int down = 2;
    public static final int left = 3;
    public static final int right = 4;
    @FXML
    protected AnchorPane anchorPane;

    @FXML
    public void switchScene(String sceneName, int transitionMode) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
        Scene scene = anchorPane.getScene();
        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);
        playAnimation(transitionMode, root, scene, stackPane);
    }

    protected void playAnimation(int transitionMode, Parent root, Scene scene, StackPane stackPane) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = chooseMode(transitionMode, root, scene);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> stackPane.getChildren().remove(anchorPane));
        timeline.play();
    }

    public KeyValue chooseMode(int transitionMode, Parent root, Scene scene) {
        KeyValue keyValue = null;
        switch (transitionMode) {
            case up:
                root.translateYProperty().set(scene.getHeight());
                keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
                break;
            case down:
                root.translateYProperty().set(-scene.getHeight());
                keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
                break;
            case left:
                root.translateXProperty().set(scene.getWidth());
                keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                break;
            case right:
                root.translateXProperty().set(-scene.getWidth());
                keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                break;
        }
        return keyValue;
    }


}
