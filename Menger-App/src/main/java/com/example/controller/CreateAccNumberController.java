package com.example.controller;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccNumberController {

    @FXML
    private TextField numberField;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView backicon;

    @FXML
    private void initialize() {
        backicon.setOnMouseClicked(this::handleBack);
    }

    @FXML
    private void handleNext(ActionEvent event) {
        String number = numberField.getText().trim();

        if (number.isEmpty() || !number.matches("\\d{10,15}")) {
            System.out.println("❗ Please enter a valid phone number (10-15 digits).");
            return;
        }

        CreateAccountData.getInstance().setNumber(number);

        try {
            MainClient.setRoot("createaccpassword", null);
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
