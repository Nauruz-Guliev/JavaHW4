package ru.kpfu.itis.gnt.hwchat.server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.String.format;

import javax.websocket.EndpointConfig;
import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.server.Server;
import ru.kpfu.itis.gnt.hwchat.models.MessageData;
import ru.kpfu.itis.gnt.hwchat.models.MessageObject;
import ru.kpfu.itis.gnt.hwchat.models.MessageType;
import ru.kpfu.itis.gnt.hwchat.serialization.MessageDecoder;
import ru.kpfu.itis.gnt.hwchat.serialization.MessageEncoder;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatServer {
    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8888;
    private static final String CONTEXT_PATH = "";
    // key = id, value = nickname
    private HashMap<String, String> users;

    public static void main(String[] args) {
        new ChatServer();
    }

    public ChatServer() {
        Server server = new Server(HOST_NAME, PORT, CONTEXT_PATH, null, this.getClass());
        try {
            users = new HashMap<>();
            server.start();
            new Scanner(System.in).nextLine();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            server.stop();
        }
    }


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.printf("User with id %1s has connected. ", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.printf("Error occurred for user with nickname \" %1s \" . Reason: %2s\n", users.get(session.getId()), throwable.getMessage());
    }

    @OnMessage
    public MessageObject onMessage(MessageObject message, Session session) {
        switch (message.getType()) {
            case REGULAR -> {
                broadcastMessage(message, session, true);
            }
            case REGISTRATION -> {
                // сохраняем пользователя
                if (!users.containsValue(message.getData().getSender())) {
                    // сохраняем ник и айди сессии
                    users.put(session.getId(), message.getData().getSender());
                    // себе отправляем сообщение об успешном подключении
                    session.getAsyncRemote().sendObject(MessageObject.builder().type(MessageType.CONNECTION_SUCCESS).build());
                    // других уведомляем о том, что мы зашли
                    broadcastMessage(MessageObject.builder()
                            .type(MessageType.CHAT_MEMBER_CHANGE)
                            .data(MessageData.builder()
                                    .text("User with nickname " + message.getData().getSender() + " has entered the chat.")
                                    .sender(message.getData().getSender())
                                    .dateCreated(message.getData().getDateCreated())
                                    .build())
                            .build(), session, false);
                } else {
                    // если пользователь с таким ником существует
                    session.getAsyncRemote().sendObject(
                            MessageObject.builder()
                                    .data(MessageData.builder()
                                            .dateCreated(new Date())
                                            .text("User with nickname " + users.get(session.getId()) + " already exists. ")
                                            .build())
                                    .type(MessageType.ERROR)
                                    .build()
                    );
                }
            }
            case DISCONNECT_USER -> {
                try {
                    session.getOpenSessions().remove(session);
                    session.close();
                    users.remove(session.getId());
                } catch (IOException ex) {
                    System.err.printf("Failed to disconnect user with nickname %1s ", users.get(session.getId()));
                }
            }
            default -> {
                System.out.printf("Unknown message received from the user. \n Here what it says: %1s", message);
            }
        }
        return null;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.printf("User with nickname \"%1s\" has disconnected with reason %2s \n", users.get(session.getId()), closeReason.getReasonPhrase());
        try {
            // сообщаем всем о выходе пользователя из чата
            broadcastMessage(MessageObject.builder()
                    .data(MessageData.builder()
                            .text("User with nickname " + users.get(session.getId()) + " has left the chat.")
                            .sender(users.get(session.getId()))
                            .build())
                    .type(MessageType.CHAT_MEMBER_CHANGE)
                    .build(), session, false);
            users.remove(session.getId());
            session.close();
        } catch (IOException ex) {
            System.err.printf("Failed to close user session. Reason %1s", ex.getMessage());
        }
    }

    private void broadcastMessage(MessageObject messageObject, Session session, boolean sendSelf) {
        session.getOpenSessions().forEach(
                peer -> {
                    try {
                        if (peer.getId().equals(session.getId()) && sendSelf) {
                            // себе отправляем новый экземпляр, заменив имя
                            peer.getBasicRemote().sendObject(
                                    MessageObject.builder()
                                            .data(MessageData.builder()
                                                    .text(messageObject.getData().getText())
                                                    .sender("You")
                                                    .dateCreated(messageObject.getData().getDateCreated())
                                                    .build())
                                            .type(messageObject.getType())
                                            .build()
                            );
                        } else {
                            peer.getBasicRemote().sendObject(messageObject);
                        }
                    } catch (IOException | EncodeException e) {
                        System.err.printf("Error occurred while broadcasting message. \n Sender nickname: %1s. \n Receiver nickname: %2s \n. Reason: %3s\n",
                                users.get(session.getId()),
                                users.get(peer.getId()),
                                e.getMessage()
                        );
                    }
                }
        );
    }
}
