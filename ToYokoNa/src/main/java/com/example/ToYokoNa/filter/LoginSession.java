package com.example.ToYokoNa.filter;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginSession {

    private final ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();

    public boolean alreadyLogin(String account) {
        return users.containsKey(account);
    }

    public void registerSession(String account, HttpSession session) {
        // 他のセッションが存在する場合は上書きする
        users.put(account, session.getId());
    }

    public void removeSession(String account) {
        users.remove(account);
    }
}
