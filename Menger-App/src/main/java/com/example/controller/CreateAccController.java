package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.example.MainClient;

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
                MainClient.setRoot("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String[]> readAccounts() {
        List<String[]> accounts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    accounts.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }


    public boolean updateAccount(String email, String newPassword) {
        boolean updated = false;
        List<String[]> accounts = readAccounts();
        for (String[] acc : accounts) {
            if (acc[0].equals(email)) {
                acc[1] = newPassword;
                updated = true;
            }
        }
        if (updated) {
            writeAllAccounts(accounts); 
        }
        return updated;
    }

  
    public boolean deleteAccount(String email) {
        List<String[]> accounts = readAccounts();
        boolean removed = accounts.removeIf(acc -> acc[0].equals(email));
        if (removed) {
            writeAllAccounts(accounts); 
        }
        return removed;
    }


    private void writeAllAccounts(List<String[]> accounts) {
        try (FileWriter fw = new FileWriter(ACCOUNTS_FILE, false)) {
            for (String[] acc : accounts) {
                fw.write(acc[0] + "," + acc[1] + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
