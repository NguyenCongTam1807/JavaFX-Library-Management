//package ui.login;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXPasswordField;
//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXToggleButton;
//import javafx.scene.Node;
//import pojo.User;
//import services.UserService;
//import utils.AlertUtils;
//import utils.Bundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.paint.Paint;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.io.Serializable;
//
//public class LoginController implements Serializable {
//
//    @FXML private JFXToggleButton toggle;
//    @FXML private Label lblError;
//    @FXML private JFXTextField txtId;
//    @FXML private JFXPasswordField txtPw;
//    @FXML private JFXButton btnLogin, btnSignUp;
//    @FXML
//    public void loginHandler(ActionEvent event) {
//        UserService userService=new UserService();
//        User user;
//        String id = txtId.getText().toString();
//        String pw = txtPw.getText().toString();
//        user=userService.getUser(id,pw,toggle.isSelected());
//        if (user!=null){
//            Parent root;
//            try{
//                root=FXMLLoader.load(getClass().getResource("/fxml/main2.fxml"));
//                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
//                stage.setResizable(false);
//                stage.setScene(new Scene(root,1000, 700));
//                stage.centerOnScreen();
//                stage.show();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            AlertUtils.showLoginFailAlert("alert.loginFail.title","alert.loginFail.content");
//        }
//    }
//
//
//    @FXML
//    public void signUpHandler(ActionEvent event) {
//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle(Bundle.getString("signup.title"));
//            stage.setScene(new Scene(root));
//            Stage primaryStage = (Stage) btnSignUp.getScene().getWindow();
//            stage.initOwner(primaryStage);
//            stage.setResizable(false);
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.show();
//            /**Set new scene in the same window**/
//            /*Stage primaryStage = (Stage) btnSignUp.getScene().getWindow();
//            primaryStage.getScene().setRoot(root);*/
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @FXML
//    public void toggleHandler() {
//        if (toggle.isSelected())
//            setStyle("login.toggle.librarian",toggle.getToggleLineColor());
//        else
//            setStyle("login.toggle.student",toggle.getUnToggleColor());
//    }
//    public void setStyle(String textBundle, Paint color) {
//        toggle.setText(Bundle.getString(textBundle));
//        toggle.setTextFill(color);
//    }
//}
