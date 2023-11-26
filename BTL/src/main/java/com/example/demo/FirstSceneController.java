package com.example.demo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;


public class FirstSceneController {
    private Stage stage;
    private Scene scene;


    @FXML
    private Label label;
    @FXML
    private Button translate;
    @FXML
    private Button dictionary;
    @FXML
    private AnchorPane anchorPane;



    @FXML
    protected void onHelloButtonClick() {
        label.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void hello() {
        label.setText(" JavaFX Application!");
    }
    @FXML
    protected void changeScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("SecondScene.fxml"));

        Scene scene = translate.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(scene.getWidth());
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
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
    protected void changeScreen2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ThirdScene.fxml"));

        Scene scene = dictionary.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(scene.getWidth());

        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            stackPane.getChildren().remove(anchorPane);
        });
        timeline.play();

    }


}
