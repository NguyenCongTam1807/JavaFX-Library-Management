package ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.AlertUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML JFXNodesList nodesList;
    @FXML JFXNodesList nodesListSettings;
    @FXML JFXNodesList nodesListLanguage;
    @FXML JFXNodesList nodesListTheme;
    @FXML JFXButton logoutButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nodesList.addAnimatedNode(nodesListSettings);
        nodesListSettings.addAnimatedNode(nodesListLanguage);
        nodesListSettings.addAnimatedNode(nodesListTheme);
    }

    @FXML
    public void logoutHandle(ActionEvent event){
        if(AlertUtils.showConfirmAlert("alert.logOut.title","alert.logOut.content")){
            Parent root;
            try{
                root=FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(new Scene(root,400, 386));
                stage.centerOnScreen();
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
