package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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
import services.BookService;
import utils.DateUtils;

import java.io.Serializable;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private int issueID,userID;
    private Date issueDate,returnDueDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtIssueID.setText(String.valueOf(issueID));
        txtUserID.setText(String.valueOf(userID));
        txtIssueDate.setText(DateUtils.changeFormat(issueDate, "dd/MM/yyyy"));
        txtReturnDueDate.setText(DateUtils.changeFormat(returnDueDate, "dd/MM/yyyy"));
        issuedLabels.add(lbl1);
        issuedBooks.add(txtBook1);
        toggles.add(toggle1);
        List<Book> books = bs.getIssuedBooks(issueID);
        lbl1.setText(books.get(0).getTitle());
        toggle1.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            txtBook1.setDisable(!newVal);
            if (!newVal)
                lbl1.setText(lbl1.getText()+". Returned in " + DateUtils.changeFormat(returnDueDate, "dd/MM/yyyy"));
        });
        for (int i = 1; i < books.size(); i++) {
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

            label.setText(books.get(i).getTitle());
            toggle.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
                textField.setDisable(!newVal);
                if (!newVal)
                    label.setText(label.getText()+". Returned in " + DateUtils.changeFormat(returnDueDate, "dd/MM/yyyy"));
            });

            issuedLabels.add(label);
            issuedBooks.add(textField);
            toggles.add(toggle);
        }
    }

    public void okHandler (){

        /** Call a method to update table "issue_details" in database here first

         ...

         **/

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

    private Stage myStage;
    public void setStage(Stage stage) {
        myStage = stage;
    }

}
