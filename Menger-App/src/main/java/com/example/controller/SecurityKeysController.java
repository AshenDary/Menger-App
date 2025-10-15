package com.example.controller;

import com.example.model.SecurityKey;
import com.example.model.SecurityKeyManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SecurityKeysController {

    @FXML
    private Button generateKeyButton;

    @FXML
    private Button revokeButton;

    @FXML
    private Button backButton;

    @FXML
    private javafx.scene.control.Label createdLabel;

    @FXML
    private javafx.scene.control.TextField keyField;

    @FXML
    private javafx.scene.control.Label activeLabel;

    @FXML
    private javafx.scene.control.Button copyButton;

    @FXML
    private javafx.scene.control.Label expiryLabel;

    private final SecurityKeyManager keyManager = new SecurityKeyManager();

    @FXML
    public void initialize() {
        // Initial display empty
        clearDisplay();

        // Start a periodic purge of expired keys every 5 seconds; if current key expires, clear UI
        Timeline purgeTimeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            int removed = keyManager.purgeExpiredKeys();
            if (removed > 0) {
                // if the currently displayed key was removed, clear UI
                if (currentDisplayedKey == null || currentDisplayedKey.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
                    clearDisplay();
                }
            }
        }));
        purgeTimeline.setCycleCount(Timeline.INDEFINITE);
        purgeTimeline.play();
    }
    private SecurityKey currentDisplayedKey = null;

    // raw token is kept for copy/mask operations; will be null when nothing is displayed
    private String currentRawToken = null;

    // timelines
    private Timeline countdownTimeline = null;
    private Timeline maskTimeline = null;

    private void refreshDisplay() {
        if (currentDisplayedKey == null) {
            clearDisplay();
            return;
        }

        createdLabel.setText(currentDisplayedKey.getCreatedAt().toString().replace('T', ' '));
        currentRawToken = currentDisplayedKey.getRawKey();
        keyField.setText(currentRawToken);
        activeLabel.setText(currentDisplayedKey.isVerified() ? "Active" : "Inactive");
        activeLabel.setStyle(currentDisplayedKey.isVerified() ? "-fx-text-fill: green;" : "-fx-text-fill: red;");

        // start expiry countdown and masking behavior
        startExpiryCountdown();
        startMaskTimer(5); // mask after 5 seconds
    }

    private void clearDisplay() {
        currentDisplayedKey = null;
        createdLabel.setText("-");
        keyField.setText("");
        currentRawToken = null;
        stopCountdowns();
        activeLabel.setText("-");
        activeLabel.setStyle("-fx-text-fill: white;");
        expiryLabel.setText("-");
    }

    private void stopCountdowns() {
        if (countdownTimeline != null) {
            countdownTimeline.stop();
            countdownTimeline = null;
        }
        if (maskTimeline != null) {
            maskTimeline.stop();
            maskTimeline = null;
        }
    }

    private void startExpiryCountdown() {
        stopCountdowns();
        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            if (currentDisplayedKey == null) return;
            java.time.Duration remaining = java.time.Duration.between(java.time.LocalDateTime.now(), currentDisplayedKey.getExpiresAt());
            if (remaining.isNegative() || remaining.isZero()) {
                clearDisplay();
                return;
            }
            long secs = remaining.getSeconds();
            expiryLabel.setText(secs + "s");
        }));
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        countdownTimeline.play();
    }

    private void startMaskTimer(int seconds) {
        if (currentRawToken == null) return;
        maskTimeline = new Timeline(new KeyFrame(Duration.seconds(seconds), ev -> {
            if (currentRawToken != null) {
                keyField.setText(maskString(currentRawToken));
            }
        }));
        maskTimeline.setCycleCount(1);
        maskTimeline.play();
    }

    private String maskString(String s) {
        if (s == null || s.length() <= 6) return "******";
        int keep = 4;
        String start = s.substring(0, keep);
        String end = s.substring(s.length() - keep);
        return start + "..." + end;
    }

    // (no sorting UI in the simplified single-key view)

    // 🔹 When Generate Key is clicked
    @FXML
    private void onGenerateKey(ActionEvent event) {
        // Generate a single active key and display it
        currentDisplayedKey = keyManager.generateKey("system", "auto-generated", true);
        refreshDisplay();
        showAlert("Success", "New security key generated successfully.");
    }

    // (Validation handled automatically when keys are generated active)

    // 🔹 When Revoke Key is clicked
    @FXML
    private void onRevokeKey(ActionEvent event) {
        if (currentDisplayedKey == null) {
            showAlert("Error", "No key is currently displayed to revoke.");
            return;
        }

        keyManager.revokeKey(currentDisplayedKey.getRawKey());
        clearDisplay();
        showAlert("Revoked", "The displayed key has been revoked.");
    }

    @FXML
    private void onCopyKey(ActionEvent event) {
        if (currentRawToken == null) {
            showAlert("Error", "No key available to copy.");
            return;
        }
        javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
        javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
        content.putString(currentRawToken);
        clipboard.setContent(content);
        showAlert("Copied", "Key copied to clipboard.");
    }

    // Expiry is handled automatically by the periodic purge task

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
