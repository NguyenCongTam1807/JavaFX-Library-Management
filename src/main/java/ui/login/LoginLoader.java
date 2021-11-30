package ui.login;

import configs.AlertUtils;
import configs.Bundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Library Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest((e) -> {
            if (!AlertUtils.showOnCloseAlert(Bundle.getString("alert.close.title"),
                    Bundle.getString("alert.close.content")))
                e.consume();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


}
