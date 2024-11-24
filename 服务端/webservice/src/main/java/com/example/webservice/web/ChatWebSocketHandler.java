package com.example.webservice.web;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 用于保存用户和会话的映射
    private static final Map<WebSocketSession, String> userSessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从连接参数中获取用户名，例如 ws://localhost:8080/chat?username=John
        String username = session.getUri().getQuery().split("=")[1];
        userSessions.put(session, username);
        System.out.println(username + " connected with session ID: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = userSessions.get(session);
        String payload = message.getPayload();
        System.out.println("Received message from " + username + ": " + payload);

        // 构造带用户名的消息
        String formattedMessage = username + ": " + payload;

        // 广播消息给所有连接
        synchronized (userSessions) {
            for (WebSocketSession s : userSessions.keySet()) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(formattedMessage));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = userSessions.get(session);
        userSessions.remove(session);
        System.out.println(username + " disconnected (Session ID: " + session.getId() + ")");
    }
}
