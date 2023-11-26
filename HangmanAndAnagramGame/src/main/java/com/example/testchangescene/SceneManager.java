package com.example.testchangescene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String sceneFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(sceneFXML));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
            // Handle exception appropriately
        }
    }

    public static void switchToMenuScene() {
        switchScene("Menu.fxml");
    }

    public static void switchToAnagramResultScene(String result, int score, AnagramGameController gameController) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("AnagramResult.fxml"));
            Parent root = loader.load();
            AnagramResultController controller = loader.getController();
            controller.setResult(result);
            controller.setScore(score);

            // Pass the AnagramGameController reference to ResultController
            controller.setGameController(gameController);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

    public static void switchToHangmanResultScene(String result, String guessedWord, HangmanController gameController) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("HangmanResult.fxml"));
            Parent root = loader.load();
            HangmanResultController controller = loader.getController();
            controller.setResult(result);
            controller.setGuessedWord(guessedWord);

            // Pass the AnagramGameController reference to ResultController
            controller.setGameController(gameController);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
}

