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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class SecondSceneController {
    private Scene scene;
    private Stage stage;


    @FXML
    private TextArea source;
    @FXML
    private TextArea des;
    @FXML
    private Label s;
    @FXML
    private Label d;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button back;

    @FXML
    public void Translate() throws IOException {
        if (s.getText().equals("English")) {
            des.setText(Json.callTranslate(source.getText(), true));
        } else {
            des.setText(Json.callTranslate(source.getText(), false));
        }
    }

    @FXML
    public void Change() throws IOException {
        String temp = s.getText();
        s.setText(d.getText());
        d.setText(temp);
    }


    @FXML
    public void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FirstScene.fxml"));

        Scene scene = back.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(-scene.getWidth());

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


}
