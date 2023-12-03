package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: white");
        Parent root = FXMLLoader.load(Main.class.getResource("FirstScene.fxml"));
        stackPane.getChildren().add(root);
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(700);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
