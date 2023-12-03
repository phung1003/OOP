package com.example.demo;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class AnagramResultController extends ResultGameController{
    @FXML
    private Label resultLabel;

    @FXML
    private Label score;


    public void setResult(String result) {
        resultLabel.setText(result);
    }

    public void setScore(int score) {
        this.score.setText("Your score: " + score);
    }

    @FXML
    public void backToMenu() {
        try {
            switchScene("Menu.fxml", up);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void playAgain() {
        try {
            switchScene("AnagramGame.fxml", up);
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
