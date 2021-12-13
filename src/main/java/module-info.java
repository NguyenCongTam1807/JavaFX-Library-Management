module JavaFX.Library.Manager {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;

    opens ui;
    exports ui;
}