module ru.kpfu.itis.gnt.hwchat {
    requires javafx.controls;
    requires javafx.fxml;

    requires tyrus.server;
    requires tyrus.client;
    requires tyrus.container.grizzly.server;
    requires javax.websocket.api;
    requires tyrus.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires tyrus.container.grizzly.client;
    requires grizzly.http.server;
    requires java.desktop;
    requires lombok;

    exports ru.kpfu.itis.gnt.hwchat.models;
    exports ru.kpfu.itis.gnt.hwchat.serialization;
    exports ru.kpfu.itis.gnt.hwchat.client;
    exports ru.kpfu.itis.gnt.hwchat.server;
}
