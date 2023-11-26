package com.example.testchangescene;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HangmanResultController {
    @FXML
    private Label resultLabel;

    @FXML
    private Label guessedWord;

    private HangmanController gameController;

    public void initialize() {
        // Initialize your controller, if needed
    }

    public void setResult(String result) {
        resultLabel.setText(result);
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord.setText("Word to guess: " + guessedWord);
    }

    @FXML
    public void backToMenu() {
        SceneManager.switchToMenuScene();
    }

    @FXML
    public void playAgain() {
        SceneManager.switchScene("Hangman.fxml");
    }

    @FXML
    public void exit() {
        // Code to switch back to Menu Scene
        System.exit(0);
    }

    public void setGameController(HangmanController gameController) {
        this.gameController = gameController;
    }
}
