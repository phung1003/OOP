package com.example.testchangescene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setPrimaryStage(primaryStage);

        // Load menu scene
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Menu Scene");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

