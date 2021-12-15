package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {

    public static boolean showConfirmAlert(String titleResource, String contentResource) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Bundle.getString(titleResource));
        alert.setHeaderText(null);
        alert.setContentText(Bundle.getString(contentResource));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void showErrorAlert(String titleResource, String contentResource) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Bundle.getString(titleResource));
        alert.setHeaderText(null);
        alert.setContentText(Bundle.getString(contentResource));
        alert.show();
    }


}
