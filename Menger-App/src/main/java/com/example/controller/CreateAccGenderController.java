package com.example.controller;

import com.example.network.MainClient;
import com.example.model.CreateAccountData;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

public class CreateAccGenderController {

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton otherRadio;

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
        if (genderGroup.getSelectedToggle() == null) {
            System.out.println("❗ Please select your gender.");
            return;
        }

        String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
        CreateAccountData.getInstance().setGender(gender);

        try {
            // Proceed to createaccemail.fxml (step 4)
            MainClient.setRoot("createaccemail", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBack(MouseEvent event) {
        try {
            MainClient.setRoot("createaccbirthday", null); // Go back to Step 2
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
