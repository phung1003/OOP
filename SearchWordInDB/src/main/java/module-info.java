module com.example.searchwordindb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.searchwordindb to javafx.fxml;
    exports com.example.searchwordindb;
}