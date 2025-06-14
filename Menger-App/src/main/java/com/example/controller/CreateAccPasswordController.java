package com.example.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccPasswordController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button finishButton;

    @FXML
    private ImageView backicon;

    @FXML
    private void initialize() {
        backicon.setOnMouseClicked(this::handleBack);
    }

    @FXML
    private void handleFinish(ActionEvent event) {
        String password = passwordField.getText().trim();

        if (password.isEmpty() || password.length() < 6) {
            System.out.println("❗ Password must be at least 6 characters.");
            return;
        }

        CreateAccountData data = CreateAccountData.getInstance();
        data.setPassword(password);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            String line = String.join(",",
                data.getFirstName(),
                data.getLastName(),
                data.getUsername(),
                data.getGender(),
                (data.getEmail() != null ? data.getEmail() : data.getNumber()),
                data.getPassword(),
                data.getBirthday()
            );
            writer.write(line);
            writer.newLine();
            System.out.println("✅ Account created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            MainClient.setRoot("login", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBack(MouseEvent event) {
        try {
            if (CreateAccountData.getInstance().getEmail() != null) {
                MainClient.setRoot("createaccemail", null);
            } else {
                MainClient.setRoot("createaccnumber", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
