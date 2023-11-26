package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.Position;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: white");
        Parent root = FXMLLoader.load(Main.class.getResource("FirstScene.fxml"));
        stackPane.getChildren().add(root);
        Scene scene = new Scene(stackPane);
      //  scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
