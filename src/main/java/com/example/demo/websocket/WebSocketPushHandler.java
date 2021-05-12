package com.example.demo.websocket;

/**
 * @Author: 11655
 * @Date: 2021/5/6 22:09
 * @Description:
 */

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author buhao
 * @version MyWSHandler.java, v 0.1 2019-10-17 17:10 buhao
 */
@Component
public class WebSocketPushHandler extends TextWebSocketHandler {

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    //方法是在 socket 连接成功后被触发，同原生注解里的 @OnOpen 功能
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = session.getUri().getQuery().split("token=")[1];
        if (token != null) {
            // 用户连接成功，放入在线用户缓存
            WeSessionManager.add(token.toString(), session);
        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
//    方法是在客户端发送信息时触发，同原生注解里的 @OnMessage 功能
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        String token = session.getUri().getQuery().split("token=")[1];
        System.out.println("[server 接收到 " + token + " 发送的 ]" + payload);
        Thread.sleep(200);
        session.sendMessage(new TextMessage("[Server 发送给 " + token + " 消息]" + Math.random() + " ____ " + LocalDateTime.now().toString()));
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
//    方法是在 socket 连接关闭后被触发，同原生注解里的 @OnClose 功能
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String token = session.getUri().getQuery().split("token=")[1];
        if (token != null) {
            // 用户退出，移除缓存
            WeSessionManager.remove(token.toString());
        }
    }


}
