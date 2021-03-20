package Controllers;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import DB.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreationController extends CustomWarnings{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker birthdayField;

    @FXML
    private TextField nameField;

    @FXML
    void initialize() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        saveButton.setOnAction(event -> {
            try {
                if (nameField.getText().isEmpty()){
                    throw new NullPointerException("Name field is empty");
                }
                // Converting util.Date from DatePicker to -> sql.Date
                Date date = Date.from(birthdayField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                long difference_In_Time =  new Date().getTime() - date.getTime();
                int age = (int) (TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l);
                dbHandler.addNewPerson(nameField.getText(), age, sqlDate);
                cancelButton.fire();
            }
            catch (NullPointerException e){
                OneOrMoreFieldEmptyDialog();
            }
        });

        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
