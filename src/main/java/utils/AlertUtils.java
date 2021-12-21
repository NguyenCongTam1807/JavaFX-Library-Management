package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.Glow;
import java.util.Optional;

public class AlertUtils {

    public static boolean showConfirmAlert(String titleResource, String contentResource) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Bundle.getString(titleResource));
        alert.setHeaderText("Confirmation");
        alert.setContentText(Bundle.getString(contentResource));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertUtils.class.getResource("/css/hacker_theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.setEffect(new Glow(0.4));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean showConfirmAlertWithParam(String titleResource, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Bundle.getString(titleResource));
        alert.setHeaderText("WARNING");
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertUtils.class.getResource("/css/hacker_theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.setEffect(new Glow(0.4));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void showErrorAlert(String titleResource, String contentResource) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Bundle.getString(titleResource));
        alert.setHeaderText("ERROR");
        alert.setContentText(Bundle.getString(contentResource));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertUtils.class.getResource("/css/hacker_theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.setEffect(new Glow(0.4));
        alert.show();
    }

    public static void showInfoAlert(String titleResource, String contentResource) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Bundle.getString(titleResource));
        alert.setHeaderText("Information");
        alert.setContentText(Bundle.getString(contentResource));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertUtils.class.getResource("/css/hacker_theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane2");
        dialogPane.setEffect(new Glow(0.4));
        alert.show();
    }
}
