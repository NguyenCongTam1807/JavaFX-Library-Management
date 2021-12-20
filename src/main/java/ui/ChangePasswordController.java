package ui;

import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.*;

import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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



public class ChangePasswordController  implements Serializable,Initializable {
    @FXML JFXPasswordField txtPw,txtNewPw,txtReNewPw;
    @FXML Label lblErrorPw,lblErrorNewPw,lblErrorRePw;
    @FXML JFXButton btnCancel,btnSave;
    SignUpController check;
    private User loggedInUser = Context.getInstance().getLoginLoader().getLoggedInUser();
    private boolean validInfo(){
        return lblErrorPw.getText()=="" && lblErrorNewPw.getText()=="" && lblErrorRePw.getText()=="";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorPw.setText(t1.equals(loggedInUser.getPassword())?"":Bundle.getString("password.invalid") );
            }
        });
        txtNewPw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorNewPw.setText(!check.checkPassword(t1) && t1 != "" ? Bundle.getString("invalid.password") : (t1 == "" ? Bundle.getString("invalid.input.null") : ""));
                lblErrorNewPw.setMinHeight(!check.checkPassword(t1) && t1 != "" ? 30 : Region.USE_COMPUTED_SIZE);
                String temp=txtReNewPw.getText();
                txtReNewPw.setText("");
                txtReNewPw.setText(temp);
            }
        });
        txtReNewPw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorRePw.setText(!t1.equals(txtNewPw.getText()) && t1!=""?Bundle.getString("invalid.rePassword"): (t1 == "" ? Bundle.getString("invalid.input.null") : ""));
            }
        });
        btnSave.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lblErrorPw.getText()=="" && lblErrorNewPw.getText()=="" && lblErrorRePw.getText()=="" && txtNewPw.getText()!="" && txtPw.getText()!="" && txtReNewPw.getText()!=""){
                    UserService us=new UserService();
                    if(us.changePass(loggedInUser,txtNewPw.getText())) {
                        loggedInUser.setPassword(txtNewPw.getText());
                        AlertUtils.showInfoAlert("change.success.title", "change.success.content");
                        Stage stage = (Stage) btnSave.getScene().getWindow();
                        stage.close();
                    }
                }
                else{
                    AlertUtils.showErrorAlert("change.fail.title","change.fail.content");
                }
            }
        });
        btnCancel.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
            }
        });
    }
}
