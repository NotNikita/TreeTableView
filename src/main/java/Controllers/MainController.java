package Controllers;


import DB.DatabaseHandler;
import Models.Person;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MainController extends CustomWarnings {
    ObservableList<Person> ListFromDataBase;

    @FXML
    private TreeTableView<Person> mainTableView;

    @FXML
    private TreeTableColumn<Person, String> nameTableColumn;

    @FXML
    private TreeTableColumn<Person, Number> ageTableColumn;

    @FXML
    private TreeTableColumn<Person, LocalDate> birthdayTableColumn;

    @FXML
    private Button deletePersonButton;

    @FXML
    private Button addPersonButton;

    @FXML
    private Button editPersonButton;

    public MainController() {
        resetDataFromDB();
    }

    public void resetDataFromDB(){
        DatabaseHandler dbHandler = new DatabaseHandler();
        ListFromDataBase = dbHandler.getAllPersons();
    }

    @FXML
    void initialize() {
        TreeItem<Person> firstElement = new TreeItem<>(new Person(0,"Nikita Yaskevich", 20, LocalDate.of(2000,9,15)));
        for (Person p: ListFromDataBase) {
            firstElement.getChildren().add(new TreeItem<>(p));
        }
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        ageTableColumn.setCellValueFactory(param -> param.getValue().getValue().ageProperty());
        birthdayTableColumn.setCellValueFactory(param -> param.getValue().getValue().birthdayProperty());
        mainTableView.setRoot(firstElement);
        mainTableView.setShowRoot(false);


        addPersonButton.setOnAction(event ->{
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/newperson.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            resetDataFromDB();
            this.initialize();
        });

        editPersonButton.setOnAction(event ->{
            if (mainTableView.getSelectionModel().getSelectedIndex() < 0){
                RowNotSelectedDialog();
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/editperson.fxml"));
                Person selectedPerson = mainTableView.getSelectionModel().getSelectedItem().getValue();
                EditController controller = new EditController(selectedPerson.getId(), selectedPerson.getName(),
                        selectedPerson.getAge(), selectedPerson.getBirthday());

                loader.setController(controller);
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
            resetDataFromDB();
            this.initialize();
        });

        deletePersonButton.setOnAction(event -> {
            if (mainTableView.getSelectionModel().getSelectedIndex() < 0){
                RowNotSelectedDialog();
                return;
            }
            try {
                Optional<ButtonType> result = ConfirmationDeleteDialog();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    Integer selectedPerson = mainTableView.getSelectionModel().getSelectedItem().getValue().getId();
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    dbHandler.deletePerson(selectedPerson);
                } else {
                    return;
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            resetDataFromDB();
            this.initialize();
        });

        mainTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && mainTableView.getSelectionModel().getSelectedIndex() >= 0) {
                LocalDate birthdayOfSelected = mainTableView.getSelectionModel().getSelectedItem().getValue().getBirthday();
                String formatedSelectedDate = birthdayOfSelected.format(DateTimeFormatter.ofPattern("dd-MMM"));
                String formatedCurrentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM"));
                if(formatedSelectedDate.equals(formatedCurrentDate))
                {
                    CongratulationsDialog();
                }
            }
        });
    }

}
