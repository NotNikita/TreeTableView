package Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class CustomWarnings {

    public void RowNotSelectedDialog(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("You have not selected a row");
        alert.setContentText("In order to edit person information, you need to select ONE person,that you need, and click EDIT button.");
        alert.show();
    }

    public void CongratulationsDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Birthday");
        alert.setHeaderText("Birthday cake for this Person");
        alert.setContentText("He've got a birthday today");
        alert.show();
    }

    public void OneOrMoreFieldEmptyDialog(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Oops, one or more fiels is empty");
        alert.setContentText("Please double-check the information you've entered. It looks like one or more fields are missing.");
        alert.show();
    }

    public Optional<ButtonType> ConfirmationDeleteDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this record?");
        return alert.showAndWait();
    }
}
