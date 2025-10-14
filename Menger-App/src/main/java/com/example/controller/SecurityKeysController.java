package com.example.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.model.SecurityKey;
import com.example.model.SecurityKeyManager;

import java.util.List;

public class SecurityKeysController {

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private TableView<SecurityKey> keysTable;

    @FXML
    private TableColumn<SecurityKey, String> ownerCol;

    @FXML
    private TableColumn<SecurityKey, String> hashCol;

    @FXML
    private TableColumn<SecurityKey, Boolean> activeCol;

    @FXML
    private Button generateKeyButton;

    @FXML
    private Button validateButton;

    @FXML
    private Button revokeButton;

    @FXML
    private Button backButton;

    private final SecurityKeyManager keyManager = new SecurityKeyManager();

    @FXML
    public void initialize() {
        // ✅ Populate ComboBox safely (Scene Builder compatible)
        sortComboBox.getItems().addAll(
                "Creation Date",
                "Expiration Date",
                "User ID",
                "Key Type",
                "Verification Status"
        );
        sortComboBox.getSelectionModel().selectFirst();

        // ✅ Configure Table Columns
        ownerCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getUserId()));

        hashCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRawKey()));

        activeCol.setCellValueFactory(data ->
                new SimpleBooleanProperty(data.getValue().isVerified()));

        // ✅ Initial table data
        refreshKeys();
    }

    private void refreshKeys() {
        String sortBy = sortComboBox.getValue();
        List<SecurityKey> sorted = keyManager.getSortedKeys(sortBy);
        keysTable.setItems(FXCollections.observableArrayList(sorted));
    }

    // 🔹 When sort option changes
    @FXML
    private void onSortChanged(ActionEvent event) {
        refreshKeys();
    }

    // 🔹 When Generate Key is clicked
    @FXML
    private void onGenerateKey(ActionEvent event) {
        keyManager.generateRandomKey();
        refreshKeys();
        showAlert("Success", "New security key generated successfully.");
    }

    // 🔹 When Validate Key is clicked
    @FXML
    private void onValidateKey(ActionEvent event) {
        SecurityKey selected = keysTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a key to validate.");
            return;
        }

        boolean valid = keyManager.validateKey(selected.getRawKey());
        showAlert("Validation", valid ? "Key is valid." : "Key is invalid.");
    }

    // 🔹 When Revoke Key is clicked
    @FXML
    private void onRevokeKey(ActionEvent event) {
        SecurityKey selected = keysTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a key to revoke.");
            return;
        }

        keyManager.revokeKey(selected.getRawKey());
        refreshKeys();
        showAlert("Revoked", "The selected key has been revoked.");
    }

    // 🔹 Back button (menu navigation)
    @FXML
    private void onBackClicked(ActionEvent event) {
        // Replace with your main menu navigation logic
        System.out.println("Back to main menu...");
    }

    // 🔹 Helper to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
