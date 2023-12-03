package com.example.demo;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SecondSceneController extends SceneController implements Initializable {
    @FXML
    private TextArea source;
    @FXML
    private TextArea des;
    @FXML
    private Label s;
    @FXML
    private Label d;
    @FXML
    private ImageView sFlag;
    @FXML
    private ImageView dFlag;

    @FXML
    public void Translate() throws IOException {
        Trans();
    }

    public void Trans() throws IOException {
        if (source.getText().isEmpty()) {
            des.setText("");
        } else {
            if (s.getText().equals("English")) {
                des.setText(Json.callTranslate(source.getText(), true));
            } else {
                des.setText(Json.callTranslate(source.getText(), false));
            }
        }

    }

    @FXML
    public void Change() throws IOException {
        //Đổi tiêu đề
        String temp = s.getText();
        s.setText(d.getText());
        d.setText(temp);

        //Đổi ảnh
        Image image = sFlag.getImage();
        sFlag.setImage(dFlag.getImage());
        dFlag.setImage(image);

        //Đổi nội dung
        temp = des.getText();
        des.setText(source.getText());
        source.setText(temp);
        Trans();
    }


    @FXML
    public void Back() throws IOException {
        switchScene("FirstScene.fxml", right);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            try {
                System.out.println(source.getText());
                Trans();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(1);
        source.setOnKeyTyped(event -> {
            timeline.stop();
            timeline.playFromStart();
        });

    }
}
