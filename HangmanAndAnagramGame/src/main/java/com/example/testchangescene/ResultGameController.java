package com.example.testchangescene;

import javafx.scene.control.Label;

public abstract class ResultGameController {
    private Label resultLabel;

    public abstract void setResult(String result);

    public abstract void backToMenu();

    public abstract void playAgain();

    public abstract void exit();
}
