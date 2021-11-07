module com.example.fxapps {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.fxapps.knightmove to javafx.fxml;
    exports com.example.fxapps.knightmove;
    exports com.example.fxapps.gameoflife;
}