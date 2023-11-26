package com.example.testchangescene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

    @FXML
    private Button switchToScene1Button;

    @FXML
    private Button switchToScene2Button;

    public void initialize() {
        // Initialize your controller, if needed
    }

    @FXML
    private void switchToScene1() {
        // Code to switch to Scene 1
        SceneManager.switchScene("AnagramGame.fxml");
    }

    @FXML
    private void switchToScene2() {
        // Code to switch to Scene 2
        SceneManager.switchScene("Hangman.fxml");
    }
}

