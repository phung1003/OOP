package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class FirstSceneController {
    private Stage stage;
    private Scene scene;

    @FXML
    private Label label;
    @FXML
    private Button translate;
    @FXML
    private Button dictionary;
    @FXML
    private AnchorPane anchorPane;



    @FXML
    protected void onHelloButtonClick() {
        label.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void hello() {
        label.setText(" JavaFX Application!");
    }
    @FXML
    protected void changeScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SecondScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void changeScreen2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ThirdScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();

    }
}
