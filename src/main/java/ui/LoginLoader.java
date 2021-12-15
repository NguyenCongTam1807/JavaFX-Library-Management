package ui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.util.Duration;
import utils.AlertUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Node;
import pojo.User;
import services.UserService;
import utils.Bundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import utils.Context;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class LoginLoader extends Application implements Serializable, Initializable {

    @FXML private Label lblError, lblCredit;
    @FXML private JFXTextField txtId;
    @FXML private JFXPasswordField txtPw;
    @FXML private JFXButton btnLogin, btnSignUp;
    User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Library Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest((e) -> {
            if (!AlertUtils.showConfirmAlert("alert.close.title", "alert.close.content"))
                e.consume();
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Context.getInstance().setLoginLoader(this);

        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
                setInterpolator(Interpolator.LINEAR);
                setCycleCount(Animation.INDEFINITE);
            }

            @Override
            protected void interpolate(double frac) {
                List<Stop> stops = Arrays.asList(new Stop(0, Color.RED), new Stop((double)1/7, Color.ORANGE),
                        new Stop((double)2/7, Color.YELLOW), new Stop((double)3/7, Color.GREEN), new Stop((double)4/7, Color.BLUE),
                        new Stop((double)5/7, Color.INDIGO), new Stop((double)6/7, Color.VIOLET), new Stop(1, Color.RED));
                lblCredit.setTextFill(new LinearGradient(frac-1,0,frac,1,true, CycleMethod.REPEAT,stops));
            }
        };
        animation.play();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void loginHandler(ActionEvent event) {
        UserService userService=new UserService();
        String id = txtId.getText();
        String pw = txtPw.getText();
        user=userService.getUser(id,pw);
        if (user!=null){
            Parent root;
            try{
                String resourcePath;
                if (user.getStudentId()==null||user.getStudentId().isEmpty())
                    resourcePath = "/fxml/main_librarian.fxml";
                else
                    resourcePath = "/fxml/main_user.fxml";
                root=FXMLLoader.load(getClass().getResource(resourcePath));
                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(new Scene(root,1000, 700));
                stage.centerOnScreen();
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            AlertUtils.showErrorAlert("alert.loginFail.title","alert.loginFail.content");
        }
    }

    @FXML
    public void signUpHandler(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
            Stage stage = new Stage();
            stage.setTitle(Bundle.getString("signup.title"));
            stage.setScene(new Scene(root));
            Stage primaryStage = (Stage) btnSignUp.getScene().getWindow();
            stage.initOwner(primaryStage);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            /**Set new scene in the same window**/
            /*Stage primaryStage = (Stage) btnSignUp.getScene().getWindow();
            primaryStage.getScene().setRoot(root);*/
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
