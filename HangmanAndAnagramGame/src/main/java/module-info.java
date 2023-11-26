module com.example.testchangescene {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testchangescene to javafx.fxml;
    exports com.example.testchangescene;
}