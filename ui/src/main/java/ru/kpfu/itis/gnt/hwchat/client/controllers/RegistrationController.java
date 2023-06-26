package ru.kpfu.itis.gnt.hwchat.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kpfu.itis.gnt.hwchat.ChatApp;
import ru.kpfu.itis.gnt.hwchat.client.Client;
import ru.kpfu.itis.gnt.hwchat.client.MessageHandler;
import ru.kpfu.itis.gnt.hwchat.models.MessageData;
import ru.kpfu.itis.gnt.hwchat.models.MessageObject;
import ru.kpfu.itis.gnt.hwchat.models.MessageType;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class RegistrationController implements MessageHandler, Initializable {
    public Button registerButton;
    public TextField registerTextField;
    private Stage stage;
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client(this);
        new Thread(client).start();
    }

    @Override
    public void handleMessage(MessageObject messageObject) {
        switch (messageObject.getType()) {
            case REGISTRATION_SUCCESS -> {
                navigateToChat(messageObject);
            }
            case ERROR -> {
                AlertDialog.builder()
                        .type(Alert.AlertType.WARNING)
                        .contentText(messageObject.getData().getText())
                        .buildAndShow();
            }
            case CHAT_MEMBER_CHANGE -> {
                System.out.println(messageObject.getData().getText());
            }
            default -> {
                AlertDialog.builder()
                        .type(Alert.AlertType.INFORMATION)
                        .contentText(messageObject.getData().getText())
                        .buildAndShow();
            }
        }
    }

    private void navigateToChat(MessageObject messageObject) {
        Platform.runLater(
                () -> {
                    try {
                        // навигируемся
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(ChatApp.class.getResource("chat-view.fxml"));
                        VBox vbox = loader.load();
                        ChatController controller = loader.getController();
                        controller.setClient(client);
                        client.setMessageHandler(controller);
                        client.setUserName(messageObject.getData().getSender());
                        Scene scene = new Scene(vbox);
                        scene.getStylesheets().add(ChatApp.class.getResource("main.css").toExternalForm());
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException exception) {
                        AlertDialog.builder()
                                .type(Alert.AlertType.ERROR)
                                .contentText("Failed to open the chat due to : " + exception.getMessage())
                                .buildAndShow();
                    }
                });
    }

    @Override
    public void handleClose(String closeReason) {
        AlertDialog.builder()
                .type(Alert.AlertType.ERROR)
                .contentText(closeReason == null ? "Unexpected error occurred. Closing..." : closeReason)
                .buildAndShow();
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onSubmitClicked(MouseEvent mouseEvent) {
        try {
            String userName = registerTextField.getText();
            if (userName.equals("") || userName.equals(" ")) {
                AlertDialog.builder()
                        .type(Alert.AlertType.WARNING)
                        .contentText("Nickname can not be empty")
                        .buildAndShow();
            } else if (userName.startsWith("You") || userName.startsWith("you")) {
                AlertDialog.builder()
                        .type(Alert.AlertType.WARNING)
                        .contentText("\"You\" is reserved username. Try using another one.")
                        .buildAndShow();
            } else {
                // регистрируемся
                client.sendMessage(
                        MessageObject.builder()
                                .type(MessageType.REGISTRATION)
                                .data(
                                        MessageData.builder()
                                                .text(userName)
                                                .sender(userName)
                                                .dateCreated(new Date())
                                                .build()
                                )
                                .build()
                );
            }
        } catch (Exception exception) {
            AlertDialog.builder()
                    .type(Alert.AlertType.ERROR)
                    .contentText("Error on the registration stage. Reason: " + exception.getMessage())
                    .buildAndShow();
        }
    }


}
