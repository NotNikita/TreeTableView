package DB;

import Models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbconnection;

    public Connection getDbconnection()
            throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbconnection = DriverManager.getConnection(connectionString, dbUser,
                dbPass);

        if (dbconnection != null) {
            System.out.println("Successfully connected to MySQL database test");
        }

        return dbconnection;
    }

    public void addNewPerson(String name, int age, Date birthday) {
        String query = "INSERT INTO " + Const.PERSON_TABLE + "(" + Const.PERSON_NAME + "," + Const.PERSON_AGE
                + "," + Const.PERSON_BIRTHDAY + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(query);
            prSt.setString(1, name);
            prSt.setInt(2, age);
            prSt.setDate(3, birthday);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editPerson(int id, String name, int age, Date birthday) {
        String query = "UPDATE "+ Const.PERSON_TABLE
                + " SET " + Const.PERSON_NAME + " = ? " + ","
                + Const.PERSON_AGE + " = ? " + ","
                + Const.PERSON_BIRTHDAY + " = ? "
                + "WHERE id = ?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(query);
            prSt.setString(1, name);
            prSt.setInt(2, age);
            prSt.setDate(3, birthday);
            prSt.setInt(4, id);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(Integer selectedPerson) {
        String query = "DELETE FROM "  + Const.PERSON_TABLE +
                " WHERE " + Const.PERSON_ID + " = ? " +
                "LIMIT 1";

        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(query);
            prSt.setInt(1, selectedPerson);
            prSt.executeUpdate();
            System.out.println("Closing connection and releasing resources...");
            prSt.close();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Person> getAllPersons() {
        ObservableList<Person> ListToReturn = FXCollections.observableArrayList();
        String select = "SELECT * FROM "  + Const.PERSON_TABLE;

        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery(select);

            while(resultSet.next()){
                Person product = new Person(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4).toLocalDate());

                System.out.println(product);
                ListToReturn.add(product);
            }


            System.out.println("Closing connection and releasing resources...");
            resultSet.close();
            prSt.close();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ListToReturn;
    }

}