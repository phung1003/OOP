package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ThirdSceneController {
    private Scene scene;
    private Stage stage;

    @FXML
    private TextArea word;
    @FXML
    private TextField search;
    @FXML
    public void Search() throws IOException {

        WordModel[] wordM = Json.callApi(search.getText());
        word.setText(wordM[0].word + "\n" + wordM[0].phonetic);
    }

    @FXML
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FirstScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

}
