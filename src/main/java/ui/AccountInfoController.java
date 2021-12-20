package ui;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.*;

import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import pojo.User;
import services.UserService;
import utils.AlertUtils;
import utils.Bundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import utils.Context;
import ui.SignUpController;

import java.io.Serializable;

public class AccountInfoController implements Serializable,Initializable {
    @FXML JFXTextField txtEmail,txtMobile,txtName,txtUserName,txtBirthday,txtStdId;
    @FXML JFXButton btnChangePw,btnSave,btnCancel;
    @FXML Label lblStatus,lblErrorEmail,lblErrorStId,lblErrorMb;
    SignUpController check;

    private User loggedInUser = Context.getInstance().getLoginLoader().getLoggedInUser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtName.setText(loggedInUser.getName());
            txtUserName.setText(loggedInUser.getAccountId());
            txtMobile.setText(loggedInUser.getPhoneNumber());
            txtBirthday.setText(loggedInUser.getBirthday().toString());
            txtStdId.setText(loggedInUser.getStudentId());
            txtStdId.setVisible(loggedInUser.getStudentId()==""?true:false);
            txtEmail.setText(loggedInUser.getEmail());
            lblStatus.setText(Bundle.getString("info.user.status."+loggedInUser.getStatus()));
            lblStatus.getStyleClass().add(loggedInUser.getStatus()==0?"error-small":"others-small");
            btnSave.setDisable(loggedInUser.getStatus()==0?true:false);
        }catch (Exception e){}
        txtEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorEmail.setText(!check.validEmail(t1)&& t1!=""?Bundle.getString("invalid.email"):(t1==""?Bundle.getString("invalid.input.null"):""));
            }
        });
        txtMobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorMb.setText(!check.validMobile(t1,txtMobile  ) && t1!="" ?Bundle.getString("invalid.mobile"):(t1==""?Bundle.getString("invalid.input.null"):""));
            }
        });
        btnCancel.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Parent root;
                try {
                    Stage stage = (Stage) btnCancel.getScene().getWindow();
                    stage.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnChangePw.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/fxml/change_password.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle(Bundle.getString("signup.title"));
                    stage.setScene(new Scene(root));
                    Stage primaryStage = (Stage) btnChangePw.getScene().getWindow();
                    stage.initOwner(primaryStage);
                    stage.setResizable(false);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSave.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lblErrorMb.getText()=="" && lblErrorEmail.getText()==""){
                    UserService us=new UserService();
                    if(us.changeInforUser(loggedInUser,txtMobile.getText(),txtEmail.getText())){
                        loggedInUser.setEmail(txtEmail.getText());
                        loggedInUser.setPhoneNumber(txtMobile.getText());
                        AlertUtils.showInfoAlert("change.success.title", "change.success.content");
                        Stage stage = (Stage) btnSave.getScene().getWindow();
                        stage.close();
                    }
                }
                else
                    AlertUtils.showErrorAlert("change.fail.title","change.fail.content");            }
        });
    }
}
