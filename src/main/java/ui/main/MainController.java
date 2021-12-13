package ui.main;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojo.Issue;
import pojo.User;
import services.IssueService;
import utils.AlertUtils;
import utils.Bundle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML JFXNodesList nodesList;
    @FXML JFXNodesList nodesListSettings;
    @FXML JFXNodesList nodesListLanguage;
    @FXML JFXNodesList nodesListTheme;
    @FXML JFXButton logoutButton;
    @FXML Tab tabBookIssue,tabBook,tabUser;
    @FXML JFXTreeTableView bookIssueTTV,bookTTV,userTTV;
    @FXML Label lblTotal;
    ObservableList<Issue> issues;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nodesList.addAnimatedNode(nodesListSettings);
        nodesListSettings.addAnimatedNode(nodesListLanguage);
        nodesListSettings.addAnimatedNode(nodesListTheme);

        JFXTreeTableColumn<Issue, String> issueId = new JFXTreeTableColumn<>("Issue ID");
        issueId.setPrefWidth(100);
        issueId.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getId())));


        JFXTreeTableColumn<Issue, String> userId = new JFXTreeTableColumn<>("User ID");
        userId.setPrefWidth(100);
        userId.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getUserId())));

        JFXTreeTableColumn<Issue, String> date = new JFXTreeTableColumn<>("Issue Date");
        date.setPrefWidth(200);
        date.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getDate())));

        JFXTreeTableColumn<Issue, String> dueDate = new JFXTreeTableColumn<>("Return Due Date");
        dueDate.setPrefWidth(200);
        dueDate.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getReturnDueDate())));

        IssueService s = new IssueService();
        issues = FXCollections.observableArrayList(s.getIssues());
        lblTotal.setText("Total: "+ issues.size());
        final TreeItem<Issue> root = new RecursiveTreeItem<>(issues, RecursiveTreeObject::getChildren);
        bookIssueTTV.getColumns().setAll(issueId, userId, date, dueDate);
        bookIssueTTV.setRoot(root);
        bookIssueTTV.setShowRoot(false);

        bookIssueTTV.expandedItemCountProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                lblTotal.setText("Total: "+ t1);
                if ((int)t1==0) {
                    Label label = new Label(Bundle.getString("main.tableview.empty"));
                    label.setStyle("-fx-font-size: 16; -fx-text-fill: -fx-text;");
                    bookIssueTTV.setPlaceholder(label);
                }
            }
        });
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

    public void deleteHandler(ActionEvent event) {
        TreeItem<Issue> selectedItem = ((TreeItem<Issue>) bookIssueTTV.getSelectionModel().getSelectedItem());
        ((TreeItem<Issue>) bookIssueTTV.getSelectionModel().getSelectedItem()).getParent().getChildren().remove(selectedItem);

    }

}
