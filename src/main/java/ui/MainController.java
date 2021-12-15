package ui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import pojo.Book;
import pojo.Issue;
import pojo.User;
import services.BookService;
import services.IssueService;
import services.UserService;
import utils.AlertUtils;
import utils.Bundle;
import utils.Context;
import utils.DateUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {
    @FXML AnchorPane anchorPane;
    @FXML JFXTabPane tabPane;
    @FXML JFXNodesList nodesList;
    @FXML JFXNodesList nodesListSettings;
    @FXML JFXNodesList nodesListLanguage;
    @FXML JFXNodesList nodesListTheme;
    @FXML JFXButton logoutButton;
    @FXML Tab tabBookIssue,tabBook,tabUser;
    @FXML JFXTreeTableView bookIssueTTV,bookTTV,userTTV;
    @FXML Label lblTotal;
    @FXML JFXTextField txtSearch;

    ObservableList<Issue> issues;
    ObservableList<Book> books;
    ObservableList<User> users;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initMenu();
        initBookIssueTab();
        initBookTab();
        initUserTab();
        User currentUser = Context.getInstance().getLoginLoader().user;

        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            int total = 0;
            if (newTab==tabBookIssue)
                total = bookIssueTTV.expandedItemCountProperty().getValue();
            else if (newTab==tabBook)
                total = bookTTV.expandedItemCountProperty().getValue();
            else
                total = userTTV.expandedItemCountProperty().getValue();
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
        userTTV.expandedItemCountProperty().addListener((observableValue, oldNum, newNum) -> {
            lblTotal.setText("Total: "+ newNum);
            if ((int)oldNum* (int)newNum==0) {
                Label label = new Label(Bundle.getString("main.tableview.empty"));
                label.setStyle("-fx-font-size: 16; -fx-text-fill: -fx-text;");
                userTTV.setPlaceholder(label);
            }
        });

        /** Clear row selection if clicked again **/
        bookIssueTTV.setRowFactory((Callback<TreeTableView, TreeTableRow>) treeTableView -> {
            final TreeTableRow<Issue> row = new TreeTableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && bookIssueTTV.getSelectionModel().isSelected(index)  ) {
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
                if (index >= 0 && bookTTV.getSelectionModel().isSelected(index)  ) {
                    bookTTV.getSelectionModel().clearSelection();
                    event.consume();
                }
            });
            return row;
        });
        userTTV.setRowFactory((Callback<TreeTableView, TreeTableRow>) treeTableView -> {
            final TreeTableRow<Book> row = new TreeTableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && userTTV.getSelectionModel().isSelected(index)  ) {
                    userTTV.getSelectionModel().clearSelection();
                    event.consume();
                }
            });
            return row;
        });

        /** Search bar **/
        txtSearch.textProperty().addListener((observableValue, oldVal, newVal) -> {
            int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
            switch (tabIndex) {
                case 0:
                    bookIssueTTV.setPredicate((Predicate<TreeItem<Issue>>) treeItem -> {
                        Boolean flag = String.valueOf(treeItem.getValue().getId()).contains(newVal);
                        System.out.println(treeItem.getValue().getId());
                        return flag;
                    });
                    break;
                case 1:
                    bookTTV.setPredicate((Predicate<TreeItem<Book>>) treeItem -> {
                        Boolean flag = String.valueOf(treeItem.getValue().getId()).contains(newVal);
                        System.out.println(treeItem.getValue().getId());
                        return flag;
                    });
                    break;
                default:
                    userTTV.setPredicate((Predicate<TreeItem<User>>) treeItem -> {
                        Boolean flag = String.valueOf(treeItem.getValue().getId()).contains(newVal);
                        System.out.println(treeItem.getValue().getId());
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
                stage.setScene(new Scene(root,400, 386));
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

        JFXTreeTableColumn<Issue, String> userId = new JFXTreeTableColumn<>("User ID");
        userId.setPrefWidth(100);
        userId.setCellValueFactory(param  -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getUserId())));

        JFXTreeTableColumn<Issue, String> date = new JFXTreeTableColumn<>("Issue Date");
        date.setPrefWidth(200);
        date.setCellValueFactory(param  -> new SimpleStringProperty(DateUtils.changeFormat(param.getValue().getValue().getDate(),"dd/MM/yyyy")));

        JFXTreeTableColumn<Issue, String> dueDate = new JFXTreeTableColumn<>("Return Due Date");
        dueDate.setPrefWidth(200);
        dueDate.setCellValueFactory(param  -> new SimpleStringProperty(DateUtils.changeFormat(param.getValue().getValue().getReturnDueDate(),"dd/MM/yyyy")));

        IssueService is = new IssueService();
        issues = FXCollections.observableArrayList(is.getIssues());
        lblTotal.setText("Total: "+ issues.size());
        final TreeItem<Issue> root = new RecursiveTreeItem<>(issues, RecursiveTreeObject::getChildren);
        bookIssueTTV.getColumns().setAll(issueId, userId, date, dueDate);
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
        books = FXCollections.observableArrayList(bs.getBooks());
        final TreeItem<Book> root = new RecursiveTreeItem<>(books, RecursiveTreeObject::getChildren);
        bookTTV.getColumns().setAll(bookId, title, author, genre, publisher, publishedYear, summmary, amount);
        bookTTV.setRoot(root);
        bookTTV.setShowRoot(false);
    }


    public void initUserTab() {
        JFXTreeTableColumn<User,String> userId=new JFXTreeTableColumn<>("User Id");
        userId.setPrefWidth(100);
        userId.setCellValueFactory(param-> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getId())));

        JFXTreeTableColumn<User,String> acount=new JFXTreeTableColumn<>("Acount");
        acount.setPrefWidth(200);
        acount.setCellValueFactory(param-> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getAccountId())));

        JFXTreeTableColumn<User,String> status=new JFXTreeTableColumn<>("Status");
        status.setPrefWidth(60);
        status.setCellValueFactory(param-> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getStatus())));

        JFXTreeTableColumn<User,String> name=new JFXTreeTableColumn<>("Name");
        name.setPrefWidth(200);
        name.setCellValueFactory(param-> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getName())));

        JFXTreeTableColumn<User,String> birthday=new JFXTreeTableColumn<>("Birthday");
        birthday.setPrefWidth(200);
        birthday.setCellValueFactory(param-> new SimpleStringProperty(DateUtils.changeFormat(param.getValue().getValue().getBirthday(),"dd/MM/yyyy")));

        JFXTreeTableColumn<User,String> phoneNumber=new JFXTreeTableColumn<>("Phone Number");
        phoneNumber.setPrefWidth(150);
        phoneNumber.setCellValueFactory(param-> new SimpleStringProperty("0"+ param.getValue().getValue().getPhoneNumber()));

        JFXTreeTableColumn<User,String> email=new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(250);
        email.setCellValueFactory(param-> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getEmail())));

        JFXTreeTableColumn<User,String> studentId=new JFXTreeTableColumn<>("Student Id");
        studentId.setPrefWidth(150);
        studentId.setCellValueFactory(param-> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getStudentId())));

        UserService us=new UserService();
        users=FXCollections.observableArrayList(us.getStudentUsers());
        final TreeItem<User> root=new RecursiveTreeItem<>(users,RecursiveTreeObject::getChildren);
        userTTV.getColumns().setAll(userId,acount,status,name,birthday,phoneNumber,email,studentId);
        userTTV.setRoot(root);
        userTTV.setShowRoot(false);

    }

    public void addHandler() throws IOException {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        Parent root;
        switch (tabIndex) {
            case 0:
                root = FXMLLoader.load(getClass().getResource("/fxml/add_issue.fxml"));
                Stage stage = new Stage();
                stage.setTitle(Bundle.getString("addBook.title"));
                stage.setScene(new Scene(root));
                Stage primaryStage = (Stage) lblTotal.getScene().getWindow();
                stage.initOwner(primaryStage);
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
                break;
            case 1:
                root = FXMLLoader.load(getClass().getResource("/fxml/add_book.fxml"));
                stage = new Stage();
                stage.setTitle(Bundle.getString("addIssue.title"));
                stage.setScene(new Scene(root));
                primaryStage = (Stage) lblTotal.getScene().getWindow();
                stage.initOwner(primaryStage);
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
                break;
            default:
                root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
                stage = new Stage();
                stage.setTitle(Bundle.getString("addUser.title"));
                stage.setScene(new Scene(root));
                primaryStage = (Stage) lblTotal.getScene().getWindow();
                stage.initOwner(primaryStage);
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
                break;
        }

    }

    public void editHandler() {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        switch (tabIndex) {
            case 0:
                break;
            case 1:
                break;
            default:;

        }
    }

    public void deleteHandler() {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        switch (tabIndex) {
            case 0:
                if (bookIssueTTV.expandedItemCountProperty().getValue()>0) {
                    TreeItem<Issue> selectedIssue = (TreeItem<Issue>) bookIssueTTV.getSelectionModel().getSelectedItem();
                    if (selectedIssue!=null) {
                        selectedIssue.getParent().getChildren().remove(selectedIssue);
                    }
                }
                break;
            case 1:
                if (bookTTV.expandedItemCountProperty().getValue()>0) {
                    TreeItem<Book> selectedBook = (TreeItem<Book>) bookTTV.getSelectionModel().getSelectedItem();
                    if (selectedBook!=null) {
                        selectedBook.getParent().getChildren().remove(selectedBook);
                    }
                }

                break;
            default:
                if (userTTV.expandedItemCountProperty().getValue()>0) {
                    TreeItem<User> selectedUser = (TreeItem<User>) userTTV.getSelectionModel().getSelectedItem();
                    if (selectedUser!=null) {
                        UserService userService = new UserService();
                        userService.deleteUser(selectedUser.getValue().getId());
                        selectedUser.getParent().getChildren().remove(selectedUser);
                    }

                }
        }
    }

}
