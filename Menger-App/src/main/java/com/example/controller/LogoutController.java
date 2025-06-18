package com.example.controller;

import java.io.File;
import java.io.IOException;

import com.example.model.CurrentUser;
import com.example.model.User;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LogoutController {

    @FXML
    private ImageView backicon;

    @FXML
    private Label cfuserchatpreviewStatic;

    @FXML
    private Button deleteaccount;

    @FXML
    private Label lblMessage;

    @FXML
    private Button logoutbutton;

    @FXML
    private void initialize() {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser != null && cfuserchatpreviewStatic != null) {
            if (currentUser.getFirstName() != null && currentUser.getLastName() != null) {
                cfuserchatpreviewStatic.setText(currentUser.getFullName());
            } else {
                cfuserchatpreviewStatic.setText(currentUser.getDisplayName());
            }
        }
    }


    @FXML
    void HandleBackToMenu(MouseEvent event) {
        try {
            MainClient.setRoot("menu", CurrentUser.getInstance().getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void HandleDeleteAccount(ActionEvent event) {
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser == null) {
            lblMessage.setText("No user is currently logged in.");
            return;
        }

        String usernameToDelete = currentUser.getDisplayName();
        String filePath = System.getProperty("user.dir") + "/accounts.txt";

        File file = new File(filePath);
        if (!file.exists()) {
            lblMessage.setText("Accounts file not found.");
            return;
        }

        try {
            java.util.List<String> updatedLines = new java.util.ArrayList<>();

            try (var br = new java.io.BufferedReader(new java.io.FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.contains("," + usernameToDelete + ",")) {
                        updatedLines.add(line);
                    }
                }
            }

            try (var writer = new java.io.BufferedWriter(new java.io.FileWriter(file))) {
                for (String line : updatedLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            // Clear session and redirect
            CurrentUser.getInstance().setUser(null);
            MainClient.setRoot("login", null);
        } catch (IOException e) {
            lblMessage.setText("Error deleting account.");
            e.printStackTrace();
        }
    }


    @FXML
    void HandleLogOut(ActionEvent event) {
        CurrentUser.getInstance().setUser(null);

        try {
            MainClient.setRoot("login", null);
        } catch (IOException e) {
            lblMessage.setText("Failed to return to login.");
            e.printStackTrace();
        }
    }


}

