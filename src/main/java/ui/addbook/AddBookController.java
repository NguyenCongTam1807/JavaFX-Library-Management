package ui.addbook;

import javafx.beans.value.ChangeListener;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import ui.signup.SignUpController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import configs.JdbcUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Bundle;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddBookController implements Initializable {
    @FXML private VBox vbox;
    @FXML private JFXButton btnAddBook, btnCancel;
    @FXML private JFXTextField txtAmount,txtTitle,txtSummary,txtGenre,txtPublisher, txtPublishedYear, txtAuthor,txtIdBook;
    @FXML private Label lblErrorAmount,lblErrorSummary,lblErrorIdBook, lblErrorPublisher,lblErrorGenre,lblErrorPublishedYear,lblErrorAuthor, lblErrorTitle;

    public boolean validYear(String s) {
        if (s.length()==4){
            Pattern digit = Pattern.compile("[0-9]");
            Matcher hasDigit = digit.matcher(s);
            return hasDigit.find();
        }
        else return false;
    }

    public static String clearSpace(String s){
        s=s.trim();
        while (s.indexOf("  ")!=-1){
            s=ui.signup.SignUpController.removeCharAt(s,s.indexOf("  ")+1,1);
        }
        return s;
    }

    public static boolean validMount(String s){
        Pattern digit = Pattern.compile("[0-9]");
        Matcher hasDigit = digit.matcher(s);
        return hasDigit.find();
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = JdbcUtils.getConn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        txtPublisher.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String Name=txtPublisher.getText();
                if (ui.signup.SignUpController.validName(ui.signup.SignUpController.unAccent(Name)) && Name!=""){
                    lblErrorPublisher.setText(Bundle.getString("invalid.name"));
                }
                else if (Name=="") {
                    lblErrorPublisher.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorPublisher.setText("");
            }
        });
        txtPublisher.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtPublisher.getText()!=""){
                        txtPublisher.setText(ui.signup.SignUpController.styleString(txtPublisher.getText()));
                    }
                }
            }
        });
        txtTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtTitle.getText()!=""){
                        txtTitle.setText(clearSpace(txtTitle.getText()));
                        lblErrorTitle.setText("");
                    }
                    else lblErrorTitle.setText(Bundle.getString("invalid.input.null"));
                }
            }
        });
        txtIdBook.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String txt= txtIdBook.getText();
                int check=ui.signup.SignUpController.checkUsername(txt);
                if (check==1){
                    lblErrorIdBook.setText(Bundle.getString("invalid.accountId.space"));
                }
                else if (check==2){
                    lblErrorIdBook.setText(Bundle.getString("invalid.accountId.character"));
                }
                else if (check==3){
                    lblErrorIdBook.setText(Bundle.getString("invalid.accountId.long"));
                }
                else
                if (txt== ""){
                    lblErrorIdBook.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorIdBook.setText("");
            }
        });
        txtAuthor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String Name=txtAuthor.getText();
                if (ui.signup.SignUpController.validName(ui.signup.SignUpController.unAccent(Name)) && Name!=""){
                    lblErrorAuthor.setText(Bundle.getString("invalid.name"));
                }
                else if (Name=="") {
                    lblErrorAuthor.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorAuthor.setText("");
            }
        });
        txtAuthor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtAuthor.getText()!="")
                        txtAuthor.setText(ui.signup.SignUpController.styleString(txtAuthor.getText()));
                }
            }
        });
        txtGenre.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtGenre.getText()!=""){
                        txtGenre.setText(clearSpace(txtGenre.getText()));
                        lblErrorGenre.setText("");
                    }
                    else lblErrorGenre.setText(Bundle.getString("invalid.input.null"));
                }
            }
        });
        txtSummary.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtSummary.getText()!=""){
                        txtSummary.setText(clearSpace(txtSummary.getText()));
                        lblErrorSummary.setText("");
                    }
                    else lblErrorSummary.setText(Bundle.getString("invalid.input.null"));
                }
            }
        });
        txtPublishedYear.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                if (validYear(txtPublishedYear.getText())){
                    int i=Integer.parseInt(txtPublishedYear.getText());
                    if (i>year || i<1000){
                        lblErrorPublishedYear.setText(Bundle.getString("invalid.years"));
                    }
                    else lblErrorPublishedYear.setText("");
                }
                else lblErrorPublishedYear.setText(Bundle.getString("invalid.years"));
            }
        });
        txtAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (validMount(txtAmount.getText())){
                    int i=Integer.parseInt(txtAmount.getText());
                    if (i<1){
                        lblErrorAmount.setText(Bundle.getString("invalid.amount.outRange"));
                    }
                    else lblErrorAmount.setText("");
                }
                else lblErrorAmount.setText(Bundle.getString("invalid.amount.null"));
            }
        });
    }
}
