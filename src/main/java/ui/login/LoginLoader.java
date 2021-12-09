package ui.login;

import utils.AlertUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/addbook.fxml"));
        primaryStage.setTitle("Library Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        primaryStage.setOnCloseRequest((e) -> {
            if (!AlertUtils.showConfirmAlert("alert.close.title", "alert.close.content"))
                e.consume();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


}
