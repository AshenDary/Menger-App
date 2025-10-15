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
    private Button backButton;

    @FXML
    private javafx.scene.control.Label createdLabel;

    @FXML
    private javafx.scene.control.TextField keyField;

    @FXML
    private javafx.scene.control.Label activeLabel;

    // per-entry copy and expiry labels are created by EntryView

    @FXML
    private javafx.scene.layout.VBox entriesContainer;

    @FXML
    private javafx.scene.control.ComboBox<String> sortComboBox;

    @FXML
    private javafx.scene.control.ComboBox<String> sortDirectionComboBox;

    // no global sort UI in this simplified multi-entry view

    private final SecurityKeyManager keyManager = new SecurityKeyManager();

    @FXML
    public void initialize() {
        // populate minimal sort UI (non-intrusive)
        if (sortComboBox != null) {
            sortComboBox.getItems().addAll("Creation Date", "Expiration Date", "User ID", "Key Type", "Verification Status");
            sortComboBox.getSelectionModel().selectFirst();
        }
        if (sortDirectionComboBox != null) {
            sortDirectionComboBox.getItems().addAll("Descending", "Ascending");
            sortDirectionComboBox.getSelectionModel().selectFirst();
        }

        // Apply initial visual highlight to indicate sort controls
        applySortHighlight();

        // Initial display empty
        clearAllEntries();

        // Start a periodic purge of expired keys every 5 seconds; if current key expires, clear UI
        Timeline purgeTimeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            int removed = keyManager.purgeExpiredKeys();
            if (removed > 0) {
                // refresh the entries list to remove expired keys from UI
                refreshDisplay();
            }
        }));
        purgeTimeline.setCycleCount(Timeline.INDEFINITE);
        purgeTimeline.play();
    }
    // Keep track of entries displayed in the container
    private final java.util.Map<SecurityKey, EntryView> entryViews = new java.util.LinkedHashMap<>();

    private void refreshDisplay() {
        // render all keys currently in manager
        entriesContainer.getChildren().clear();
        entryViews.clear();
        java.util.List<SecurityKey> keys;
        if (sortComboBox != null && sortDirectionComboBox != null && sortComboBox.getValue() != null) {
            String sortBy = sortComboBox.getValue();
            SecurityKeyManager.SortBy sb = SecurityKeyManager.SortBy.CREATION_DATE;
            switch (sortBy) {
                case "Expiration Date": sb = SecurityKeyManager.SortBy.EXPIRATION_DATE; break;
                case "User ID": sb = SecurityKeyManager.SortBy.USER_ID; break;
                case "Key Type": sb = SecurityKeyManager.SortBy.KEY_TYPE; break;
                case "Verification Status": sb = SecurityKeyManager.SortBy.VERIFICATION_STATUS; break;
                case "Creation Date":
                default: sb = SecurityKeyManager.SortBy.CREATION_DATE; break;
            }

            keys = keyManager.getSortedKeys(sb);

            // apply direction: manager returns a default order; reverse for Ascending if requested
            String dir = sortDirectionComboBox.getValue();
            if ("Ascending".equals(dir)) {
                java.util.Collections.reverse(keys);
            }
        } else {
            keys = keyManager.getAllKeys();
        }

        for (SecurityKey k : keys) {
            EntryView ev = new EntryView(k);
            entryViews.put(k, ev);
            entriesContainer.getChildren().add(ev.root);
        }
    }
    private void clearAllEntries() {
        for (EntryView ev : entryViews.values()) ev.dispose();
        entryViews.clear();
        entriesContainer.getChildren().clear();
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
        SecurityKey k = keyManager.generateKey("system", "auto-generated", true);
        refreshDisplay();
        // After rendering, find the entry view and ensure it shows raw momentarily
        EntryView ev = entryViews.get(k);
        if (ev != null) ev.showRawTemporarily(5);
        showAlert("Success", "New security key generated successfully.");
    }

    // (Validation handled automatically when keys are generated active)

    // Note: global revoke button/handler removed - use per-entry Revoke buttons beside each key.

    // global copy handler removed; copy is handled per-entry

    // Expiry is handled automatically by the periodic purge task

    @FXML
    private void onSortChanged(javafx.event.ActionEvent event) {
        // Non-intrusive: just re-render using any selected sort when available
        refreshDisplay();
        applySortHighlight();
    }

    // Small UI helper: highlight the sort controls so the active sort is visually obvious
    private void applySortHighlight() {
        String highlightStyle = "-fx-border-color: #00b894; -fx-border-width: 1.5; -fx-border-radius: 4; -fx-background-color: rgba(0,184,148,0.06);";
        String normalStyle = "";

        if (sortComboBox != null) {
            if (sortComboBox.getValue() != null && !sortComboBox.getValue().isEmpty()) {
                sortComboBox.setStyle(highlightStyle);
            } else {
                sortComboBox.setStyle(normalStyle);
            }
        }

        if (sortDirectionComboBox != null) {
            if (sortDirectionComboBox.getValue() != null && !sortDirectionComboBox.getValue().isEmpty()) {
                sortDirectionComboBox.setStyle(highlightStyle);
            } else {
                sortDirectionComboBox.setStyle(normalStyle);
            }
        }
    }

    // 🔹 Back button (menu navigation)
    @FXML
    private void onBackClicked(ActionEvent event) {
        // Replace with your main menu navigation logic
        System.out.println("Back to main menu...");
    }

    // Small helper that creates a UI block for a single SecurityKey and manages its timers
    private class EntryView {
        final SecurityKey key;
        final javafx.scene.layout.HBox root;
        final javafx.scene.control.Label created;
        final javafx.scene.control.TextField keyFieldLocal;
        final javafx.scene.control.Button copyBtn;
        final javafx.scene.control.Label status;
        final javafx.scene.control.Label expires;
        final javafx.scene.control.Button revokeBtn;

        private Timeline countdown;
        private Timeline maskTimer;

        EntryView(SecurityKey k) {
            this.key = k;
            this.root = new javafx.scene.layout.HBox(10);
            this.root.setStyle("-fx-padding: 8; -fx-background-color: rgba(255,255,255,0.02); -fx-background-radius: 6;");
            this.root.setPrefWidth(740);

            this.created = new javafx.scene.control.Label(k.getCreatedAt().toString().replace('T', ' '));
            this.created.setStyle("-fx-text-fill: white;");
            this.created.setPrefWidth(140);


            this.keyFieldLocal = new javafx.scene.control.TextField();
            this.keyFieldLocal.setEditable(false);
            // allow the key field to grow so it doesn't truncate the expiry/seconds label
            this.keyFieldLocal.setMaxWidth(Double.MAX_VALUE);
            javafx.scene.layout.HBox.setHgrow(this.keyFieldLocal, javafx.scene.layout.Priority.ALWAYS);
            // show full raw key by default (no masking) and make text clearly visible on dark background
            this.keyFieldLocal.setText(k.getRawKey());
            this.keyFieldLocal.setStyle("-fx-text-fill: white; -fx-font-family: 'Monospaced'; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-color: transparent;");
            // tooltip still available for safety
            javafx.scene.control.Tooltip tt = new javafx.scene.control.Tooltip(k.getRawKey());
            this.keyFieldLocal.setTooltip(tt);

            this.copyBtn = new javafx.scene.control.Button("Copy");
            this.copyBtn.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-background-radius: 6;");
            this.copyBtn.setMinWidth(56);
            this.copyBtn.setOnAction(ev -> {
                javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
                javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                content.putString(k.getRawKey());
                clipboard.setContent(content);
                showAlert("Copied", "Key copied to clipboard.");
            });

            this.status = new javafx.scene.control.Label(k.isVerified() ? "Active" : "Inactive");
            this.status.setStyle(k.isVerified() ? "-fx-text-fill: green;" : "-fx-text-fill: red;");

            this.expires = new javafx.scene.control.Label("-");
            this.expires.setStyle("-fx-text-fill: white;");
            this.expires.setMinWidth(48);

            this.revokeBtn = new javafx.scene.control.Button("Revoke");
            this.revokeBtn.setStyle("-fx-background-color: #B22222; -fx-text-fill: white; -fx-background-radius: 8;");
            this.revokeBtn.setMinWidth(64);
            this.revokeBtn.setOnAction(ev -> {
                keyManager.revokeKey(k.getRawKey());
                dispose();
                entryViews.remove(k);
            });

            javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
            javafx.scene.layout.HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            this.root.getChildren().addAll(created, keyFieldLocal, copyBtn, status, spacer, new javafx.scene.control.Label("Expires in:"), expires, revokeBtn);

            startCountdowns();
        }

        

        void startCountdowns() {
            stopCountdowns();
            countdown = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                java.time.Duration rem = java.time.Duration.between(java.time.LocalDateTime.now(), key.getExpiresAt());
                if (rem.isNegative() || rem.isZero()) {
                    // remove from UI
                    dispose();
                    entryViews.remove(key);
                    keyManager.revokeKey(key.getRawKey());
                    return;
                }
                expires.setText(rem.getSeconds() + "s");
            }));
            countdown.setCycleCount(Timeline.INDEFINITE);
            countdown.play();

            // keep showing the full raw key (no masking)
            keyFieldLocal.setText(key.getRawKey());
        }

        void stopCountdowns() {
            if (countdown != null) { countdown.stop(); countdown = null; }
            if (maskTimer != null) { maskTimer.stop(); maskTimer = null; }
        }

        void showRawTemporarily(int seconds) {
            keyFieldLocal.setText(key.getRawKey());
            if (maskTimer != null) maskTimer.stop();
            maskTimer = new Timeline(new KeyFrame(Duration.seconds(seconds), ev -> keyFieldLocal.setText(maskString(key.getRawKey()))));
            maskTimer.setCycleCount(1);
            maskTimer.play();
        }

        void dispose() {
            stopCountdowns();
            entriesContainer.getChildren().remove(root);
        }
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
