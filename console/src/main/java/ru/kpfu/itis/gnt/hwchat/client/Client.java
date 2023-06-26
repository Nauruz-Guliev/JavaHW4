package ru.kpfu.itis.gnt.hwchat.client;

import ru.kpfu.itis.gnt.hwchat.models.MessageObject;
import ru.kpfu.itis.gnt.hwchat.models.MessageData;
import ru.kpfu.itis.gnt.hwchat.models.MessageType;
import ru.kpfu.itis.gnt.hwchat.serialization.MessageDecoder;
import ru.kpfu.itis.gnt.hwchat.serialization.MessageEncoder;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class Client implements Runnable {
    private static final String CHAT_END_POINT = "ws://localhost:8888/chat";
    private Session userSession = null;
    private volatile boolean isConnected;

    public static void main(String[] args) {
        new Thread(new Client()).start();
    }

    @Override
    public void run() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, new URI(CHAT_END_POINT));
            runConsoleInteraction();
        } catch (Exception e) {
            System.err.printf("Connection failure occurred. Reason: %1s ", e.getMessage());
        }
    }

    private void runConsoleInteraction() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** CHAT APPLICATION *** \n");
        System.out.println("* !! To disconnect from chat type \"EXIT\" with all caps !! *\n");
        while (userSession == null) {
            Thread.onSpinWait();
        }
        System.out.print("Enter your chat nickname: ");
        String nickName = sc.nextLine();
        sendMessage(MessageObject.builder()
                .data(MessageData.builder()
                        .sender(nickName)
                        .text(nickName)
                        .build())
                .type(MessageType.REGISTRATION)
                .build());
        while (userSession != null) {
            String message = sc.nextLine();
            //сообщение о выходе из чата
            if (message.equals("EXIT")) {
                sendMessage(MessageObject.builder()
                        .data(MessageData.builder()
                                .sender(nickName)
                                .text(message)
                                .build())
                        .type(MessageType.DISCONNECT_USER)
                        .build());
            } else {
                // простое текстовое сообщение
                sendMessage(MessageObject.builder()
                        .data(MessageData.builder()
                                .sender(nickName)
                                .text(message)
                                .build())
                        .type(MessageType.REGULAR)
                        .build());
            }
        }
    }


    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        try {
            this.isConnected = false;
            this.userSession.close();
            this.userSession = null;
        } catch (IOException ex) {
            System.out.printf("Error occurred while closing the session. Reason: %1s ", ex.getMessage());
        }
    }

    @OnMessage
    public void onMessage(MessageObject message) {
        switch (message.getType()) {
            case CONNECTION_SUCCESS -> {
                isConnected = true;
            }
            case REGULAR -> {
                if (isConnected) {
                    System.out.printf("%1s : %2s \n", message.getData().getSender(), message.getData().getText());
                }
            }
            case CHAT_MEMBER_CHANGE -> {
                if (isConnected) {
                    System.out.println("\n" + message.getData().getText());
                }
            }
            case ERROR -> {
                System.err.printf("\nError occurred: %1s \n", message.getData().getText());
            }
            default -> {
                System.out.printf("Unsupported message received from the server. Here what is says: %1s \n", message.getData().getText());
            }
        }
    }

    private void sendMessage(MessageObject message) {
        new Thread(() -> userSession.getAsyncRemote().sendObject(message)).start();
    }
}
