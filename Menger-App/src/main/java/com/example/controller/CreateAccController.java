package com.example.controller;

import java.io.FileWriter;
import java.io.IOException;

import com.example.MainClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateAccController {

    @FXML   
    private VBox rootVBox;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton maleRadio1;
    @FXML
    private Button signUpButton;

    private final String ACCOUNTS_FILE = System.getProperty("user.dir") + "/accounts.txt";

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSignUp() {
    String firstName = firstNameField.getText().trim();
    String lastName = lastNameField.getText().trim();
    String email = emailField.getText().trim();
    String password = passwordField.getText().trim();
    String birthday = birthDatePicker.getValue() != null ? birthDatePicker.getValue().toString() : "";

    if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !birthday.isEmpty()) {
        try {
            System.out.println("Writing account to: " + ACCOUNTS_FILE);
            try (FileWriter fw = new FileWriter(ACCOUNTS_FILE, true)) {
                fw.write(firstName + "," + lastName + "," + email + "," + password + "," + birthday + System.lineSeparator());
            }
            MainClient.setRoot("login", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Please fill in all fields.");
    }
}

    @FXML
    private void handleBackToLogin() {
        try {
            MainClient.setRoot("login", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}