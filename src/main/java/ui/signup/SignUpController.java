package ui.signup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import  javafx.fxml.LoadException;

import java.io.Serializable;

public class SignUpController implements Serializable {
    /*@FXML private JFXToggleButton toggle;
    @FXML private Label lblError;
    @FXML private JFXTextField txtId;
    @FXML private JFXPasswordField txtPw;
    @FXML private JFXButton btnLogin, btnSignUp;*/
    //@FXML private JFXButton saveButton;
    //@FXML private JFXTextField Id;

    @FXML
    public void checkId() {
        /*String txt= Id.getText();
        Id.setText("a");*/
        /*Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Id.toString());
        alert.show();*/
    }

    @FXML
    public void btnCreateHandler() {

    }
    @FXML
    public boolean validate() {

        return true;
    }
}
