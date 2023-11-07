package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class ThirdSceneController {
    private Scene scene;
    private Stage stage;
    private WordModel[] wordM;

    @FXML
    private TextArea word;
    @FXML
    private TextField search;
    @FXML
    private VBox vBox;

    /**
     * Tìm kiếm từ.
     */
    @FXML
    public void Search() throws IOException, URISyntaxException {
        vBox.getChildren().clear();
        wordM = Json.callApi(search.getText());
        wordM[0].removeDuplicates();

        //Tieu de
        Label word = new Label(wordM[0].word);
        word.setFont(new Font("Comics Sans MS", 40));
        word.setTextFill(Color.DARKBLUE);
        vBox.getChildren().add(
                word
        );

        //Phat am
        int i = 1;
        for (Phonetics item : wordM[0].phonetics) {
            if (!item.audio.equals("")) {
                HBox hBox = new HBox();
                Label phonetic = new Label("(" + i + ") " + item.text);
                phonetic.setFont(new Font("Comics Sans MS", 20));
                ImageView imageView = new ImageView(new Image(getClass().getResource("search.png").toURI().toString()));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(20);
                hBox.getChildren().addAll(
                        phonetic,
                        imageView
                );
                hBox.setSpacing(5);
                hBox.setAlignment(Pos.CENTER_LEFT);
                i++;
                vBox.getChildren().add(hBox);
            }
        }

        //Nghia

        for (Meanings item : wordM[0].meanings) {
            //dạng từ
            Label partOfSpeech = new Label(item.getPartOfSpeech());
            partOfSpeech.setFont(new Font("Comics Sans MS", 19));
            vBox.getChildren().add(partOfSpeech);
            i = 1;
            //Định nghĩa
            for (Definitions item2 : item.definitions) {
                Label stt = new Label("(" + i + ")");
                stt.setFont(new Font("Comics Sans MS", 14));
                stt.setTextFill(Color.PURPLE);
                Label temp = new Label("\tDefinition: " + item2.definition);
                temp.setWrapText(true);
                temp.setMinWidth(vBox.USE_PREF_SIZE);
                Label temp2 = new Label("\tExample: " + item2.example);
                temp2.setWrapText(true);
                temp2.setMinWidth(vBox.USE_PREF_SIZE);
                vBox.getChildren().addAll(stt, temp, temp2);
                i++;
            }
            //ĐỒng nghĩa
            vBox.getChildren().add(new Label("Synonyms: "));
            for (String item2 : item.synonyms) {
                Label temp = new Label("\t- " + item2);
                vBox.getChildren().addAll(temp);
            }
            //Trái nghĩa
            vBox.getChildren().add(new Label("Antonyms: "));
            for (String item2 : item.antonyms) {
                Label temp = new Label("\t- " + item2);
                vBox.getChildren().addAll(temp);
            }

        }

    }


    @FXML
    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FirstScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

}
