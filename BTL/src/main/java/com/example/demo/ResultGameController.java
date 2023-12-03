package com.example.demo;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public abstract class ResultGameController extends SceneController{
    private Label resultLabel;

    @FXML
    private AnchorPane anchorPane;

    public abstract void setResult(String result);

    public abstract void backToMenu();

    public abstract void playAgain();

    public abstract void exit();

}
