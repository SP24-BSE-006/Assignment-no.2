package com.example.assignment2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class FormApp extends Application {
    // ArrayList to store Person objects
    private ArrayList<Person> personList = new ArrayList<>();
    private String uploadedFilePath = null; // To store the uploaded file path
    @Override
    public void start(Stage primaryStage) {
        Label banner = new Label("Person Form");
        banner.setStyle("-fx-background-color: #C8E6F7; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: navy;");
        banner.setMinHeight(50);
        banner.setAlignment(Pos.CENTER);
        banner.setMaxWidth(Double.MAX_VALUE);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label fatherNameLabel = new Label("Father Name:");
        TextField fatherNameField = new TextField();
        Label cnicLabel = new Label("CNIC:");
        TextField cnicField = new TextField();
        Label datePickerLabel = new Label("Date (Picker):");
        DatePicker datePicker = new DatePicker();

        Label genderLabel = new Label("Gender:");
        RadioButton maleRadio = new RadioButton("Male");
        RadioButton femaleRadio = new RadioButton("Female");
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(10, maleRadio, femaleRadio); // Place them in a horizontal box
        genderBox.setAlignment(Pos.CENTER_LEFT);

        Label cityLabel = new Label("City:");
        ComboBox<String> cityComboBox = new ComboBox<>();
        cityComboBox.getItems().addAll("Lahore", "Karachi", "Multan", "Islamabad", "Peshawar");

        Label fileLabel = new Label("File:");
        Button uploadFileButton = new Button("Upload File");
        Button saveButton = new Button("Save");
        //ACTION OF UPLOAD FILE BUTTON..........
        uploadFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Supported Files", "*.png", "*.pdf", "*.exe"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                uploadedFilePath = selectedFile.getAbsolutePath(); // Save the file path
                System.out.println("File selected: " + uploadedFilePath); // Optional: log file path
            }
        });

        // ACTION OF SAVE BUTTON..........
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String cnic = cnicField.getText();
            LocalDate date = datePicker.getValue();
            String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : null;
            String city = cityComboBox.getValue();
            if (name.isEmpty() || fatherName.isEmpty() || cnic.isEmpty() || date == null || gender == null || city == null || uploadedFilePath == null) {
                showAlert("Error", "All fields are required. Please fill out the form completely.", Alert.AlertType.ERROR);
            } else {
                Person person = new Person(name, fatherName, cnic, date, gender, city, uploadedFilePath);
                personList.add(person);
                showAlert("Success", "Person details saved successfully!", Alert.AlertType.INFORMATION);

                nameField.clear();
                fatherNameField.clear();
                cnicField.clear();
                datePicker.setValue(null);
                genderGroup.selectToggle(null);
                cityComboBox.setValue(null);
                uploadedFilePath = null;
            }
        });

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20, 20, 20, 20));

        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(fatherNameLabel, 0, 1);
        formGrid.add(fatherNameField, 1, 1);
        formGrid.add(cnicLabel, 0, 2);
        formGrid.add(cnicField, 1, 2);
        formGrid.add(datePickerLabel, 0, 3);
        formGrid.add(datePicker, 1, 3);

        formGrid.add(genderLabel, 0, 4);
        formGrid.add(genderBox, 1, 4);
        formGrid.add(cityLabel, 0, 5);
        formGrid.add(cityComboBox, 1, 5);
        formGrid.add(fileLabel, 0, 6);
        formGrid.add(uploadFileButton, 1, 6);
        formGrid.add(saveButton, 1, 7);

        GridPane.setMargin(saveButton, new Insets(10, 0, 0, 0));
        saveButton.setMaxWidth(Double.MAX_VALUE);

        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(banner, formGrid);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(mainLayout, 400, 500);

        primaryStage.setTitle("Person Form with Banner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

// Person class
class Person {
    private String name;
    private String fatherName;
    private String cnic;
    private LocalDate date;
    private String gender;
    private String city;
    private String filePath;

    public Person(String name, String fatherName, String cnic, LocalDate date, String gender, String city, String filePath) {
        this.name = name;
        this.fatherName = fatherName;
        this.cnic = cnic;
        this.date = date;
        this.gender = gender;
        this.city = city;
        this.filePath = filePath;
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", cnic='" + cnic + '\'' +
                ", date=" + date +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
