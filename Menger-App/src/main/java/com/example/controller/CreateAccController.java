package com.example.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

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

    private final String ACCOUNTS_FILE = Paths.get(System.getProperty("user.dir"), "accounts.txt").toString();

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSignUp() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                try (FileWriter fw = new FileWriter(ACCOUNTS_FILE, true)) {
                    fw.write(email + "," + password + System.lineSeparator());
                }
                MainClient.setRoot("login", null); // Pass null as no data is required
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Other methods remain unchanged
}