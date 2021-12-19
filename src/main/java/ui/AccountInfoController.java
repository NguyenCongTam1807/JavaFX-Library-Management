package ui;

import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.*;

import com.jfoenix.controls.*;
import javafx.event.EventHandler;
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

import java.io.Serializable;

public class AccountInfoController implements Serializable,Initializable {
    @FXML JFXTextField txtEmail,txtMobile,txtName,txtUserName,txtBirthday,txtStdId;
    @FXML JFXButton btnChangePw,btnSave,btnCancel;
    @FXML Label lblStatus,lblErrorEmail,lblErrorStId;

    private User loggedInUser = Context.getInstance().getLoginLoader().getLoggedInUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtName.setText(loggedInUser.getName());
            txtUserName.setText(loggedInUser.getAccountId());
            txtMobile.setText(loggedInUser.getPhoneNumber());
            txtBirthday.setText(loggedInUser.getBirthday().toString());
            txtStdId.setText(loggedInUser.getStudentId());
            System.out.println(txtStdId.getId());
            txtStdId.setVisible(loggedInUser.getStudentId()==""?true:false);
            txtEmail.setText(loggedInUser.getEmail());
            lblStatus.setText(Bundle.getString("info.user.status."+loggedInUser.getStatus()));
            lblStatus.getStyleClass().add(loggedInUser.getStatus()==0?"error-small":"others-small");
        }catch (Exception e){

        };
    }
}
