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
//import static jdk.internal.logger.DefaultLoggerFinder.SharedLoggers.system;


public class SignUpController implements Serializable, Initializable {

    @FXML private JFXButton saveButton,cancelButton;
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
    @FXML private VBox container;
    @FXML private Label lblErrorBirthday;


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static int checkUsername(String s){
        s=unAccent(s);
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
    public static boolean checkPassword(String password)
    {
        if(password.length()>=8)
        {
            return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");

        }
        else
            return false;

    }

    private boolean validBirthday(LocalDate birthday){
        int age=LocalDate.now().getYear()-birthday.getYear();
        if(age>=18&&age<=130)
            return true;
        return false;
    }

    private boolean validStudentId(String stdid){
        if (stdid.length()==10){
            return stdid.matches("-?\\d+?");
        }
        else return false;
    }

   public static boolean validMobile(String mb, JFXTextField $mobile){
        if (mb.indexOf("00")==0){
            return false;
        }
        else if (mb.indexOf("0")==0){
            mb=mb.substring(1);
            $mobile.setText(mb);
            return validMobile(mb,$mobile);
        }
        else if (mb.length()==9){
            return mb.matches("-?\\d+?");
        }

        return false;
    }

    public static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public static String upperCaseFirstLetter(String s) {
        s=s.toLowerCase();
        String firstLetter = s.substring(0,1);
        String secondLetter = s.substring(1);
        return firstLetter.toUpperCase()+secondLetter;
    }

    public static String removeCharAt(String s, int pos,int i) {
        return s.substring(0, pos) + s.substring(pos + i);
    }
    //   le dang   thanh dat
    public static String styleString(String s){
        s=s.trim();
        while (s.indexOf("  ")!=-1){
            s=removeCharAt(s,s.indexOf("  ")+1,1);
        }
        String ch[]=s.split("\\s");
        s="";
        for (String i:ch){
            s+=upperCaseFirstLetter(i)+" ";
        }
        return s.substring(0,s.length()-1);
    }
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        // return pattern.matcher(temp).replaceAll("");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "");
    }

    public static boolean validName(String Name){
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}:;'.,/\\[\\]\"~-]");
        Matcher hasDigit = digit.matcher(Name);
        Matcher hasSpecial = special.matcher(Name);
        return hasDigit.find() || hasSpecial.find();
    }

    boolean valid = true;
    public boolean validSignUp(){
        valid=true;
        container.getChildren().stream().filter(node -> node.getClass()==Label.class).map(Label->((Label) Label).getText()).forEach(text -> {
            if(text!="") valid=false;
        });
        container.getChildren().stream().filter(node -> node.getClass()==JFXTextField.class).map(JFXTextField->((JFXTextField) JFXTextField).getText()).forEach(text->
        {
            if (text=="") valid=false;
        });
        container.getChildren().stream().filter(node -> node.getClass()==JFXDatePicker.class).map(JFXDatePicker->((JFXDatePicker) JFXDatePicker).getValue()).forEach(date->
        {
            if(date==null) valid=false;
        });
        return valid ;
    }

    public void signup() {
        if(validSignUp())
        {
            User user = new User(Id.getText(),txtPw.getText(),1,name.getText(),java.sql.Date.valueOf(birthday.getValue()),
                    mobile.getText(),email.getText(),studentId.getText());
            UserService userService = new UserService();
            if(userService.addUser(user)) {
                AlertUtils.showInfoAlert("signup.success.title", "signup.success.content");
                //do something
                Stage stage = (Stage) saveButton.getScene().getWindow();
                // do what you have to do
                stage.close();
            }
            else
                AlertUtils.showErrorAlert("alert.signUpFail.title","alert.signUpFail.content");
        } else {
            AlertUtils.showErrorAlert("signup.fail.title","signup.fail.content");
        }
    }

    public void clearAllFields() {
        container.getChildren().stream().filter(node -> node.getClass()==JFXTextField.class).map(JFXTextField->((JFXTextField) JFXTextField)).forEach($field->{
            $field.setText("");
        });
        container.getChildren().stream().filter(node -> node.getClass()==JFXPasswordField.class).map(JFXPasswordField ->((JFXPasswordField) JFXPasswordField)).forEach($pass->{
            $pass.setText("");
        });
        container.getChildren().stream().filter(node -> node.getClass()==Label.class).map(Label ->((Label) Label)).forEach($text->{
            $text.setText("");
        });
        birthday.setValue(null);
        checkTerms.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Context.getInstance().setSignUpController(this);
        checkTerms.selectedProperty().addListener((observableValue, oldValue, newValue)
                -> saveButton.setDisable(!newValue));

        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorName.setText(validName(unAccent(t1)) && t1!=""?Bundle.getString("invalid.name"):(t1==""?Bundle.getString("invalid.input.null"):""));
            }
        });
        name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (name.getText()!="")
                        name.setText(styleString(name.getText()));
                }
            }
        });
        txtPw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorPw.setText(!checkPassword(t1) && t1 != "" ? Bundle.getString("invalid.password") : (t1 == "" ? Bundle.getString("invalid.input.null") : ""));
                lblErrorPw.setMinHeight(!checkPassword(t1) && t1 != "" ? 30 : Region.USE_COMPUTED_SIZE);
                String temp=repassword.getText();
                repassword.setText("");
                repassword.setText(temp);
            }
        });
        repassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorRePw.setText(!t1.equals(txtPw.getText()) && t1!=""?Bundle.getString("invalid.rePassword"): (t1 == "" ? Bundle.getString("invalid.input.null") : ""));

            }
        });
        Id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    int c=checkUsername(t1);
                lblErrorId.setText(c==1?Bundle.getString("invalid.accountId.space"):(c==2?Bundle.getString("invalid.accountId.character"):(c==3?Bundle.getString("invalid.accountId.long"):(t1==""?Bundle.getString("invalid.input.null"):""))));
            }
        });

        birthday.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                lblErrorBirthday.setText(t1!=null?(validBirthday(t1)?"":Bundle.getString("invalid.birthday")):"");
            }
        });


        studentId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorStId.setText(validStudentId(t1)==false && t1!=""?Bundle.getString("invalid.studentId"):(t1==""?Bundle.getString("invalid.input.null"):""));
            }
        });
        mobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorMb.setText(validMobile(t1,mobile)==false && t1!=""?Bundle.getString("invalid.mobile"):(t1==""?Bundle.getString("invalid.input.null"):""));
            }
        });
        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lblErrorEmail.setText(!validEmail(t1) && t1!=""?Bundle.getString("invalid.email"):(t1==""?Bundle.getString("invalid.input.null"):""));
            }
        });
        saveButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                signup();
            }
        });
        cancelButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearAllFields();
            }
        });
    }
}
