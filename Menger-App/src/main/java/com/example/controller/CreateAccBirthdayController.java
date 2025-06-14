package com.example.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.model.CreateAccountData;
import com.example.network.MainClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateAccBirthdayController implements Initializable {

    @FXML
    private DatePicker myDatePicker;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView backicon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Handle DatePicker styling
        myDatePicker.getStyleClass().add("rounded");

        myDatePicker.showingProperty().addListener((obs, wasShowing, isShowing) -> {
            if (isShowing) {
                myDatePicker.getStyleClass().removeAll("rounded");
                myDatePicker.getStyleClass().add("popup-open");
            } else {
                myDatePicker.getStyleClass().removeAll("popup-open");
                myDatePicker.getStyleClass().add("rounded");
            }
        });

        // Set up back button
        backicon.setOnMouseClicked(this::handleBack);
    }

    @FXML
    private void handleNext(ActionEvent event) {
        if (myDatePicker.getValue() == null) {
            System.out.println("❗ Please select your birthday.");
            return;
        }

        String birthday = myDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CreateAccountData.getInstance().setBirthday(birthday);

        try {
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
