package ru.kpfu.itis.gnt.hwchat.client;

import ru.kpfu.itis.gnt.hwchat.models.MessageObject;
import ru.kpfu.itis.gnt.hwchat.models.MessageData;
import ru.kpfu.itis.gnt.hwchat.models.MessageType;
import ru.kpfu.itis.gnt.hwchat.utils.MessageDecoder;
import ru.kpfu.itis.gnt.hwchat.utils.MessageEncoder;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class Client implements Runnable {
    private static final String CHAT_END_POINT = "ws://localhost:8888/chat";
    private volatile Session userSession = null;
    private MessageHandler messageHandler;
    private String userName;

    public Client(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, new URI(CHAT_END_POINT));
        } catch (Exception e) {
            System.err.printf("Connection failure occurred. Reason: %1s ", e.getMessage());
        }
    }


    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        try {
            this.userSession.close();
            this.userSession = null;
        } catch (IOException ex) {
            System.out.printf("Error occurred while closing the session. Reason: %1s ", ex.getMessage());
        }
    }

    @OnMessage
    public void onMessage(MessageObject message) {
        messageHandler.handleMessage(message);
    }


    public void sendMessage(MessageObject message) {
        new Thread(() -> userSession.getAsyncRemote().sendObject(message)).start();
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
