package com.example.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class SecurityKeyManager {
    private final List<SecurityKey> keys = new ArrayList<>();

    /** Simple enum to pick sorting strategy */
    public enum SortBy {
        CREATION_DATE,
        EXPIRATION_DATE,
        USER_ID,
        KEY_TYPE,
        VERIFICATION_STATUS
    }

    /** Add a new key */
    public void addKey(SecurityKey key) {
        keys.add(key);
    }

    /**
     * Generate and add a new random key for a user. Returns the created SecurityKey.
     * If 'active' is true the created key will be marked as verified.
     */
    public SecurityKey generateKey(String userId, String keyType, boolean active) {
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String fullKey = userId + "_" + randomPart;
        SecurityKey key = new SecurityKey(userId, keyType, active, fullKey);
        addKey(key);
        return key;
    }

    /** Validate a key by checking if it exists and not expired */
    public boolean validateKey(String rawKey) {
        return keys.stream()
                .anyMatch(k -> k.getRawKey().equals(rawKey) && 
                               k.getExpiresAt().isAfter(java.time.LocalDateTime.now()));
    }

    /** Revoke (remove) a key by its raw value */
    public void revokeKey(String rawKey) {
        keys.removeIf(k -> k.getRawKey().equals(rawKey));
    }

    /**
     * Mark a key as verified (active) by its raw key value. Returns true if found and updated.
     */
    public boolean setVerifiedByRawKey(String rawKey, boolean verified) {
        for (SecurityKey k : keys) {
            if (k.getRawKey().equals(rawKey)) {
                k.setVerified(verified);
                return true;
            }
        }
        return false;
    }

    /** Return all keys */
    public List<SecurityKey> getAllKeys() {
        return new ArrayList<>(keys);
    }

    /** Sorting logic using the local SortBy enum */
    public List<SecurityKey> getSortedKeys(SortBy sortBy) {
        Comparator<SecurityKey> comparator;

        switch (sortBy) {
            case EXPIRATION_DATE:
                comparator = Comparator.comparing(SecurityKey::getExpiresAt);
                break;
            case USER_ID:
                comparator = Comparator.comparing(SecurityKey::getUserId);
                break;
            case KEY_TYPE:
                comparator = Comparator.comparing(SecurityKey::getKeyType);
                break;
            case VERIFICATION_STATUS:
                comparator = Comparator.comparing(SecurityKey::isVerified).reversed();
                break;
            case CREATION_DATE:
            default:
                comparator = Comparator.comparing(SecurityKey::getCreatedAt).reversed();
        }

        return keys.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Remove expired keys and return the number removed.
     */
    public int purgeExpiredKeys() {
        int before = keys.size();
        LocalDateTime now = LocalDateTime.now();
        keys.removeIf(k -> k.getExpiresAt().isBefore(now) || k.getExpiresAt().isEqual(now));
        return before - keys.size();
    }

    public void generateRandomKey() {
        // Generate a random key for a default system user.
        // Use the existing helper so the generation logic stays consistent.
    generateKey("system", "auto-generated", false);
        // Optionally you could customize expiry or verification here.
        // For now we leave defaults (30 days expiry, not verified).
        // Keep the returned key available in case callers want to use it.
        // (The controller currently only triggers generation, so no return value required.)
    }

    /**
     * Generate a random key for the default system user and mark it active.
     * Returns the created SecurityKey.
     */
    public SecurityKey generateRandomActiveKey() {
        SecurityKey k = generateKey("system", "auto-generated", true);
        k.setVerified(true);
        return k;
    }
}
