package com.example.controller;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccGenderController {

    @FXML
    private RadioButton femaleradio;

    @FXML
    private RadioButton maleradio;

    @FXML
    private ImageView backicon;

    private ToggleGroup genderGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        femaleradio.setToggleGroup(genderGroup);
        maleradio.setToggleGroup(genderGroup);
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
            MainClient.setRoot("createaccemail", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBack(MouseEvent event) {
        try {
            MainClient.setRoot("createaccbirthday", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
