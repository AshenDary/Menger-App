package com.example.model;

import java.time.LocalDateTime;

public class SecurityKey {
    private String userId;
    private String keyType;
    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String rawKey;

    public SecurityKey(String userId, String keyType, boolean verified, String rawKey) {
        this.userId = userId;
        this.keyType = keyType;
        this.verified = verified;
        this.rawKey = rawKey;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusDays(30); // Default 30-day validity
    }

    public String getUserId() {
        return userId;
    }

    public String getKeyType() {
        return keyType;
    }

    public boolean isVerified() {
        return verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getRawKey() {
        return rawKey;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return userId + " | " + keyType + " | " + (verified ? "✔" : "✘");
    }
}
