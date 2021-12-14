package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import configs.JdbcUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import pojo.Book;
import services.BookService;
import services.IssueService;
import utils.Bundle;
import utils.DateUtils;

import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import ui.SignUpController;

public class AddIssueController implements Serializable, Initializable {

    @FXML private VBox root,vboxBookList;
    @FXML private JFXTextField userId;
    @FXML private JFXTextField txtIssueDate;
    @FXML private JFXTextField txtBook1;
    @FXML private JFXButton btnAdd, btnDelete;
    @FXML private List<JFXTextField> issuedBooks = new ArrayList<>();
    @FXML private List<Label> issuedLabels=new ArrayList<>();
    private int i;
    private BookService bs=new BookService();
    private List<Book> books=bs.getBooks();

    private void addItems(){
        int i = issuedBooks.size();
        if (i<5) {
            JFXTextField textField = new JFXTextField();
            Label label=new Label();
            label.setMinHeight(13);
            label.setId("idNo"+(i+1));
            label.getStyleClass().clear();
            label.getStyleClass().add("errorSmall");
            textField.setId("idNo"+(i+1));
            textField.setLabelFloat(true);
            textField.setEffect(new Glow(0.9));
            textField.setMinHeight(30);
            textField.setPromptText("Book ID No."+(i+1));
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (!t1){
                        if (textField.getText()!="" && label.getText()==""){
                            if (checkBook(textField.getText())!=-1)
                                label.setText(books.get(checkBook(textField.getText())).getTitle());
                            else label.setText(Bundle.getString("invalid.book.null"));
                        }
                    }
                }
            });
            textField.textProperty().addListener((new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (ui.AddBookController.validMount(textField.getText()) && textField.getText()!=""){
                        label.setText(Bundle.getString("invalid.idbook.number"));
                    }
                    else
                    if (textField.getText()== ""){
                        label.setText(Bundle.getString("invalid.input.null"));
                    }
                    else label.setText("");
                }
            }));
            VBox.setMargin(textField,new Insets(18,0,0,0));
            VBox.setMargin(label,new Insets(5,0,0,0));
            root.getScene().getWindow().setHeight(Math.round(root.getHeight()+101));
            vboxBookList.getChildren().add(textField);
            vboxBookList.getChildren().add(label);
            issuedBooks.add(textField);
            issuedLabels.add(label);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtIssueDate.setText(DateUtils.changeFormat(new Date(),"dd/MM/yyyy"));
        //addItems();
        btnAdd.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addItems();
            }
        });
        btnDelete.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                i = issuedBooks.size();
                if (i>1) {
                    root.getScene().getWindow().setHeight(Math.round(root.getHeight()-20));
                    vboxBookList.getChildren().remove(issuedBooks.get(i-1));
                    vboxBookList.getChildren().remove(issuedLabels.get(i-1));
                    issuedLabels.remove(i-1);
                    issuedBooks.remove(i-1);
                }
            }
        });
    }


    private int checkBook(String s){
        int id=Integer.parseInt(s);
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId()==id)
                return i;
        }
        return -1;
    }


}
