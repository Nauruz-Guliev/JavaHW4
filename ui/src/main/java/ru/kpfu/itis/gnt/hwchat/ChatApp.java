package ru.kpfu.itis.gnt.hwchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.kpfu.itis.gnt.hwchat.client.controllers.RegistrationController;

import java.io.IOException;

public class ChatApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApp.class.getResource("registration-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        ((RegistrationController) fxmlLoader.getController()).setStage(stage);
        stage.getIcons().add(new Image(ChatApp.class.getResourceAsStream("icon.png")));
        stage.setTitle("Websockets Chat App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}