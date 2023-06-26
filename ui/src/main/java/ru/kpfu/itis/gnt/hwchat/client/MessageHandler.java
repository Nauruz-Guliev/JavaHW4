package ru.kpfu.itis.gnt.hwchat.client;

import ru.kpfu.itis.gnt.hwchat.models.MessageObject;

import javax.websocket.CloseReason;
import javax.websocket.Session;

public interface MessageHandler {

    void handleMessage(MessageObject messageObject);

    void handleClose(String closeReason);

    void setClient(Client client);
}
