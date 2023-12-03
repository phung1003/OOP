package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AnagramGameController extends SceneController implements Initializable {
    @FXML
    private Label timerLabel;
    private int secondsRemaining = 60;
    private Timeline timeline;

    @FXML
    private HBox buttonBox;

    @FXML
    private Text selectedLettersText;

    private String originalWord;
    private List<Button> letterButtons = new ArrayList<>();
    @FXML
    private Label scoreLabel;
    private static int score = 0;

    private Map<Integer, String> wordMap = new HashMap<>();

    private void setWordMap() {
        int wordCount = 0;

        List<String> words;
        try {
            words = Files.readAllLines(Paths.get("HangmanAndAnagramGame/WordsForGameUpperCase.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String word : words) {
            wordCount++;
            wordMap.put(wordCount, word);
        }
    }

    public static void setScore(int score) {
        AnagramGameController.score = score;
    }

    public void setSecondsRemaining(int secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWordMap();
        originalWord = generateRandomWord();
        List<Character> shuffledCharacters = shuffleWord(originalWord);

        createButtonBox(shuffledCharacters);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), this::updateTimer)
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private String generateRandomWord() {
        Random random = new Random();
        int randomIndex = new Random().nextInt(wordMap.size());

        System.out.println(wordMap.get(randomIndex));
        System.out.println("Random Integer: " + randomIndex);

        return wordMap.get(randomIndex);
    }

    private List<Character> shuffleWord(String word) {
        List<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        return characters;
    }

    private void createButtonBox(List<Character> characters) {
        for (char c : characters) {
            Button button = createLetterButton(String.valueOf(c));

            // Đặt font-weight là đậm (bold) và font-family là Arial
            button.setStyle("-fx-font-weight: bold; -fx-font-family: 'Arial';");

            // Đặt kích thước ưu tiên cho mỗi Button
            button.setPrefWidth(40); // Đặt chiều rộng ưu tiên
            button.setPrefHeight(40); // Đặt chiều cao ưu tiên

            buttonBox.getChildren().add(button);
            letterButtons.add(button);
        }
    }

    private Button createLetterButton(String letter) {
        Button button = new Button(letter);
        button.setOnAction(e -> handleButtonClick(button));
        return button;
    }

    private void handleButtonClick(Button button) {
        String buttonText = button.getText();
        if (button.isDisabled()) {
            button.setDisable(false);
            selectedLettersText.setText(selectedLettersText.getText().replace(buttonText, ""));
        } else {
            button.setDisable(true);
            selectedLettersText.setText(selectedLettersText.getText() + buttonText);
        }

        if(selectedLettersText.getText().length() == originalWord.length()
                && !selectedLettersText.getText().equals(originalWord)) {
            selectedLettersText.setFill(Color.RED); // Đoán sai thì chuyển sang màu đỏ
        }

        if (selectedLettersText.getText().equals(originalWord)) {
            selectedLettersText.setFill(Color.GREEN); // Đoán đúng thì chuyển sang màu xanh
            score += 10;
            updateScoreLabel();
            PauseTransition pause = getPauseTransition();
            // Bắt đầu PauseTransition
            pause.play();
        }
    }


    private PauseTransition getPauseTransition() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));

        // Xử lý sự kiện sau khi thời gian đợi kết thúc
        pause.setOnFinished(event -> {
            // Thực hiện các hành động bạn muốn sau khi đợi
            buttonBox.getChildren().clear();
            selectedLettersText.setText("");
            selectedLettersText.setFill(Color.BLACK); // Trả về màu mặc định

            originalWord = generateRandomWord();
            List<Character> shuffledCharacters = shuffleWord(originalWord);

            createButtonBox(shuffledCharacters);
        });
        return pause;
    }

    @FXML
    private void resetGame() {
        for (Button button : letterButtons) {
            button.setDisable(false);
        }
        selectedLettersText.setFill(Color.BLACK); // Trả về màu mặc định
        selectedLettersText.setText("");
    }

    private void updateTimer(ActionEvent event) {
        secondsRemaining--;

        if (secondsRemaining >= 0) {
            int mins = secondsRemaining / 60;
            int secs = secondsRemaining % 60;
            String timerText = String.format("%02d:%02d", mins, secs);
            timerLabel.setText(timerText);
        } else {
            timeline.stop();
            try {
                showResultScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setScore(0);
        }
    }

    private void showResultScreen() throws IOException {
        // Gọi SceneManager để chuyển đến màn hình kết quả và truyền điểm số

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AnagramResult.fxml"));
        Parent root = loader.load();

        AnagramResultController controller = loader.getController();

        controller.setResult("Time up!");
        controller.setScore(score);

        Scene scene = anchorPane.getScene();
        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(scene.getWidth());
        stackPane.getChildren().add(root);

        playAnimation(left, root, scene, stackPane);
    }

    @FXML
    public void Back() throws IOException {
        this.timeline.stop();
        switchScene("Menu.fxml", right);
    }
}
