package com.example.ToYokoNa.filter;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    // アカウント名とセッションIDのペアを管理する
    static final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

    public static String getSessionId(String account) {
        return userSessions.get(account);
    }

    public static void addSession(String account, String sessionId) {
        userSessions.put(account, sessionId);
    }

    public static void removeSession(String account) {
        userSessions.remove(account);
    }
}