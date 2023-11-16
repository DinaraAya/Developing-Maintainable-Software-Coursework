module brickGame {
    requires javafx.fxml;
    requires transitive javafx.controls;
    requires javafx.media;
    requires javafx.graphics;

    opens brickGame to javafx.fxml, javafx.graphics;
    exports brickGame;
}