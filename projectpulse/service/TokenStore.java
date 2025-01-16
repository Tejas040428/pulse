package com.cds.projectpulse.service;

import java.util.HashSet;
import java.util.Set;

public class TokenStore {
    private final Set<String> blacklistedTokens = new HashSet<>();

    public void addTokenToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
