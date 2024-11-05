package com.example.ToYokoNa.filter;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }

    @Override
    //sessionDestroyed → セッションが無効にされようとしているという通知を受け取る。
    public void sessionDestroyed(HttpSessionEvent event) {
        // セッション破棄時に、SessionManagerから該当するセッションを削除
        String sessionId = event.getSession().getId();

        // 全ユーザのセッションをチェックし、破棄されたセッションを削除
        // SessionManagerで扱っているMapからキーと値のペアをセットとして取得し(entrySet())、
        // entry(Map)のValueを取得し、それが上で取得したsessionIdと同じだった場合削除する。
        SessionManager.userSessions.entrySet().removeIf(entry -> entry.getValue().equals(sessionId));
    }
}
