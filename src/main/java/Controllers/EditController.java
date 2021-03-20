package Controllers;

import java.net.URL;
import java.time.LocalDate;
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

public class EditController extends CustomWarnings{
    private DatabaseHandler dbHandler;
    private Integer personHiddenId;
    private String initialNameForField;
    private Integer initialAgeForField;
    private LocalDate initialBirthdayForField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveButon;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private DatePicker birthdayDatePicker;

    public EditController(Integer _id, String _name, Integer _age, LocalDate _date){
        dbHandler = new DatabaseHandler();
        this.personHiddenId = _id;
        this.initialNameForField = _name;
        this.initialAgeForField = _age;
        this.initialBirthdayForField = _date;
    }

    @FXML
    void initialize() {
        try {
            nameField.setText(initialNameForField);
            ageField.setText(initialAgeForField.toString());
            birthdayDatePicker.setValue(initialBirthdayForField);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        saveButon.setOnAction(event -> {
            try {
                if (nameField.getText().isEmpty()){
                    throw new NullPointerException("Name field is empty");
                }
                // Converting util.Date from DatePicker to -> sql.Date
                Date date = Date.from(birthdayDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                long difference_In_Time =  new Date().getTime() - date.getTime();
                int age = (int) (TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l);
                dbHandler.editPerson(personHiddenId, nameField.getText(), age, sqlDate);
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
