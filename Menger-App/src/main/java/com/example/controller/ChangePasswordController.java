package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChangePasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField retypePassField;

    @FXML
    private Button changePassButton;

    @FXML
    private ImageView backicon;

    @FXML
    private Label lblMessage;

    private final String filePath = System.getProperty("user.dir") + "/accounts.txt";

    @FXML
    private void initialize() {
        backicon.setOnMouseClicked(this::handleBack);
    }

    @FXML
    private void handleBack(MouseEvent event) {
        try {
            com.example.network.MainClient.setRoot("login", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
private void ChangePass() {
    String email = emailField.getText().trim();
    String newPassword = newPassField.getText().trim();
    String retypePassword = retypePassField.getText().trim();

    if (email.isEmpty() || newPassword.isEmpty() || retypePassword.isEmpty()) {
        showMessage("Please fill all fields.", "red");
        return;
    }

    if (!newPassword.equals(retypePassword)) {
        showMessage("Passwords do not match.", "red");
        return;
    }

    if (newPassword.length() < 6) {
        showMessage("Password must be at least 6 characters.", "red");
        return;
    }

    try {
        File file = new File(filePath);
        if (!file.exists()) file.createNewFile();

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        boolean found = false;
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",", 7);
            if (parts.length == 7 && parts[4].trim().equalsIgnoreCase(email)) {
                parts[5] = newPassword;
                line = String.join(",", parts);
                found = true;
            }
            updatedLines.add(line);
        }

        if (found) {
            Files.write(Paths.get(filePath), updatedLines);
            showMessage("Password successfully changed!", "green");

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(() -> {
                        try {
                            com.example.network.MainClient.setRoot("login", null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            showMessage("Email not found.", "red");
        }

    } catch (IOException e) {
        e.printStackTrace();
        showMessage("An error occurred. Try again.", "red");
    }
}


    private void showMessage(String message, String color) {
        if (lblMessage != null) {
            lblMessage.setText(message);
            lblMessage.setStyle("-fx-text-fill: " + color + ";");
        }
    }
}
