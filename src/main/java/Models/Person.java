package Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty age;
    private final ObjectProperty<LocalDate> birthday;

    public Person(Integer id, String name, Integer age, LocalDate birthday) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String firstName) {
        this.name.set(firstName);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Integer getAge() {
        return age.get();
    }

    public void setLastName(Integer age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public Integer getId() {
        return id.get();
    }
    public void setId(Integer id){
        this.id.set(id);
    }
}