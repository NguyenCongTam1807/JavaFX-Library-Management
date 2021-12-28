package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import pojo.Book;
import pojo.IssueDetail;
import pojo.User;
import services.BookService;
import services.IssueDetailService;
import utils.Context;
import utils.DateUtils;

import java.io.Serializable;
import java.net.URL;
import java.sql.Array;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import static jdk.internal.logger.DefaultLoggerFinder.SharedLoggers.system;

public class BookReturnController implements Initializable, Serializable {

    @FXML private VBox root;
    @FXML private JFXTextField txtIssueID,txtUserID,txtIssueDate,txtReturnDueDate;
    @FXML private JFXButton btnOK;
    @FXML private Label lbl1;
    @FXML private JFXTextField txtBook1;
    @FXML private JFXToggleButton toggle1;
    @FXML private List<Label> issuedLabels = new ArrayList<>();
    @FXML private List<JFXTextField> issuedBooks = new ArrayList<>();
    @FXML private List<JFXToggleButton> toggles = new ArrayList<>();
    @FXML private VBox vboxBookList;

    private BookService bs = new BookService();
    private IssueDetailService ids=new IssueDetailService();
    private List<IssueDetail> issueDetails;

    private int issueID,userID;
    private Date issueDate,returnDueDate;
    private User loggedInUser = Context.getInstance().getLoginLoader().getLoggedInUser();
    long millis=System.currentTimeMillis();
    Date date=new java.sql.Date(millis);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtIssueID.setText(String.valueOf(issueID));
        txtUserID.setText(String.valueOf(userID));
        txtIssueDate.setText(DateUtils.changeFormat(issueDate, "dd/MM/yyyy"));
        txtReturnDueDate.setText(DateUtils.changeFormat(returnDueDate, "dd/MM/yyyy"));
        List<Book> books = bs.getIssuedBooks(issueID);
        issueDetails=ids.getIssueDetail(issueID);
        for (int i = 0; i < books.size(); i++) {
            date=new java.sql.Date(millis);
            Label label = new Label();
            label.setPrefHeight(13);
            label.setPrefWidth(400);
            label.getStyleClass().add("others-small");
            label.setEffect(new Glow(0.9));
            label.setText(books.get(i).getTitle());

            JFXTextField textField = new JFXTextField();
            textField.setEffect(new Glow(0.9));
            textField.setLabelFloat(true);
            textField.setPrefHeight(30);
            textField.setPrefWidth(400);
            textField.setPromptText("Book state when returned");
            textField.setDisable(true);

            JFXToggleButton toggle = new JFXToggleButton();
            toggle.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            toggle.setToggleColor(Paint.valueOf("#12fb08"));
            toggle.setToggleLineColor(Paint.valueOf("#10910c"));
            toggle.setSize(8);
            toggle.setEffect(new Glow(0.6));

            HBox hBox = new HBox();
            hBox.setPrefHeight(48);
            hBox.getChildren().add(textField);
            hBox.getChildren().add(toggle);

            VBox.setMargin(textField, new Insets(15, 0, 0, 0));
            VBox.setMargin(hBox, new Insets(20, 0, 0, 0));

            vboxBookList.getChildren().add(label);
            vboxBookList.getChildren().add(hBox);

            int issueDetail=i;
            toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    textField.setDisable(!t1);
                    if(t1){
                        String s=". Returned in " + DateUtils.changeFormat(date, "dd/MM/yyyy");
                        label.setText(books.get(issueDetail).getTitle()+s);
                    }
                    else {
                        label.setText(books.get(issueDetail).getTitle());
                    }
                    issueDetails.get(issueDetail).setReturnDate(t1?date:null);
                }
            });
            issuedLabels.add(label);
            issuedBooks.add(textField);
            toggles.add(toggle);
            if(issueDetails.get(i).getReturnDate()!=null){
                date=issueDetails.get(i).getReturnDate();
                textField.setText(issueDetails.get(i).getBookState());
                toggle.setSelected(true);
            }
        }
        if (loggedInUser.getStudentId()!=null && !loggedInUser.getStudentId().isEmpty())
            disableInput();
    }

    public void okHandler () {

        /** Call a method to update table "issue_details" in database here first

         **/
        for(int i=0;i<issueDetails.size();i++){
            issueDetails.get(i).setBookState(issueDetails.get(i).getReturnDate()!=null?issuedBooks.get(i).getText():"");
            ids.updateIssueDetail(issueDetails.get(i));
        }


        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /** Functions to fix ".getScene() returning null" problem **/

    public void getIssueInfo(int issueID, int userID, Date issueDate, Date returnDueDate){
        this.issueID = issueID;
        this.userID = userID;
        this.issueDate = issueDate;
        this.returnDueDate = returnDueDate;
    }

    public void disableInput(){
        toggles.forEach(tog->tog.setDisable(true));
        issuedBooks.forEach(txt->txt.setDisable(true));
    }

    private Stage myStage;
    public void setStage(Stage stage) {
        myStage = stage;
    }

}
