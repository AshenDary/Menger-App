package com.example.model;

import java.util.*;
import java.util.stream.Collectors;

public class SecurityKeyManager {
    private final List<SecurityKey> keys = new ArrayList<>();

    /** Add a new key */
    public void addKey(SecurityKey key) {
        keys.add(key);
    }

    /** Generate and add a new random key for a user */
    public SecurityKey generateKey(String userId, String keyType) {
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String fullKey = userId + "_" + randomPart;
        SecurityKey key = new SecurityKey(userId, keyType, false, fullKey);
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

    /** Return all keys */
    public List<SecurityKey> getAllKeys() {
        return new ArrayList<>(keys);
    }

    /** Sorting logic */
    public List<SecurityKey> getSortedKeys(String sortBy) {
        Comparator<SecurityKey> comparator;

        switch (sortBy) {
            case "Expiration Date":
                comparator = Comparator.comparing(SecurityKey::getExpiresAt);
                break;
            case "User ID":
                comparator = Comparator.comparing(SecurityKey::getUserId);
                break;
            case "Key Type":
                comparator = Comparator.comparing(SecurityKey::getKeyType);
                break;
            case "Verification Status":
                comparator = Comparator.comparing(SecurityKey::isVerified).reversed();
                break;
            default: // Creation Date
                comparator = Comparator.comparing(SecurityKey::getCreatedAt).reversed();
        }

        return keys.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /** Get only verified keys */
    public List<SecurityKey> getVerifiedKeys() {
        return keys.stream().filter(SecurityKey::isVerified).collect(Collectors.toList());
    }
}
