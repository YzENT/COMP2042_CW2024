module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    exports com.example.demo.Controller;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.ActorsLogic to javafx.fxml;
    opens com.example.demo.Actor.Plane to javafx.fxml;
    opens com.example.demo.Actor.WeaponProjectiles to javafx.fxml;
    opens com.example.demo.ImageEntities to javafx.fxml;
    opens com.example.demo.Screens to javafx.fxml;
}