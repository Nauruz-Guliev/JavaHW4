package ru.kpfu.itis.gnt.hwchat.client.controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.gnt.hwchat.client.Client;
import ru.kpfu.itis.gnt.hwchat.client.MessageHandler;
import ru.kpfu.itis.gnt.hwchat.models.MessageData;
import ru.kpfu.itis.gnt.hwchat.models.MessageObject;
import ru.kpfu.itis.gnt.hwchat.models.MessageType;
import ru.kpfu.itis.gnt.hwchat.utils.ImageConverter;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ChatController implements MessageHandler, Initializable {
    public TextField chatTextField;
    public Button sendButton;
    public ComboBox<String> stickerComboBox;
    public VBox chatBox;
    public HBox messageHBox;
    private Client client;

    private Double chatWidth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.chatWidth = messageHBox.getWidth() <= 0 ? messageHBox.getPrefWidth() : 600;
    }

    private void requestStickerList() {
        System.out.println("REQUEST SEND");
        client.sendMessage(MessageObject.builder().type(MessageType.AVAILABLE_STICKER_LIST).build());
    }

    @Override
    public void handleMessage(MessageObject messageObject) {
        Platform.runLater(() -> {
            switch (messageObject.getType()) {
                case REGULAR, STICKER -> {
                    HBox messageCard = createChatBox(messageObject);
                    chatBox.getChildren().add(messageCard);
                }
                case CHAT_MEMBER_CHANGE -> {
                    HBox memberChangeBox = createMemberChangeBox(messageObject.getData().getText());
                    chatBox.getChildren().add(memberChangeBox);
                }
                case AVAILABLE_STICKER_LIST -> {
                    stickerComboBox.getItems().addAll(messageObject.getData().getAvailableStickerList());
                    initStickerComboBoxListener();
                }
                default -> {
                    System.out.printf("NOT SUPPORTED YET: %1s", messageObject.getData().getText());
                }
            }
        });
    }

    @Override
    public void handleClose(String closeReason) {
        AlertDialog.builder().type(Alert.AlertType.ERROR).contentText(closeReason == null ? "Unexpected error occurred. Closing..." : closeReason).buildAndShow();
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
        this.requestStickerList();
    }

    private HBox createChatBox(MessageObject messageObject) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(chatWidth);

        VBox messageBox = new VBox();
        // content creation
        Label author = new Label(messageObject.getData().getSender());
        author.maxWidth(150F);
        Label content = new Label(messageObject.getData().getText());
        content.maxWidth(150F);
        Label date = new Label(messageObject.getData().getDateCreated().toString());
        date.maxWidth(150F);

        hBox.setStyle(" -fx-background-color: #f7f7f7;" + "    -fx-background-radius: 10;" + "    -fx-border-color: #ccc;" + "    -fx-border-radius: 10;" + "    -fx-border-width: 1;" + "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 3);" + "    -fx-padding: 10;" + "    -fx-spacing: 5;");

        String authorColor = messageObject.getData().getSender().equalsIgnoreCase("you") ? "#303F9F" : "#ff91c1";
        author.setStyle("  -fx-font-size: 12;" + "    -fx-font-weight: bold;" + "    -fx-font-family: \"Roboto\";" + "    -fx-text-fill:" + authorColor + ";");

        content.setStyle("-fx-font-size: 14;" + "    -fx-font-weight: bold;" + "    -fx-font-family: \"Roboto\";" + "    -fx-padding: 10 0 10 0;" + "    -fx-text-fill: #343434;");
        date.setStyle("    -fx-font-size: 10;" + "    -fx-font-family: \"Roboto\";" + "    -fx-text-fill: #9b9b9b;");

        ImageView sticker;
        if (messageObject.getType() == MessageType.STICKER && messageObject.getData().getSticker() != null) {
            sticker = createStickerView(ImageConverter.decodeFromString(messageObject.getData().getSticker().encodedSticker()));
            messageBox.getChildren().addAll(author, sticker, date);
        } else {
            messageBox.getChildren().addAll(author, content, date);
        }
        hBox.getChildren().add(messageBox);
        return hBox;
    }

    private ImageView createStickerView(InputStream imageInputStream) {
        ImageView imageView = new ImageView();
        Image image = new Image(imageInputStream);
        imageView.setImage(image);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private HBox createMemberChangeBox(String message) {
        HBox hbox = new HBox();
        Label label = new Label(message);
        hbox.setPrefWidth(chatWidth);
        HBox.setHgrow(label, Priority.ALWAYS);
        hbox.setAlignment(Pos.TOP_CENTER);
        label.setStyle("  -fx-font-family: 'Roboto';" + "       -fx-font-size: 12px;" + "       -fx-text-fill: #484747;");
        hbox.getChildren().add(label);
        return hbox;
    }


    public void onSendClicked(MouseEvent mouseEvent) {
        String message = chatTextField.getText();
        if (message.equals("") || message.equals(" ")) {
            return;
        }
        MessageObject messageObject = MessageObject.builder().type(MessageType.REGULAR).data(MessageData.builder().sender(client.getUserName()).dateCreated(new Date()).text(message).build()).build();
        client.sendMessage(messageObject);
        chatTextField.setText("");
    }

    private void initStickerComboBoxListener() {
        stickerComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            client.sendMessage(
                    MessageObject.builder()
                            .type(MessageType.STICKER)
                            .data(
                                    MessageData.builder()
                                            .text(newValue)
                                            .sender(client.getUserName())
                                            .dateCreated(new Date())
                                            .build()
                            )
                            .build()
            );
            stickerComboBox.getSelectionModel().clearSelection();
        });
    }
}
