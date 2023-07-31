module com.example.brickbreaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;
    opens com.example.brickbreaker to javafx.fxml;
    exports com.example.brickbreaker;
    exports com.example.brickbreaker.menu;
    opens com.example.brickbreaker.menu to javafx.fxml;
    opens assets.textures;
}