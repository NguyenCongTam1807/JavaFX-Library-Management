package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import pojo.Book;
import pojo.User;
import services.BookService;
import services.UserService;
import utils.AlertUtils;
import utils.Bundle;
import utils.DateUtils;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class AddIssueController implements Serializable, Initializable {

    @FXML private VBox root,vboxBookList;
    @FXML private HBox btn;
    @FXML private JFXTextField userId;
    @FXML private JFXTextField txtIssueDate,txtReturnDueDate;
    @FXML private JFXTextField txtBook1;
    @FXML private Label label1,lblErrorName;
    @FXML private JFXButton btnAdd, btnDelete,btnCheckUser,btnDone;
    @FXML private List<JFXTextField> issuedBooks = new ArrayList<>();
    @FXML private List<Label> issuedLabels=new ArrayList<>();
    private int i=0;
    private BookService bs=new BookService();
    private UserService u=new UserService();
    private List<Book> books=bs.getBooks();
    private List<User> users=u.getStudentUsers();
    private String txt="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtIssueDate.setText(DateUtils.changeFormat(new Date(),"dd/MM/yyyy"));
        issuedBooks.add(txtBook1);
        issuedLabels.add(label1);
        addTextFieldListeners(txtBook1, label1);
        btnAdd.onMouseClickedProperty().set(mouseEvent -> addItems());
        btnDelete.onMouseClickedProperty().set(mouseEvent -> {
            i = issuedBooks.size();
            if (i>1) {
                root.getScene().getWindow().setHeight(Math.round(root.getHeight()-31));
                vboxBookList.getChildren().remove(issuedBooks.get(i-1));
                vboxBookList.getChildren().remove(issuedLabels.get(i-1));
                issuedLabels.remove(i-1);
                issuedBooks.remove(i-1);
            }
        });
        userId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                userIdIsTrue(true);
                lblErrorName.setText("");
            }
        });
        btnCheckUser.onMouseClickedProperty().set(mouseEvent -> {
            String user=userId.getText();
            int j=checkUsername(user);
            if (j!=-1){
                if (users.get(j).getStatus()==0){
                    lblErrorName.setText(Bundle.getString("invalid.status.0"));
                    userIdIsTrue(true);
                }
                else {
                    AlertUtils.showConfirmAlert("addIssue.usersCheck.content","addIssue.usersCheck.title");
                    lblErrorName.setText("");
                    userIdIsTrue(false);
                    //check so luong sach con co the muon
                }
            }
            else {
                lblErrorName.setText(Bundle.getString("invalid.users.null"));
                userIdIsTrue(true);
            }
        });
        btnDone.onMouseClickedProperty().set(mouseEvent ->{
            if (validBtnDone()){
                AlertUtils.showConfirmAlert("addIssue.success.title","addIssue.success.content");
                //do something
            }
            else AlertUtils.showErrorAlert("addIssue.fail.title","addIssue.fail.content");
        });
    }

    private void addItems(){
        i = issuedBooks.size();
        if (i<5) {
            JFXTextField textField = new JFXTextField();
            Label label=new Label();
            label.setPrefHeight(13);
            label.getStyleClass().add("error-small");
            label.setEffect(new Glow(0.9));
            textField.setLabelFloat(true);
            textField.setEffect(new Glow(0.9));
            textField.setPrefHeight(30);
            textField.setPromptText("Book ID No."+(i+1));
            addTextFieldListeners(textField, label);
            VBox.setMargin(textField,new Insets(20,0,0,0));
            VBox.setMargin(label,new Insets(5,0,0,0));
            root.getScene().getWindow().setHeight(Math.round(root.getHeight()+105));
            System.out.println(root.getHeight());
            vboxBookList.getChildren().add(textField);
            vboxBookList.getChildren().add(label);
            issuedBooks.add(textField);
            issuedLabels.add(label);
        }
    }

    private void addTextFieldListeners(JFXTextField textField, Label label) {
        textField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1){
                if (label.getText().equals(Bundle.getString("invalid.book.selected"))){
                }
                else
                if (textField.getText()!="" && label.getText()==""){
                    if (checkBook(textField.getText())!=-1) {
                        label.setText(books.get(checkBook(textField.getText())).getTitle());
                    }
                    else
                        label.setText(Bundle.getString("invalid.book.null"));
                }
            }
        });

        textField.textProperty().addListener(((observableValue, s, t1) -> {
            String id=" "+t1+" ";
            txt=" ";
            int t=0,index=0;
            for (int j = 0; j < issuedBooks.size(); j++) {
                txt+=issuedBooks.get(j).getText();
                txt+=" ";
            }
            while (txt.indexOf(id,index)!=-1){
                t++;
                index=txt.indexOf(id,index)+1;
            }
            if (t!=1 && t1!=""){
                label.setText(Bundle.getString("invalid.book.selected"));
            }
            else
            if (AddBookController.validAmount(t1) && t1!=""){
                label.setText(Bundle.getString("invalid.idbook.number"));
            }
            else
            if (t1== ""){
                label.setText(Bundle.getString("invalid.input.null"));
            }
            else label.setText("");
        }));
    }
    boolean valid;
    private boolean validBtnDone(){
        valid=true;
        issuedBooks.forEach(text->{
            if (text.getText().equals("")){
                valid=false;
            }
        });
        issuedLabels.forEach(label->{
           String s=label.getText();
           if (s.equals("") || s.equals(Bundle.getString("invalid.book.selected")) || s.equals(Bundle.getString("invalid.book.null")) || s.equals(Bundle.getString("invalid.idbook.number"))){
               valid=false;
           }
        });
        return valid;
    }

    private int checkBook(String s){
        int id=Integer.parseInt(s);
        for (int j = 0; j < books.size(); j++) {
            if (books.get(j).getId()==id)
                return j;
        }
        return -1;
    }

    private int checkUsername(String s){
        int id=Integer.parseInt(s);
        for (int j = 0; j < users.size(); j++) {
            if (users.get(j).getId()==id){
                return j;
            }
        }
        return -1;
    }

    private void userIdIsTrue(boolean isTrue){
       btn.setDisable(isTrue);
       vboxBookList.setDisable(isTrue);
       txtReturnDueDate.setDisable(isTrue);
       btnDone.setDisable(isTrue);
       if (isTrue){
           txtReturnDueDate.setText("");
       }
       else {
           Calendar c=Calendar.getInstance();
           Date date=new Date();
           c.setTime(date);
           c.add(Calendar.DATE,30);
           txtReturnDueDate.setText(DateUtils.changeFormat(c.getTime(),"dd/MM/yyyy"));
       }
    }
}
