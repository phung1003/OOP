package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WordSearchApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("word_search.fxml"));
        Parent root = loader.load();


        primaryStage.setTitle("Word Search App");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}

