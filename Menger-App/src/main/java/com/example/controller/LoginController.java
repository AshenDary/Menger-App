package com.example.controller;

import java.io.File;
import java.io.IOException;

import com.example.model.CurrentUser;
import com.example.model.User;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    @FXML
    private Hyperlink ChangePass;

    @FXML
    private TextField txtPasswordVisible;

    @FXML
    private Button btnTogglePassword;

    private boolean passwordVisible = false;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = passwordVisible ? txtPasswordVisible.getText().trim() : txtPassword.getText().trim();

        if (authenticate(username, password)) {
            lblMessage.setStyle("-fx-text-fill: green;");
            lblMessage.setText("Login successful!");
            try {
                User user = getUserDetails(username, password);
                CurrentUser.getInstance().setUser(user);

                MainClient.initializeClientSocket();
                MainClient.setRoot("chat", user);
            } catch (Exception e) {
                lblMessage.setStyle("-fx-text-fill: red;");
                lblMessage.setText("FAILED TO LOAD");
                e.printStackTrace();
            }
        } else {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Invalid username or password.");
        }
    }

    @FXML
    private void handleTogglePassword(ActionEvent event) {
        passwordVisible = !passwordVisible;
        
        if (passwordVisible) {
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
            btnTogglePassword.setText("🙈");
        } else {
            txtPassword.setText(txtPasswordVisible.getText());
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
            btnTogglePassword.setText("👁");
        }
    }


    private boolean authenticate(String username, String password) {
        String filePath = System.getProperty("user.dir") + "/accounts.txt";
        try {
            File file = new File(filePath);
            if (!file.exists()) file.createNewFile();

            try (var br = new java.io.BufferedReader(new java.io.FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",", 7);
                    if (parts.length == 7 && parts[2].equals(username) && parts[5].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User getUserDetails(String username, String password) throws IOException {
        String filePath = System.getProperty("user.dir") + "/accounts.txt";
        try (var br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 7);
                if (parts.length == 7 && parts[2].equals(username) && parts[5].equals(password)) {
                    User user = new User(username);
                    user.setFirstName(parts[0]);
                    user.setLastName(parts[1]);
                    user.setDisplayName(username);
                    return user;
                }
            }
        }
        throw new IOException("User details not found for username: " + username);
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {
            MainClient.setRoot("createaccname", null);
        } catch (Exception e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Failed to load create account screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void ChangePassTransfer() {
        try {
            MainClient.setRoot("changepassword", null);
        } catch (Exception e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Failed to load change password screen.");
            e.printStackTrace();
        }
    }
}
