module JavaFX.Library.Manager {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires java.sql;

    //opens ui.login;
    opens ui.login;
    exports ui.addbook;
}