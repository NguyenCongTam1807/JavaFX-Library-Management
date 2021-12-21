package ui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.utils.JFXHighlighter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import pojo.Book;
import pojo.Issue;
import pojo.User;
import services.BookService;
import services.IssueDetailService;
import services.IssueService;
import services.UserService;
import utils.AlertUtils;
import utils.Bundle;
import utils.Context;
import utils.DateUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainUserController implements Initializable {
    @FXML private AnchorPane anchorPane;
    @FXML private JFXTabPane tabPane;
    @FXML private JFXNodesList nodesList;
    @FXML private JFXNodesList nodesListSettings;
    @FXML private JFXNodesList nodesListLanguage;
    @FXML private JFXNodesList nodesListTheme;
    @FXML private JFXButton logoutButton,infoButton;
    @FXML private Tab tabBookIssue,tabBook,tabUser;
    @FXML private JFXTreeTableView bookIssueTTV,bookTTV;
    @FXML private Label lblTotal;
    @FXML private JFXTextField txtSearch;

    private final User loggedInUser = Context.getInstance().getLoginLoader().getLoggedInUser();

    private final BookService bs = new BookService();
    private final UserService us = new UserService();
    private final IssueService is = new IssueService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Context.getInstance().setMainUserController(this);
        initMenu();
        initBookIssueTab();
        initBookTab();
        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            int total = 0;
            if (newTab==tabBookIssue)
                total = bookIssueTTV.expandedItemCountProperty().getValue();
            else
                total = bookTTV.expandedItemCountProperty().getValue();
            lblTotal.setText("Total: " + total);
            txtSearch.setText("");
        });

        /** Display "Empty" message if TableView is empty **/
        bookIssueTTV.expandedItemCountProperty().addListener((observableValue, oldNum, newNum) -> {
            lblTotal.setText("Total: "+ newNum);
            if ((int)oldNum* (int)newNum==0) {
                Label label = new Label(Bundle.getString("main.tableview.empty"));
                label.setStyle("-fx-font-size: 16; -fx-text-fill: -fx-text;");
                bookIssueTTV.setPlaceholder(label);
            }
        });
        bookTTV.expandedItemCountProperty().addListener((observableValue, oldNum, newNum) -> {
            lblTotal.setText("Total: "+ newNum);
            if ((int)oldNum* (int)newNum==0) {
                Label label = new Label(Bundle.getString("main.tableview.empty"));
                label.setStyle("-fx-font-size: 16; -fx-text-fill: -fx-text;");
                bookTTV.setPlaceholder(label);
            }
        });

        /** Clear row selection if clicked twice **/
        bookIssueTTV.setRowFactory((Callback<TreeTableView, TreeTableRow>) treeTableView -> {
            final TreeTableRow<Issue> row = new TreeTableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && bookIssueTTV.getSelectionModel().isSelected(index) && event.getClickCount()<=1 ) {
                    bookIssueTTV.getSelectionModel().clearSelection();
                    event.consume();
                }
            });
            return row;
        });
        bookTTV.setRowFactory((Callback<TreeTableView, TreeTableRow>) treeTableView -> {
            final TreeTableRow<Book> row = new TreeTableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && bookTTV.getSelectionModel().isSelected(index) && event.getClickCount()<=1 ) {
                    bookTTV.getSelectionModel().clearSelection();
                    event.consume();
                }
            });
            return row;
        });

        /** Double click event for TreeTableView rows **/

        bookIssueTTV.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1 &&
                    (event.getTarget() != null || ((VBox) event.getTarget()).getChildren().size() > 0)) {
                TreeItem<Issue> selectedIssue = (TreeItem<Issue>) bookIssueTTV.getSelectionModel().getSelectedItem();
                Parent root;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/book_return.fxml"));
                    loader.setControllerFactory(aClass -> {
                        if (aClass == BookReturnController.class) {
                            BookReturnController controller = new BookReturnController();
                            controller.getIssueInfo(selectedIssue.getValue().getId(),selectedIssue.getValue().getUserId(),
                                    selectedIssue.getValue().getDate(),selectedIssue.getValue().getReturnDueDate());
                            controller.setStage(new Stage());
                            return controller ;
                        } else {
                            try {
                                return aClass.getDeclaredConstructor().newInstance();
                            } catch (Exception exc) {
                                throw new RuntimeException(exc);
                            }
                        }
                    });
                    root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle(Bundle.getString("bookReturn.title"));
                    stage.setScene(new Scene(root));
                    Stage primaryStage = (Stage) lblTotal.getScene().getWindow();
                    stage.initOwner(primaryStage);
                    stage.setResizable(false);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bookTTV.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 &&
                    (event.getTarget() != null || ((AnchorPane) event.getTarget()).getChildren().size() > 0)) {

                //your code here
            }
        });


        /** Search bar **/
        txtSearch.textProperty().addListener((observableValue, oldVal, newVal) -> {
            int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
            switch (tabIndex) {
                case 0:
                    bookIssueTTV.setPredicate((Predicate<TreeItem<Issue>>) treeItem -> {
                        Issue i = treeItem.getValue();
                        Boolean flag = String.valueOf(i.getId()).contains(newVal)
                                || String.valueOf(i.getUserId()).contains(newVal)
                                || DateUtils.changeFormat(i.getDate(),"dd/MM/yyyy").contains(newVal)
                                || DateUtils.changeFormat(i.getReturnDueDate(),"dd/MM/yyyy").contains(newVal);
                        return flag;
                    });
                    break;
                default:
                    bookTTV.setPredicate((Predicate<TreeItem<Book>>) treeItem -> {
                        Book b = treeItem.getValue();
                        Boolean flag = String.valueOf(b.getId()).contains(newVal)
                                || String.valueOf(b.getId()).contains(newVal)
                                || String.valueOf(b.getPublishedYear()).contains(newVal)
                                || b.getTitle().contains(newVal)
                                || b.getGenre().contains(newVal)
                                || b.getPublisher().contains(newVal)
                                || b.getAuthor().contains(newVal);
                        return flag;
                    });
            }
        });
    }

    @FXML
    public void logoutHandle(ActionEvent event){
        if(AlertUtils.showConfirmAlert("alert.logOut.title","alert.logOut.content")){
            Stage stage1=(Stage)((Node) event.getSource()).getScene().getWindow();
            stage1.close();
            Parent root;
            try{
                root=FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.centerOnScreen();
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initMenu() {
        nodesList.addAnimatedNode(nodesListSettings);
        nodesListSettings.addAnimatedNode(nodesListLanguage);
        nodesListSettings.addAnimatedNode(nodesListTheme);
    }

    public void initBookIssueTab() {
        JFXTreeTableColumn<Issue, String> issueId = new JFXTreeTableColumn<>("Issue ID");
        issueId.setPrefWidth(100);
        issueId.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getId())));

        JFXTreeTableColumn<Issue, String> date = new JFXTreeTableColumn<>("Issue Date");
        date.setPrefWidth(200);
        date.setCellValueFactory(param  -> new SimpleStringProperty(DateUtils.changeFormat(param.getValue().getValue().getDate(),"dd/MM/yyyy")));

        JFXTreeTableColumn<Issue, String> dueDate = new JFXTreeTableColumn<>("Return Due Date");
        dueDate.setPrefWidth(200);
        dueDate.setCellValueFactory(param  -> new SimpleStringProperty(DateUtils.changeFormat(param.getValue().getValue().getReturnDueDate(),"dd/MM/yyyy")));

        ObservableList<Issue> issues = FXCollections.observableArrayList(is.getIssuesById(this.loggedInUser.getId()));
        lblTotal.setText("Total: "+ issues.size());
        final TreeItem<Issue> root = new RecursiveTreeItem<>(issues, RecursiveTreeObject::getChildren);
        bookIssueTTV.getColumns().setAll(issueId, date, dueDate);
        bookIssueTTV.setRoot(root);
        bookIssueTTV.setShowRoot(false);
    }

    public void initBookTab() {
        JFXTreeTableColumn<Book, String> bookId = new JFXTreeTableColumn<>("Book ID");
        bookId.setPrefWidth(100);
        bookId.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getId())));

        JFXTreeTableColumn<Book, String> title = new JFXTreeTableColumn<>("Book Title");
        title.setPrefWidth(400);
        title.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getTitle())));

        JFXTreeTableColumn<Book, String> author = new JFXTreeTableColumn<>("Author");
        author.setPrefWidth(200);
        author.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getAuthor())));

        JFXTreeTableColumn<Book, String> genre = new JFXTreeTableColumn<>("Genre");
        genre.setPrefWidth(200);
        genre.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getGenre())));

        JFXTreeTableColumn<Book, String> publisher = new JFXTreeTableColumn<>("Publisher");
        publisher.setPrefWidth(300);
        publisher.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getPublisher())));

        JFXTreeTableColumn<Book, String> publishedYear = new JFXTreeTableColumn<>("Published Year");
        publishedYear.setPrefWidth(150);
        publishedYear.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getPublishedYear())));

        JFXTreeTableColumn<Book, String> summmary = new JFXTreeTableColumn<>("Summary");
        summmary.setPrefWidth(400);
        summmary.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getSummary())));

        JFXTreeTableColumn<Book, String> amount = new JFXTreeTableColumn<>("Amount");
        amount.setPrefWidth(100);
        amount.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getAmount())));

        BookService bs = new BookService();
        ObservableList<Book> books = FXCollections.observableArrayList(bs.getBooks());
        final TreeItem<Book> root = new RecursiveTreeItem<>(books, RecursiveTreeObject::getChildren);
        bookTTV.getColumns().setAll(bookId, title, author, genre, publisher, publishedYear, summmary, amount);
        bookTTV.setRoot(root);
        bookTTV.setShowRoot(false);
    }

    public void refreshHandler() {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        switch (tabIndex) {
            case 0:initBookIssueTab();
                break;
            default:initBookTab();
        }
    }

    public void showInfo() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/account_info.fxml"));
            Stage stage = new Stage();
            stage.setTitle(Bundle.getString("signup.title"));
            stage.setScene(new Scene(root));
            Stage primaryStage = (Stage) infoButton.getScene().getWindow();
            stage.initOwner(primaryStage);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
