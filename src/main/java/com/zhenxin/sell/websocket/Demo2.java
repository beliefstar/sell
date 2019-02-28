package com.crc.web.buyer.dto.pay;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xzhen
 * @created 17:07 27/02/2019
 * @description TODO
 */
@Data
@Slf4j
@Component
@ServerEndpoint("/payment/cashwith")
public class NotifyWebSocket {

    private String paymentBillNo;

    private Session session;

    public static ConcurrentHashMap<String, NotifyWebSocket> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void OnOpen(Session session) {
        List<String> stringList = session.getRequestParameterMap().get("paymentBillNo");
        String paymentBillNo = String.join("", stringList);
        if (StringUtils.isEmpty(paymentBillNo)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.paymentBillNo = paymentBillNo;
        this.session = session;
        log.info("socket open: {}", paymentBillNo);
        sessions.put(paymentBillNo, this);
    }

    @OnClose
    public void onClose() {
        log.info("socket is broken");
        sessions.remove(paymentBillNo);
    }

    @OnMessage
    public void onMessage(String text) {
        log.info("【客户端: {} 发送 socket】: {}", paymentBillNo, text);
    }

    public void sendMsg(String msg) {
        try {
            log.info("send paymentBillNo: {}, msg: {}", this.paymentBillNo, msg);
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String paymentBillNo, String msg) {
        NotifyWebSocket webSocket = sessions.get(paymentBillNo);
        if (webSocket != null) {
            webSocket.sendMsg(msg);
        }
    }
}
