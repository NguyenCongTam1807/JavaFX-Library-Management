package ui.addbook;

import javafx.beans.value.ChangeListener;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import configs.JdbcUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utils.Bundle;
import utils.AlertUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddBookController implements Initializable {
    @FXML private VBox vbox;
    @FXML private JFXButton btnAddBook, btnClear;
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
    boolean valid;
    private boolean validInput(){
        valid=true;
        vbox.getChildren().stream().filter(node -> node.getClass()==Label.class).map(Label ->((Label) Label).getText()).forEach(text ->{
            if (text!="")
                valid=false;
        });
        vbox.getChildren().stream().filter(node -> node.getClass()==JFXTextField.class).map(JFXTextField ->((JFXTextField) JFXTextField).getText()).forEach(text ->{
           if (text=="")
               valid=false;
        });
        return valid;
    }

/*    @FXML*/
    /*private void btnClear() {

    }*/

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
        btnAddBook.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (validInput()){
                    //do something
                    if (true){
                        AlertUtils.showConfirmAlert("addBook.success.title","addBook.success.content");
                    }
                }
                else {
                    AlertUtils.showSignUpFailAlert("alert.addBookFail.title","alert.addBookFail.content");
                }
            }
        });
        btnClear.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               /* Label[] label=new Label[10];
                label.
                vbox.getChildren().stream().filter(node -> node.getClass()==Label.class).map(Label ->((Label) Label)).forEach(lab->label.Controls.add(lab));
                AlertUtils.showConfirmAlert("signup.success.title", "signup.success.content");*/
            }
        });
    }
}
