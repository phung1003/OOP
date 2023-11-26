package com.example.testchangescene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AnagramResultController extends ResultGameController{
    @FXML
    private Label resultLabel;

    @FXML
    private Label score;

    private AnagramGameController gameController;


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
        SceneManager.switchToMenuScene();
    }

    @FXML
    public void playAgain() {
        SceneManager.switchScene("AnagramGame.fxml");
    }

    @FXML
    public void exit() {
        // Code to switch back to Menu Scene
        System.exit(0);
    }

    public void setGameController(AnagramGameController gameController) {
        this.gameController = gameController;
    }
}
