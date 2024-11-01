module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.Initialize;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.ActorsLogic to javafx.fxml;
    opens com.example.demo.ActorsLogic.WeaponProjectiles to javafx.fxml;
    opens com.example.demo.Actor to javafx.fxml;
}