package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;


public class AddUpdateRemoveNewController extends WordSearchController implements Initializable {


    @FXML
    private Label notification;


    @FXML
    private TextArea newDefinition;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView notifyImg;

    private static final int MAX_SUGGESTIONS = 50;


    public void initialize() {
        init();
    }

    public void init() {

        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> pane.setVisible(false));
        suggestionList.setVisible(false);  // Ẩn ListView khi không có từ gợi ý

        // Xử lý sự kiện khi người dùng nhập vào ô tìm kiếm
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateSuggestions(newValue));

        anchorPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                String keyWord = searchField.getText();
                String definition = dictionaryLookup(keyWord);
                wordTargetDefinition.setText(definition);
                suggestionList.setVisible(false);
            }
        });

        suggestionList.setOnMouseClicked(event -> {
            String selectedWord = suggestionList.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                searchField.setText(selectedWord);
                suggestionList.setVisible(false);  // Ẩn ListView sau khi chọn từ
                wordTargetDefinition.setText(dictionaryLookup(selectedWord)); // Đặt văn bản cho textArea
            }
        });
    }


    // Cập nhật danh sách từ gợi ý dựa trên từ khóa nhập vào
    private void updateSuggestions(String keyword) {
        if (!keyword.isEmpty()) {
            ObservableList<String> suggestions = FXCollections.observableArrayList();

            for (String word : wordMap.keySet()) {
                if (word.startsWith(keyword)) {
                    suggestions.add(word);
                    if (suggestions.size() == MAX_SUGGESTIONS) {
                        break; // Stop adding suggestions once the limit is reached
                    }
                }
            }

            suggestionList.setItems(suggestions);
            suggestionList.setVisible(true);  // Hiển thị ListView khi có từ gợi ý
        } else {
            suggestionList.getItems().clear();
            suggestionList.setVisible(false);  // Ẩn ListView khi không có từ gợi ý
        }
    }

    public void handleAddButton() throws URISyntaxException {
        String newTarget = searchField.getText();
        String newTargetDefinition = newDefinition.getText();
        if (newTargetDefinition.isEmpty()) {
            notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
            notification.setText("New definition is empty");
            pane.setVisible(true);
        }
        if (!newTarget.isEmpty() && !newTargetDefinition.isEmpty()) {
            addEntryToDatabase(newTarget, newTargetDefinition);
        }
        wordTargetDefinition.clear();
        newDefinition.clear();
    }

    public void addEntryToDatabase(String target, String definition) {
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection(url, username, password);

            // Truy vấn SQL để kiểm tra sự tồn tại của target
            String checkExistenceQuery = "SELECT COUNT(*) AS count FROM dictionary WHERE target = ?";
            try (PreparedStatement checkExistenceStatement = connection.prepareStatement(checkExistenceQuery)) {
                checkExistenceStatement.setString(1, target);

                // Thực hiện truy vấn SELECT
                try (ResultSet resultSet = checkExistenceStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt("count") > 0) {
                        notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
                        // Target đã tồn tại, in ra thông báo
                        notification.setText("The word '" + target + "' already exists. Cannot add new.");
                        System.out.println("The word '" + target + "' already exists. Cannot add new.");
                    } else {
                        // Target chưa tồn tại, tiếp tục thêm mới
                        // Truy vấn SQL để lấy id của dòng cuối cùng
                        String getIdQuery = "SELECT id FROM dictionary ORDER BY id DESC LIMIT 1";
                        try (PreparedStatement getIdStatement = connection.prepareStatement(getIdQuery);
                             ResultSet idResultSet = getIdStatement.executeQuery()) {
                            int lastId = 0;
                            if (idResultSet.next()) {
                                lastId = idResultSet.getInt("id");
                            }

                            // Tính toán id mới
                            int newId = lastId + 1;

                            // Truy vấn SQL để thêm dữ liệu vào cơ sở dữ liệu với id mới
                            String sqlQuery = "INSERT INTO dictionary (id, target, definition) VALUES (?, ?, ?)";
                            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                                preparedStatement.setInt(1, newId);
                                preparedStatement.setString(2, target);
                                preparedStatement.setString(3, definition);

                                // Thực hiện truy vấn
                                int rowsAffected = preparedStatement.executeUpdate();

                                if (rowsAffected > 0) {
                                    notifyImg.setImage(new Image(getClass().getResource("image/checked.png").toURI().toString()));
                                    notification.setText("The word '" + target + "' has been added.");
                                    System.out.println("The word '" + target + "' has been added.");
                                } else {
                                    notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
                                    notification.setText("The word '" + target + "' cannot be added.");
                                    System.out.println("The word '" + target + "' cannot be added.");
                                }
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    pane.setVisible(true);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insertFromSQL(); // Cập nhật lại database
    }

    public void handleUpdateButton() throws URISyntaxException {
        String newTarget = searchField.getText();
        String newTargetDefinition = newDefinition.getText();
        if (newTargetDefinition.isEmpty()) {
            notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
            notification.setText("New definition is empty");
            pane.setVisible(true);
        }
        if (!newTarget.isEmpty() && !newTargetDefinition.isEmpty()) {
            updateDefinition(newTarget, newTargetDefinition);
        }
        wordTargetDefinition.clear();
        newDefinition.clear();
    }

    public void updateDefinition(String target, String newDefinition) {
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection(url, username, password);

            // Truy vấn SQL để lấy definition cũ
            String getDefinitionQuery = "SELECT definition FROM dictionary WHERE target = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(getDefinitionQuery)) {
                preparedStatement.setString(1, target);

                // Thực hiện truy vấn SELECT
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String oldDefinition = resultSet.getString("definition");

                        // Truy vấn SQL để cập nhật definition mới
                        String updateDefinitionQuery = "UPDATE dictionary SET definition = ? WHERE target = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateDefinitionQuery)) {
                            updateStatement.setString(1, newDefinition);
                            updateStatement.setString(2, target);

                            // Thực hiện truy vấn UPDATE
                            int rowsAffected = updateStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                notifyImg.setImage(new Image(getClass().getResource("image/checked.png").toURI().toString()));
                                notification.setText("The new definition of the word '" + target + "' has been updated");
                                System.out.println("The new definition of the word '" + target + "' has been updated");
                            } else {
                                notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
                                notification.setText("The word '" + target + "' could not be found or the new definition could not be updated");
                                System.out.println("The word '" + target + "' could not be found or the new definition could not be updated");
                            }
                            pane.setVisible(true);
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        notification.setText("The word '" + target + "' was not found.");
                        System.out.println("The word '" + target + "' was not found.");
                    }
                }
            }
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insertFromSQL(); // Cập nhật lại database
    }

    public void handleDeleteButton() throws URISyntaxException {
        String newTarget = searchField.getText();
        if (!newTarget.isEmpty()) {
            notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
            deleteEntryByTarget(newTarget);
            pane.setVisible(true);
        }
        wordTargetDefinition.clear();
        newDefinition.clear();
    }

    public void deleteEntryByTarget(String target) {
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection connection = DriverManager.getConnection(url, username, password);

            // Truy vấn SQL để xóa dòng dựa trên giá trị target
            String sqlQuery = "DELETE FROM dictionary WHERE target = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, target);

                // Thực hiện truy vấn DELETE
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    notifyImg.setImage(new Image(getClass().getResource("image/checked.png").toURI().toString()));
                    notification.setText("The word '" + target + "' has been removed.");
                    System.out.println("Dòng có target '" + target + "' đã được xóa khỏi cơ sở dữ liệu.");
                } else {
                    notifyImg.setImage(new Image(getClass().getResource("image/unchecked.png").toURI().toString()));
                    notification.setText("The word '" + target + "' was not found.");
                    System.out.println("The word '" + target + "' was not found.");
                }
                pane.setVisible(true);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insertFromSQL(); // Cập nhật lại database
        wordMap.remove(target);
    }

    @FXML
    public void Back() throws IOException {
        switchScene("word_search.fxml", right);
    }
}
