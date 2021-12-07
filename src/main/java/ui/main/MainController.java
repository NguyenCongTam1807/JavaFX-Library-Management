package ui.main;

import com.jfoenix.controls.JFXNodesList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML JFXNodesList nodesList;
    @FXML JFXNodesList nodesListSettings;
    @FXML JFXNodesList nodesListLanguage;
    @FXML JFXNodesList nodesListTheme;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nodesList.addAnimatedNode(nodesListSettings);
        nodesListSettings.addAnimatedNode(nodesListLanguage);
        nodesListSettings.addAnimatedNode(nodesListTheme);
    }
}
