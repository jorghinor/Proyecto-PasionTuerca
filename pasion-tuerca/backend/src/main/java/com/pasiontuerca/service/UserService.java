package com.pasiontuerca.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 Very small in-memory user store for demo purposes.
 Username: admin  Password: admin123
 Username: user   Password: user123
*/
@Service
public class UserService {
    private final Map<String, String> users = new HashMap<>();

    public UserService() {
        users.put("admin", "admin123");
        users.put("user", "user123");
    }

    public boolean validateCredentials(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
