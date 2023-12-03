package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HangmanResultController extends ResultGameController{
    @FXML
    private Label resultLabel;

    @FXML
    private Label guessedWord;


    public void setResult(String result) {
        resultLabel.setText(result);
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord.setText("Word to guess: " + guessedWord);
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
            switchScene("Hangman.fxml", up);
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
