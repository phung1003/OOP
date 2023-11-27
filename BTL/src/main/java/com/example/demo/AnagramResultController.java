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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class AnagramResultController extends ResultGameController{
    @FXML
    private Label resultLabel;

    @FXML
    private Label score;





    public void initialize() {
        // Initialize your controller, if needed
    }

    public void setResult(String result) {
        resultLabel.setText(result);
    }

    public void setScore(int score) {
        this.score.setText("Your score: " + score);
    }

    @FXML
    public void backToMenu() {
        try {
            switchScene("Menu.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void playAgain() {
        try {
            switchScene("AnagramGame.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exit() {
        // Code to switch back to Menu Scene
        System.exit(0);
    }

}
