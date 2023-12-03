package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FirstSceneController extends SceneController implements Initializable {
    @FXML
    private Button translate;
    @FXML
    private Button dictionary;
    @FXML
    private Button game;
    @FXML
    private Button dictionaryDB;
    @FXML
    private AnchorPane pane;

    @FXML
    protected void dictionaryPick() {
        pane.setVisible(true);
    }
    @FXML
    protected void changeTranslate() throws IOException {
        switchScene("SecondScene.fxml", left);
    }

    @FXML
    protected void changeDictionary() throws IOException {
        switchScene("ThirdScene.fxml", left);
    }

    @FXML
    protected void changeGame() throws IOException {
        switchScene("Menu.fxml", left);

    }

    @FXML
    protected void changeDB() throws IOException {
        switchScene("word_search.fxml", left);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> pane.setVisible(false));
    }
}
