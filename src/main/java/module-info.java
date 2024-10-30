module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.Initialize;
    opens com.example.demo.Levels to javafx.fxml;
}