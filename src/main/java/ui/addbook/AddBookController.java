package ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import configs.JdbcUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
    @FXML private VBox vbox;
    @FXML private JFXButton btnAddBook, btnCancel;
    @FXML private JFXTextField txtName, txtId, txtAuthor, txtPublisher, txtYear;

    @FXML
    private void cancel() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = JdbcUtils.getConn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
