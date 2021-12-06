package ui.signup;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.regex.*;

import com.jfoenix.controls.*;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Labeled;
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

import java.io.Serializable;

import static javafx.scene.input.KeyCode.V;


public class SignUpController implements Serializable, Initializable {

    @FXML private JFXButton saveButton;
    @FXML private Label lblErrorName;
    @FXML private Label lblErrorId;
    @FXML private Label lblErrorPw;
    @FXML private Label lblErrorRePw;
    @FXML private Label lblErrorStId;
    @FXML private Label lblErrorMb;
    @FXML private Label lblErrorEmail;
    @FXML private JFXTextField Id;
    @FXML private JFXPasswordField txtPw;
    @FXML private JFXPasswordField repassword;
    @FXML private JFXTextField studentId;
    @FXML private JFXTextField mobile;
    @FXML private JFXCheckBox checkTerms;
    @FXML private JFXTextField email;
    @FXML private JFXTextField name;
    @FXML private JFXDatePicker birthday;

    private static boolean validSignUp=false;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private int checkUsername(String s){
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        if (s.indexOf(" ")!=-1){
            return 1;
        }
        else if (m.find()){
            return 2;
        }
        else if (s.length()>20){
            return 3;
        }
        return 0;
    }
    private boolean checkPassword(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }

    private boolean validStudentId(String stdid){
        if (stdid.length()==10){
            Pattern digit = Pattern.compile("[0-9]");
            Matcher hasDigit = digit.matcher(stdid);
            return hasDigit.find();
        }
        else return false;
    }

    private boolean validMobile(String mb){
        if (mb.indexOf("00")==0){
            return false;
        }
        else if (mb.indexOf("0")==0){
            mb=mb.substring(1,mb.length());
            mobile.setText(mb);
            return validMobile(mb);
        }
        else if (mb.length()==9){
            Pattern digit = Pattern.compile("[0-9]");
            Matcher hasDigit = digit.matcher(mb);
            return hasDigit.find();
        }

        return false;
    }

    public static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public static String upperCaseFirstLetter(String s) {
        String firstLetter = s.substring(0,1);
        String secondLetter = s.substring(1,s.length());
        firstLetter.toUpperCase();
        secondLetter.toLowerCase();
        return firstLetter+secondLetter;
    }
    public static String styleString(String s){
        String ch[]=s.split("\\s");
        s="";
        for (String i:ch){
            s+=upperCaseFirstLetter(i)+" ";
        }
        return s;
    }
    public static boolean validName(String Name){
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasDigit = digit.matcher(Name);
        Matcher hasSpecial = special.matcher(Name);
        return hasDigit.find() && hasSpecial.find();
    }
    String str = "";
    public boolean validSignUp(){
        str="";
        VBox vbox = new VBox();
        vbox.getChildren().stream().map(Label.class::cast).map(Label::getText).forEach(text-> str.concat(text));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(str);
        alert.show();
        return str.equals("");
    }

    @FXML
    public void signup() {
        //Dang suy nghi phuong phap toi uu de check
        //if (validSignUp)
        //Cái này để test hàm Insert User vào database
        if(validSignUp())
        {
            /*User user = new User(Id.getText(),txtPw.getText(),1,name.getText(),java.sql.Date.valueOf(birthday.getValue()),
                    mobile.getText(),email.getText(),studentId.getText());
            UserService userService = new UserService();
            userService.addUser(user);*/
            AlertUtils.showConfirmAlert("signup.success.title","signup.success.content");
            //do sothing
            Stage stage = (Stage) saveButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        } else {
            AlertUtils.showErrorAlert("signup.fail.title","signup.fail.content");
        }
    }

    public void clearAllFields() {
        name.setText("");
        Id.setText("");
        txtPw.setText("");
        repassword.setText("");
        birthday.setValue(null);
        studentId.setText("");
        mobile.setText("");
        email.setText("");
        checkTerms.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkTerms.selectedProperty().addListener((observableValue, oldValue, newValue)
                -> saveButton.setDisable(!newValue));

        /*name.textProperty().addListener((observableValue, oldString, newString) -> {
            if (newString.equals(""))
                lblErrorName.setText("");
        });*/
        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
               /* String Name=name.getText();
                if (!validName(Name) && Name!=""){
                    lblErrorName.setText(Bundle.getString("invalid.name"));
                }
                else lblErrorName.setText(Bundle.getString("invalid.input.null"));*/
            }
        });
        txtPw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String pw=txtPw.getText();
                if (!checkPassword(pw) && pw!=""){
                    lblErrorPw.setText(Bundle.getString("invalid.password"));
                    lblErrorPw.setMinHeight(30);
                }
                else
                if (txtPw.getText()=="")
                {
                    lblErrorPw.setText(Bundle.getString("invalid.input.null"));
                    lblErrorPw.setMinHeight(Region.USE_COMPUTED_SIZE);
                }
                else {
                    lblErrorPw.setText("");
                    lblErrorPw.setMinHeight(Region.USE_COMPUTED_SIZE);
                }
            }
        });
        repassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String password=txtPw.getText();
                String repas=repassword.getText();
                if (password.equals(repas)){
                    lblErrorRePw.setText("");
                }
                else
                if (repas==""){
                    lblErrorRePw.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorRePw.setText(Bundle.getString("invalid.rePassword"));
            }
        });
        Id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String txt= Id.getText();
                int check=checkUsername(txt);
                if (check==1){
                    lblErrorId.setText(Bundle.getString("invalid.accountId.space"));
                }
                else if (check==2){
                    lblErrorId.setText(Bundle.getString("invalid.accountId.character"));
                }
                else if (check==3){
                    lblErrorId.setText(Bundle.getString("invalid.accountId.long"));
                }
                else
                if (txt== ""){
                    lblErrorId.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorId.setText("");
            }
        });
        studentId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String stdid= studentId.getText();
                if (validStudentId(stdid)==false && stdid!=""){
                    lblErrorStId.setText(Bundle.getString("invalid.studentId"));
                }
                else
                if (stdid=="")
                {
                    lblErrorStId.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorStId.setText("");
            }
        });
        mobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String mb=mobile.getText();
                if (validMobile(mb)==false && mb!=""){
                    lblErrorMb.setText(Bundle.getString("invalid.mobile"));
                }
                else
                if (mb=="")
                {
                    lblErrorMb.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorMb.setText("");
            }
        });
        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String validemail=email.getText();
                if (!validEmail(validemail) && validemail!=""){
                    lblErrorEmail.setText(Bundle.getString("invalid.email"));
                }
                else
                if (validemail=="")
                {
                    lblErrorEmail.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorEmail.setText("");
            }
        });
    }
}
