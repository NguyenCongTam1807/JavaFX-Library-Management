package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import utils.DateUtils;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddIssueController implements Serializable, Initializable {

    @FXML private VBox root,vboxBookList;
    @FXML private JFXTextField userId;
    @FXML private JFXTextField txtIssueDate;
    @FXML private JFXTextField txtBook1;
    @FXML private JFXButton btnAdd, btnDelete;
    @FXML private List<JFXTextField> issuedBooks = new ArrayList<>();
    private int i;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtIssueDate.setText(DateUtils.changeFormat(new Date(),"dd/MM/yyyy"));
        issuedBooks.add(txtBook1);

    }

    public void addHandler() {
        int i = issuedBooks.size();
        if (i<5) {
            JFXTextField textField = new JFXTextField();
            textField.setLabelFloat(true);
            textField.setEffect(new Glow(0.9));
            textField.setPrefHeight(30);
            textField.setPromptText("Book ID No."+(i+1));
            VBox.setMargin(textField,new Insets(20,0,0,0));
            root.getScene().getWindow().setHeight(Math.round(root.getHeight()+87));
            vboxBookList.getChildren().add(textField);
            issuedBooks.add(textField);
        }
    }

    public void deleteHandler() {
        i = issuedBooks.size();
        if (i>1) {
            root.getScene().getWindow().setHeight(Math.round(root.getHeight()-13));
            vboxBookList.getChildren().remove(issuedBooks.get(i-1));
            issuedBooks.remove(i-1);
        }
    }

}
