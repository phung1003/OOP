package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class FirstSceneController {
    private Stage stage;
    private Scene scene;

    Media media;
    MediaPlayer mediaPlayer;

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
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void changeScreen2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ThirdScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void playAudio(ActionEvent event) throws IOException {
        File file = new File("file.mp3");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
