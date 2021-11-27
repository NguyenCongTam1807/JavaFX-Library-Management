package ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.Serializable;

public class LoginController implements Serializable {

    @FXML private JFXTextField txtId, txtPw;
    @FXML private JFXButton btnLogin, btnCancel;
    @FXML
    public void loginHandler() {

    }
    @FXML
    public void cancelHandler() {

    }
}
