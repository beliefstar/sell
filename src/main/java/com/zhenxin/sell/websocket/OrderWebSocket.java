package com.zhenxin.sell.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/webSocket")
public class OrderWebSocket {

    private Session session;

    private static CopyOnWriteArraySet<Session> socketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen (Session session) {
        log.info("socket open, session={}", session);
        socketSet.add(session);
        this.session = session;
    }

    @OnClose
    public void onClose() {
        log.info("socket is broken");
        socketSet.remove(session);
    }

    @OnMessage
    public void onMessage(String text) {
        log.info("【客户端发送 socket】: {}", text);
    }

    public void sendMessage(String msg) {
        for (Session s : socketSet) {
            try {
                s.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.error("【发送 WebSocket 失败】: {}", e.getMessage());
            }
        }
    }
}
