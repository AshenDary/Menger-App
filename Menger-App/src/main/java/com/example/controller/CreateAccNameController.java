package com.example.controller;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccNameController {
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;

    @FXML
    private Button nextbutton;

    @FXML
    private ImageView backicon;

    @FXML
    private void initialize() {
        backicon.setOnMouseClicked(this::handleBack);
    }

    @FXML
    private void handleNext(ActionEvent event) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            System.out.println("❗ Please enter both first and last name.");
            return;
        }

        try {
            // Proceed to Birthday screen (2)
            CreateAccountData.getInstance().setFirstName(firstName);
            CreateAccountData.getInstance().setLastName(lastName);
            MainClient.setRoot("createaccbirthday", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBack(MouseEvent event) {
        try {
            MainClient.setRoot("login", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}    
