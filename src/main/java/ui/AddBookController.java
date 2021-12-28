package ui;

import javafx.beans.value.ChangeListener;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import configs.JdbcUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Book;
import services.BookService;
import utils.Bundle;
import utils.AlertUtils;
import utils.Context;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddBookController implements Initializable {

    private boolean isHandleAddBook=true;
    private Book book;
    private Stage myStage;

    @FXML private VBox vbox;
    @FXML private JFXButton btnAddBook, btnClear;
    @FXML private JFXTextField txtAmount,txtTitle,txtSummary,txtGenre,txtPublisher, txtPublishedYear, txtAuthor,txtIdBook;
    @FXML private Label lblErrorAmount,lblErrorSummary,lblErrorIdBook, lblErrorPublisher,lblErrorGenre,lblErrorPublishedYear,lblErrorAuthor, lblErrorTitle;

    public  void setHandleAddBook(boolean handleAddBook) {
        this.isHandleAddBook = handleAddBook;
    }

    public boolean validYear(String s) {
        if (s.length()==4){
            Pattern digit = Pattern.compile("[0-9]");
            Matcher hasDigit = digit.matcher(s);
            return hasDigit.find();
        }
        else return false;
    }

    public static String clearSpace(String s){
        s=s.trim();
        while (s.indexOf("  ")!=-1){
            s= SignUpController.removeCharAt(s,s.indexOf("  ")+1,1);
        }
        return s;
    }

    public static boolean validAmount(String s){
        s= SignUpController.unAccent(s);
        return !s.matches("-?\\d+?");
    }
    boolean valid;
    private boolean validInput(){
        valid=true;
        //loi o day nay
        vbox.getChildren().stream().filter(node -> node.getClass()==Label.class).map(Label ->((Label) Label).getText()).forEach(text ->{
            if(text!="") {
                valid = false;
            }
        });
        if (txtGenre.isDisable()==true) valid=true;
        vbox.getChildren().stream().filter(node -> node.getClass()==JFXTextField.class).map(JFXTextField ->((JFXTextField) JFXTextField)).forEach(text ->{
           if (!text.getId().equals("txtSummary"))
               if (text.getText()==""){
                   valid=false;
               }
        });
        return valid;
    }
    public void clearField(){
        vbox.getChildren().stream().filter(node -> node.getClass()==JFXTextField.class).map(JFXTextField->((JFXTextField) JFXTextField)).forEach($field->{
            $field.setText("");
        });
        vbox.getChildren().stream().filter(node -> node.getClass()==Label.class).map(Label ->((Label) Label)).forEach($text->{
            $text.setText("");
        });
        disableAllField(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!isHandleAddBook){
            txtIdBook.setText(String.valueOf(book.getId()));
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtGenre.setText(book.getGenre());
            txtPublishedYear.setText(String.valueOf(book.getPublishedYear()));
            txtSummary.setText(book.getSummary());
            txtPublisher.setText(book.getPublisher());
            txtAmount.setText(String.valueOf(book.getAmount()));
            txtIdBook.setDisable(true);
        }
        txtTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1&&isHandleAddBook){
                        if(txtTitle.getText()!=""){
                            String t=SignUpController.styleString(txtTitle.getText());
                            txtTitle.setText(t);
                            BookService bs=new BookService();
                            Book book=bs.checkBookTitle(t);
                            if(book!=null){
                                txtIdBook.setText(String.valueOf(book.getId()));
                                txtAuthor.setText(book.getAuthor());
                                txtGenre.setText(book.getGenre());
                                txtSummary.setText(book.getSummary());
                                txtPublisher.setText(book.getPublisher());
                                txtPublishedYear.setText(String.valueOf(book.getPublishedYear()));
                                disableAllField(true);
                            }
                            else {
                                if (txtSummary.isDisable()==true) {
                                    clearField();
                                    txtTitle.setText(t);
                                }
                            }
                        }
                }
            }
        });
        txtPublisher.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String Name=txtPublisher.getText();
                if (SignUpController.validName(SignUpController.unAccent(Name)) && Name!=""){
                    lblErrorPublisher.setText(Bundle.getString("invalid.name"));
                }
                else if (Name=="") {
                    lblErrorPublisher.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorPublisher.setText("");
            }
        });
        txtPublisher.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtPublisher.getText()!=""){
                        txtPublisher.setText(SignUpController.styleString(txtPublisher.getText()));
                    }
                }
            }
        });
        txtTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtTitle.getText()!=""){
                        txtTitle.setText(clearSpace(txtTitle.getText()));
                        lblErrorTitle.setText("");
                    }
                    else lblErrorTitle.setText(Bundle.getString("invalid.input.null"));
                }
            }
        });
        txtIdBook.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (validAmount(txtIdBook.getText()) && txtIdBook.getText()!=""){
                    lblErrorIdBook.setText(Bundle.getString("invalid.idbook.number"));
                }
                else
                if (txtIdBook.getText()== ""){
                    lblErrorIdBook.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorIdBook.setText("");
            }
        });
        txtAuthor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String Name=txtAuthor.getText();
                if (SignUpController.validName(SignUpController.unAccent(Name)) && Name!=""){
                    lblErrorAuthor.setText(Bundle.getString("invalid.name"));
                }
                else if (Name=="") {
                    lblErrorAuthor.setText(Bundle.getString("invalid.input.null"));
                }
                else lblErrorAuthor.setText("");
            }
        });
        txtAuthor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtAuthor.getText()!="")
                        txtAuthor.setText(SignUpController.styleString(txtAuthor.getText()));
                }
            }
        });
        txtGenre.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtGenre.getText()!=""){
                        txtGenre.setText(clearSpace(txtGenre.getText()));
                        lblErrorGenre.setText("");
                    }
                    else lblErrorGenre.setText(Bundle.getString("invalid.input.null"));
                }
            }
        });
        txtSummary.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1){
                    if (txtSummary.getText()!=""){
                        txtSummary.setText(clearSpace(txtSummary.getText()));
                    }
                }
            }
        });
        txtPublishedYear.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                if (validYear(txtPublishedYear.getText())){
                    int i=Integer.parseInt(txtPublishedYear.getText());
                    if (i>year || i<1000){
                        lblErrorPublishedYear.setText(Bundle.getString("invalid.years"));
                    }
                    else lblErrorPublishedYear.setText("");
                }
                else lblErrorPublishedYear.setText(Bundle.getString("invalid.years"));
            }
        });
        txtAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                boolean t= validAmount(txtAmount.getText());
                if (!t && txtAmount.getText()!=""){
                    int i=Integer.parseInt(txtAmount.getText());
                    if (i<1){
                        lblErrorAmount.setText(Bundle.getString("invalid.amount.outRange"));
                    }
                    else lblErrorAmount.setText("");
                }
                else lblErrorAmount.setText(Bundle.getString("invalid.amount.null"));
            }
        });
        btnAddBook.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Book book=new Book(Integer.parseInt(txtIdBook.getText()),txtTitle.getText(),Integer.parseInt(txtAmount.getText()),txtAuthor.getText(),Integer.parseInt(txtPublishedYear.getText()),txtGenre.getText(),txtPublisher.getText(),txtSummary.getText());
                BookService bookService=new BookService();
                if (validInput()){
                    if(isHandleAddBook){
                        if (bookService.addBook(book)){
                            AlertUtils.showInfoAlert("addBook.success.title","addBook.success.content");
                            Stage stage = (Stage) btnAddBook.getScene().getWindow();
                            stage.close();
                        }
                    }
                    else {
                        if(bookService.upDateBook(book)){
                            AlertUtils.showInfoAlert("editBook.success.title","editBook.success.content");
                            Stage stage = (Stage) btnAddBook.getScene().getWindow();
                            stage.close();
                        }
                    }
                    MainController controller = Context.getInstance().getMainController();
                    controller.refreshHandler();
                }
                else {
                    AlertUtils.showErrorAlert("alert.addBookFail.title","alert.addBookFail.content");
                }
            }
        });
        btnClear.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearField();
            }
        });
        txtIdBook.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1&&isHandleAddBook){
                        String t=txtIdBook.getText();
                        if(t!="" && !validAmount(t)) {
                            Book book;
                            BookService bookService = new BookService();
                            book=bookService.checkBookId(Integer.parseInt(txtIdBook.getText()));
                            if(book!=null) {
                                txtTitle.setText(book.getTitle());
                                txtAuthor.setText(book.getAuthor());
                                txtGenre.setText(book.getGenre());
                                txtSummary.setText(book.getSummary());
                                txtPublisher.setText(book.getPublisher());
                                txtPublishedYear.setText(String.valueOf(book.getPublishedYear()));
                                disableAllField(true);
                            }
                            else
                            if (txtGenre.isDisable()==true) {
                                clearField();
                                txtIdBook.setText(t);
                            }
                        }
                }
            }
        });
    }

    public void disableAllField(boolean disable){
        vbox.getChildren().stream().filter(node -> node.getClass()==JFXTextField.class).map(JFXTextField->((JFXTextField) JFXTextField)).forEach(field->{
            if(!field.getId().equals("txtIdBook")&&!field.getId().equals("txtAmount"))
                field.setDisable(disable);
        });
    }

    public void getBookInfo(int id,String title,int amount,String author,int publishedYear,String genre,String publisher,String summary){
        book=new Book(id,title,amount,author,publishedYear,genre,publisher,summary);
        isHandleAddBook=false;
    }

    public void setStage(Stage stage) {
        myStage = stage;
    }

}
