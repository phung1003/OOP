package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordSearchController extends SceneController implements Initializable{

    protected static final String url = "jdbc:mysql://localhost:3306/OOP";
    protected static final String username = "root";
    protected static final String password = "vttp1003";

    @FXML
    protected TextField searchField;

    @FXML
    private Button lookUpButton;

    @FXML
    protected ListView<String> suggestionList;

    @FXML
    protected TextArea wordTargetDefinition;

    @FXML
    private Button backButton;

    protected static SortedMap<String, Word> wordMap = new TreeMap<String, Word>();
    private static final int MAX_SUGGESTIONS = 50;

    public void insertFromSQL(){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM dictionary";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String column1 = resultSet.getString("target");
                String column2 = resultSet.getString("definition");
                column2 = handleDefinitionString(column2);
                // insert data into wordList
                Word newWord = new Word(column1, column2);
                wordMap.put(column1, newWord);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleLookUpButtonSearch() {
        suggestionList.setVisible(false);  // Ẩn ListView khi không có từ gợi ý
        String keyWord = searchField.getText();
        String definition = dictionaryLookup(keyWord);
        wordTargetDefinition.setText(definition);
    }

    public String handleDefinitionString(String inputString) {
        // Xóa <I><Q>@ ở đầu chuỗi
        String withoutStartTag = inputString.replaceFirst("<I><Q>", "");

        // Xóa </Q></I> ở cuối chuỗi
        String withoutEndTag = withoutStartTag.replaceAll("</Q></I>", "");

        String withoutChar = withoutEndTag.replaceAll("@", "");

        // Xóa <br /> và thêm xuống dòng
        String withoutBr = withoutChar.replaceAll("<br />", "\n");

        // Thay thế dấu "+" bằng ":"
        String finalResult = withoutBr.replace("+", ":");

        return finalResult;
    }


    public String dictionaryLookup(String keyWord) {
        //Scanner scanner = new Scanner(System.in);
        //String keyWord = scanner.nextLine();
        if (wordMap.get(keyWord) == null) {
            System.out.println("Not found!");
            return "Not found!";
        }
        return wordMap.get(keyWord).getWordExplain();
    }

    // Cập nhật danh sách từ gợi ý dựa trên từ khóa nhập vào
    private void updateSuggestions(String keyword) {
        if (keyword.length() > 0) {
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

    @FXML
    private void back() throws IOException {
        switchScene("FirstScene.fxml", right);
    }

    @FXML
    private void toFixScene() throws IOException {
        switchScene("AddUpdateRemove.fxml", left);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    protected void init() {
        if (wordMap.isEmpty()) {
            insertFromSQL();
        }
        
        suggestionList.setVisible(false);  // Ẩn ListView khi không có từ gợi ý
        suggestionList.setPickOnBounds(false);
        // Xử lý sự kiện khi người dùng nhập vào ô tìm kiếm
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateSuggestions(newValue);
        });

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
}
