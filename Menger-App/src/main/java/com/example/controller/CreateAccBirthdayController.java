package com.example.controller;

import java.time.format.DateTimeFormatter;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccBirthdayController {

    @FXML
    private DatePicker birthdayPicker;

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
        if (birthdayPicker.getValue() == null) {
            System.out.println("❗ Please select your birthday.");
            return;
        }

        String birthday = birthdayPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CreateAccountData.getInstance().setBirthday(birthday);

        try {
            // Proceed to Gender screen (3)
            MainClient.setRoot("createaccgender", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBack(MouseEvent event) {
        try {
            MainClient.setRoot("createaccname", null); // Go back to Step 1
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
