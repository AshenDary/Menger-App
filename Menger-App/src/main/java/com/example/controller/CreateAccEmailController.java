package com.example.controller;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccEmailController {

    @FXML
    private TextField emailField;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView backicon;

    @FXML
    private Button signupnumberbutton;

    @FXML
    private void initialize() {
        backicon.setOnMouseClicked(this::handleBack);
    }

    @FXML
    private void handleNext(ActionEvent event) {
        String email = emailField.getText().trim();

        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("❗ Please enter a valid email address.");
            return;
        }

        CreateAccountData.getInstance().setEmail(email);

        try {
            MainClient.setRoot("createaccpassword", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleTransfer(MouseEvent event) {
        try {
            MainClient.setRoot("createaccnumber", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBack(MouseEvent event) {
        try {
            MainClient.setRoot("createaccgender", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
