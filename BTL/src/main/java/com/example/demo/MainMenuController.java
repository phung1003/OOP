package com.example.demo;


import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuController extends SceneController{
    @FXML
    private void switchToScene1() throws IOException {
        // Code to switch to Scene 1
        switchScene("AnagramGame.fxml", left);
    }

    @FXML
    private void switchToScene2() throws IOException {
        switchScene("Hangman.fxml", left);

    }

    @FXML
    public void Back() throws IOException {
        switchScene("FirstScene.fxml", right);
    }
}

