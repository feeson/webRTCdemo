package com.example.webrtcdemo.controller;

import com.alibaba.fastjson2.JSON;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

public class MyWebSocketHandler implements WebSocketHandler {
    static int cnt = 0;
    private Map<String,WebSocketSession> webSocketSessions=new HashMap();
    @Override
    public void afterConnectionEstablished(
            WebSocketSession session) throws Exception {
//        String uuid= UUID.randomUUID().toString();
        System.out.println(cnt);
        webSocketSessions.put(String.valueOf(cnt), session);
        cnt++;
//        //分配id
//        session.sendMessage(new TextMessage(JSON.toJSONString(uuid)));
    }

    @Override
    public void handleMessage(WebSocketSession session,
                              WebSocketMessage<?> message) throws Exception {
        message.getPayload();
//        System.out.println("payload: \n\t"+message);
        Message msgO= JSON.parseObject((String) message.getPayload(),Message.class);
        System.out.println("message: ");
        System.out.println(msgO.toString());
        if (msgO.messageId.equals("PROXY")){
            WebSocketSession toConn = webSocketSessions.get(
                    msgO.toPeerId);
            if (toConn!=null)
                toConn.sendMessage(new TextMessage(JSON.toJSONString(msgO)));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
//        webSocketSessions.forEach((s, webSocketSession) -> {
//            Message message=new Message();
//            message.messageId="PEER_LEAVE";
//            Message.MessageData messageData=message.getMessageData();
//            messageData.peerId="LEVING UUID";
//            message.messageData=messageData;
//            try {
//                webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(message)));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
