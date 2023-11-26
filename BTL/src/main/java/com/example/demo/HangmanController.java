package com.example.demo;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HangmanController implements Initializable {
    private Map<Integer, String> wordMap = new HashMap<>();
    private String wordToGuess;
    @FXML
    private Label correctGuessedLetters;
    @FXML
    private ImageView hangmanImage;
    private String guessedLetters = "";

    @FXML
    private HBox hBox1;
    @FXML
    private HBox hBox2;
    @FXML
    private HBox hBox3;
    @FXML
    private AnchorPane anchorPane;

    private void enableAllButtonsInHBox() {
        for (javafx.scene.Node node : hBox1.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(false);
            }
        }

        for (javafx.scene.Node node : hBox2.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(false);
            }
        }

        for (javafx.scene.Node node : hBox3.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(false);
            }
        }
    }

    private void setWordMap() {
        int wordCount = 0;

        List<String> words = null;
        try {
            String filename="HangmanAndAnagramGame/WordsForGameUpperCase.txt";
            Path pathToFile = Paths.get(filename);
            System.out.println(pathToFile.toAbsolutePath());

            words = Files.readAllLines(pathToFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String word : words) {
            wordCount++;
            wordMap.put(wordCount, word);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWordMap();
        setWordToGuess();
        setGuessedLetters();
        displayCorrectGuessedLetter();
        try {
            updateHangmanImage();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWordToGuess() {
        Random random = new Random();
        int randomIndex = new Random().nextInt(wordMap.size());

        System.out.println(wordMap.get(randomIndex));
        System.out.println("Random Integer: " + randomIndex);

        this.wordToGuess = wordMap.get(randomIndex);
    }

    public void setGuessedLetters() {
        this.guessedLetters = "";
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            clickedButton.setDisable(true);
            String buttonText = clickedButton.getText();
            System.out.println("Button Clicked! Text: " + buttonText);
            guessedLetters += buttonText;
            displayCorrectGuessedLetter();
            try {
                updateHangmanImage();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            //alertNotification();
            showResultScreen();
        }
    }

    @FXML
    public void displayCorrectGuessedLetter() {
        String displayedWord = "";
        for (char letter : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(String.valueOf(letter))) {
                displayedWord += letter;
            } else {
                displayedWord += " _";
            }
        }
        correctGuessedLetters.setText(displayedWord);
    }

    public int calculateIncorrectGuesses() {
        int incorrectGuesses = 0;
        for (char letter : guessedLetters.toCharArray()) {
            if (!wordToGuess.contains(String.valueOf(letter))) {
                incorrectGuesses++;
            }
        }
        return incorrectGuesses;
    }

    private void updateHangmanImage() throws URISyntaxException {
        int incorrectGuesses = calculateIncorrectGuesses();
        if (1 <= incorrectGuesses && incorrectGuesses <= 10) {
            Image image = new Image(getClass().getResource("Hangman/hangman" + incorrectGuesses + ".png").toURI().toString());
            hangmanImage.setImage(image);
        }
    }

    private void showResultScreen() {
        int incorrectGuesses = calculateIncorrectGuesses();
        if (incorrectGuesses == 10) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1.25));
            // Xử lý sự kiện sau khi thời gian đợi kết thúc
            pause.setOnFinished(event -> {
                try {
                    showResultScene("You Lose!", wordToGuess);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            pause.play();
        }
        if (correctGuessedLetters.getText().equals(wordToGuess)) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1.25));
            // Xử lý sự kiện sau khi thời gian đợi kết thúc
            pause.setOnFinished(event -> {
                try {
                    showResultScene("You Won!", wordToGuess);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            pause.play();
        }
    }

    public void showResultScene(String result, String wordToGuess) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("HangmanResult.fxml"));
        Parent root = loader.load();

        HangmanResultController controller = loader.getController();
        controller.setResult(result);
        controller.setGuessedWord(wordToGuess);

        Scene scene = anchorPane.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(scene.getWidth());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            stackPane.getChildren().remove(anchorPane);
        });
        timeline.play();
    }
}
