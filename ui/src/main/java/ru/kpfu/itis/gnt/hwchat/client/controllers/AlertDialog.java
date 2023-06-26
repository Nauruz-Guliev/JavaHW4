package ru.kpfu.itis.gnt.hwchat.client.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class AlertDialog {
    private Alert.AlertType alertType;
    private final String headerText;
    private final String contentText;
    private final String alertTitle;

    private AlertDialog(Builder builder) {
        this.alertType = builder.alertType;
        this.headerText = builder.headerText;
        this.alertTitle = builder.alertTitle;
        this.contentText = builder.contentText;
    }

    public static AlertType builder() {
        return new Builder();
    }

    public interface AlertType {
        ContentText type(Alert.AlertType type);
    }

    public interface ContentText {
        Build contentText(String contentText);
    }

    public interface Build {
        Build alertTitle(String contentTitle);

        Build headerText(String headerText);

        void buildAndShow();
    }

    private static class Builder implements AlertType, ContentText, Build {
        private Alert.AlertType alertType;
        private String headerText;
        private String contentText;
        private String alertTitle;

        @Override
        public ContentText type(Alert.AlertType type) {
            this.alertType = type;
            return this;
        }

        @Override
        public Build contentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        @Override
        public Build alertTitle(String alertTitle) {
            this.alertTitle = alertTitle;
            return this;
        }

        @Override
        public Build headerText(String headerText) {
            this.headerText = headerText;
            return this;
        }

        @Override
        public void buildAndShow() {
            new AlertDialog(this).createAlertDialog();
        }
    }


    public void createAlertDialog() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(alertType);
                    switch (alertType) {
                        case ERROR -> {
                            alert.setTitle(alertTitle == null ? "Error" : alertTitle);
                            alert.setHeaderText(headerText == null ? "Error occurred" : headerText);
                            alert.setOnHidden(event -> System.exit(0));
                            alert.setContentText(contentText);
                        }
                        case WARNING -> {
                            alert.setTitle(alertTitle == null ? "Warning" : alertTitle);
                            alert.setHeaderText(headerText == null ? "Pay attention!" : headerText);
                            alert.setContentText(contentText);
                        }
                        case INFORMATION -> {
                            alert.setTitle(alertTitle == null ? "Info" : alertTitle);
                            alert.setHeaderText(null);
                            alert.setContentText(contentText);
                        }
                        default -> {
                            System.err.println("This type of alert is not supported");
                        }
                    }
                    alert.showAndWait();
                }
        );
    }
}
