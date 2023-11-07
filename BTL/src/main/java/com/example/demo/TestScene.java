package com.example.demo;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestScene {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;


    @FXML
    public void addElement() throws IOException, URISyntaxException {

        for (int i = 0; i < 10; i++) {
            HBox hBox = new HBox();
            Label label = new Label("Hellh");
            label.setId("label");
            label.setWrapText(true);
            ImageView imageView = new ImageView(new Image(getClass().getResource("search.png").toURI().toString()));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(20);
            hBox.getChildren().addAll(
                    label,
                    imageView
            );
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(10);
            vBox.getChildren().addAll(
                    hBox
            );
        }
    }

}
