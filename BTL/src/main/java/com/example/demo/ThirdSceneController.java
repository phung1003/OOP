package com.example.demo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ThirdSceneController implements Initializable {
    private Scene scene;
    private Stage stage;
    private WordModel[] wordM;


    Media media;
    MediaPlayer mediaPlayer;

    @FXML
    private TextArea word;
    @FXML
    private TextField search;
    @FXML
    private VBox vBox;
    @FXML
    private VBox mainVBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button back;


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
            Label word = new Label(wordM[0].word);
            word.setFont(new Font("Comics Sans MS", 40));
            word.setTextFill(Color.DARKBLUE);
            vBox.getChildren().add(
                    word
            );
            vBox.setMargin(word, new Insets(0, 5, 0, 5));

            //Phat am
            int i = 1;
            for (WordModel.Phonetics item : wordM[0].phonetics) {
                if (!item.audio.equals("")) {
                    HBox hBox = new HBox();
                    Label phonetic = new Label("(" + i + ") " + item.text);
                    phonetic.setFont(new Font("Comics Sans MS", 20));
                    ImageView imageView = new ImageView(new Image(getClass().getResource("image/mingcute_volume-fill.png").toURI().toString()));
                    imageView.setPreserveRatio(true);
                    Button button = new Button();
                    button.setOnAction(event -> {
                        try {
                            System.out.println(1);
                            playAudio(item.audio);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    button.setGraphic(imageView);
                    button.setText("");
                    button.setStyle("-fx-border-color: white");
                    button.setStyle("-fx-background-color: transparent");
                    button.setCursor(Cursor.HAND);


                    hBox.getChildren().addAll(
                            phonetic,
                            button
                    );
                    hBox.setSpacing(0);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    i++;
                    vBox.getChildren().add(hBox);
                    vBox.setMargin(hBox, new Insets(0, 5, 0, 5));

                }
            }

            //Nghia

            for (WordModel.Meanings item : wordM[0].meanings) {
                //dạng từ
                Label partOfSpeech = new Label(item.getPartOfSpeech());
                partOfSpeech.setFont(new Font("Comics Sans MS", 19));
                vBox.getChildren().add(partOfSpeech);
                vBox.setMargin(partOfSpeech, new Insets(0, 5, 0, 5));

                i = 1;
                //Định nghĩa
                for (WordModel.Definitions item2 : item.definitions) {
                    Label stt = new Label("(" + i + ")");
                    stt.setFont(new Font("Comics Sans MS", 14));
                    stt.setTextFill(Color.PURPLE);

                    Text def = new Text("(+) Definition: ");
                    Text temp1 = new Text("\n\t- " + item2.definition);
                    TextFlow tf1 = new TextFlow(def);
                    tf1.getChildren().add(temp1);
                    vBox.setMargin(temp1, new Insets(0, 5, 0, 5));


                    Text ex = new Text("(+) Example: ");
                    Text temp2 = new Text("\n\t- " + item2.example);
                    TextFlow tf2 = new TextFlow(ex);
                    tf2.getChildren().add(temp2);
                    vBox.setMargin(temp2, new Insets(0, 5, 0, 5));


                    vBox.getChildren().addAll(stt, tf1, tf2);
                    vBox.setMargin(stt, new Insets(0, 5, 0, 5));
                    vBox.setMargin(tf1, new Insets(0, 5, 0, 5));
                    vBox.setMargin(tf2, new Insets(0, 5, 0, 5));

                    i++;
                }

                //ĐỒng nghĩa
                Label label1 = new Label("Synonyms: ");
                label1.setFont(new Font(18));
                vBox.getChildren().add(label1);
                vBox.setMargin(label1 , new Insets(0, 5, 0, 5));

                for (String item2 : item.synonyms) {
                    Label temp = new Label("\t- " + item2);
                    vBox.getChildren().addAll(temp);
                    vBox.setMargin(temp, new Insets(0, 5, 0, 5));

                }

                //Trái nghĩa
                Label label2 = new Label("Antonyms: ");
                label2.setFont(new Font(18));
                vBox.getChildren().add(label2);
                vBox.setMargin(label2, new Insets(0, 5, 0, 5));

                for (String item2 : item.antonyms) {
                    Label temp = new Label("\t- " + item2);

                    vBox.getChildren().addAll(temp);
                    vBox.setMargin(temp, new Insets(0, 5, 0, 5));

                }

            }
        } catch (IOException e) {
            Label label = new Label("Couldn't find any word");
            label.setFont(new Font(40));
            label.setAlignment(Pos.CENTER);
            vBox.getChildren().add(
                    label
            );
            vBox.setMargin(label, new Insets(0, 5, 0, 5));

            vBox.setAlignment(Pos.CENTER);
            throw new RuntimeException(e);
        }


    }


    @FXML
    public void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FirstScene.fxml"));

        Scene scene = back.getScene();

        StackPane stackPane = (StackPane) scene.getRoot();
        root.translateXProperty().set(-scene.getWidth());
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainVBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    try {
                        Search();
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
