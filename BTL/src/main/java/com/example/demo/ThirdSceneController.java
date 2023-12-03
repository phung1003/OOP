package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ThirdSceneController extends SceneController implements Initializable {
    private WordModel[] wordM;


    Media media;
    MediaPlayer mediaPlayer;

    @FXML
    private TextField search;
    @FXML
    private VBox vBox;
    @FXML
    private VBox mainVBox;
    @FXML
    private ScrollPane scrollPane;

    public ThirdSceneController() {
    }


    @FXML
    protected void playAudio(String link) throws IOException {
        File file = new File(Audio.downloadMp3(link));
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        Audio.Delete();
    }

    /**
     * Tìm kiếm từ.
     */
    @FXML
    public void Search() throws URISyntaxException {
        scrollPane.setFitToWidth(true);
        vBox.getChildren().clear();
        vBox.setAlignment(Pos.TOP_LEFT);
        try {
            wordM = Json.callApi(search.getText());
            wordM[0].removeDuplicates();
            //Tieu de
            addTilte();
            //Phat am
            addPhonetics();
            //Nghia
            addMeaning();
        } catch (IOException e) {
            cantFindWord();
            throw new RuntimeException(e);
        }


    }

    private void cantFindWord() throws URISyntaxException {
        Text label = new Text("COULDN'T FIND ANY WORD");
        label.setFont(new Font(40));
        ImageView imageView = new ImageView(new Image(getClass().getResource("image/fail.png").toURI().toString()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(300);
        vBox.getChildren().addAll(label, imageView);
        vBox.setMargin(label, new Insets(50, 5, 0, 5));
        vBox.setMargin(imageView, new Insets(100, 5, 10, 5));

        vBox.setAlignment(Pos.CENTER);
    }

    private void addMeaning() {
        for (WordModel.Meanings item : wordM[0].meanings) {
            //dạng từ
            addPartOfSpeech(item);

            addMeaningAndEx(item);

            //ĐỒng nghĩa
            addSynAndAnt("Synonyms: ", item.synonyms);

            //Trái nghĩa
            addSynAndAnt("Antonyms: ", item.antonyms);

        }
    }


    private void addSynAndAnt(String s, List<String> item) {
        Text label1 = new Text(s);
        label1.setFont(new Font(20));
        label1.setFill(Color.DARKBLUE);
        vBox.getChildren().add(label1);
        vBox.setMargin(label1, new Insets(0, 5, 0, 5));

        for (String item2 : item) {
            Text temp = new Text("\t- " + item2);
            temp.setFont(new Font(16));
            vBox.getChildren().addAll(temp);
            vBox.setMargin(temp, new Insets(0, 5, 0, 5));

        }
    }

    private void addMeaningAndEx(WordModel.Meanings item) {
        int i = 1;
        //Định nghĩa
        for (WordModel.Definitions item2 : item.definitions) {
            Text stt = new Text("(" + i + ")");
            stt.setFont(new Font("Comics Sans MS", 17));
            stt.setFill(Color.PURPLE);

            Text def = new Text("(+) Definition: ");
            Text temp1 = new Text("\n\t- " + item2.definition);
            def.setFont(new Font(16));
            temp1.setFont(new Font(16));
            TextFlow tf1 = new TextFlow(def);
            tf1.getChildren().add(temp1);
            vBox.setMargin(temp1, new Insets(0, 5, 0, 5));


            Text ex = new Text("(+) Example: ");
            Text temp2 = new Text("\n\t- " + item2.example);
            ex.setFont(new Font(16));
            temp2.setFont(new Font(16));
            TextFlow tf2 = new TextFlow(ex);
            tf2.getChildren().add(temp2);
            vBox.setMargin(temp2, new Insets(0, 5, 0, 5));


            vBox.getChildren().addAll(stt, tf1, tf2);
            vBox.setMargin(stt, new Insets(0, 5, 0, 5));
            vBox.setMargin(tf1, new Insets(0, 5, 0, 5));
            vBox.setMargin(tf2, new Insets(0, 5, 0, 5));

            i++;
        }
    }

    private void addPartOfSpeech(WordModel.Meanings item) {
        Text partOfSpeech = new Text(item.getPartOfSpeech());
        partOfSpeech.setFont(new Font("Comics Sans MS", 30));
        partOfSpeech.setFill(Color.DARKBLUE);
        vBox.getChildren().add(partOfSpeech);
        vBox.setMargin(partOfSpeech, new Insets(0, 5, 0, 5));
    }

    private void addPhonetics() {
        int i = 1;
        for (WordModel.Phonetics item : wordM[0].phonetics) {
            if (item.text != null) {
                Text phonetic = new Text("(" + i + ") " + item.text);
                phonetic.setFont(new Font("Comics Sans MS", 30));
                i++;
                vBox.getChildren().add(phonetic);
                vBox.setMargin(phonetic, new Insets(0, 5, 0, 5));
            }
        }
    }

    private void addTilte() throws URISyntaxException {
        HBox hBox = new HBox();
        hBox.setSpacing(0);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Text word = new Text(wordM[0].word);

        ImageView imageView = new ImageView(new Image(getClass().getResource("image/volume.png").toURI().toString()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        Button button = createButton(word, imageView);

        word.setFont(new Font("Comics Sans MS", 60));
        word.setFill(Color.DARKBLUE);

        hBox.getChildren().addAll(word, button);
        vBox.getChildren().add(hBox);
        vBox.setMargin(hBox, new Insets(0, 5, 0, 5));
    }

    private Button createButton(Text word, ImageView imageView) {
        Button button = new Button();
        button.setOnAction(event -> {
            try {
                System.out.println(1);
                playAudio("https://howjsay.com/mp3/" + word.getText().replaceAll(" ", "%20") + ".mp3");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        button.setGraphic(imageView);
        button.setText("");
        button.setStyle("-fx-border-color: white");
        button.setStyle("-fx-background-color: transparent");
        button.setCursor(Cursor.HAND);
        return button;
    }


    @FXML
    public void Back() throws IOException {
        switchScene("FirstScene.fxml", right);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainVBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    Search();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
