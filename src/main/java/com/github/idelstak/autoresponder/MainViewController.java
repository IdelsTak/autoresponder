package com.github.idelstak.autoresponder;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.Timer;

public class MainViewController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    private AutoResponderApplication responderApplication;

    @FXML
    protected void startAutoresponder(ActionEvent actionEvent) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        try {
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore();
            String myEmail = emailField.getText();
            String password = passwordField.getText();
            store.connect("imap.yandex.com", 993, myEmail, password);

            Timer autoRespondTimer = new Timer();
            responderApplication.setAutoRespondTimer(autoRespondTimer);
            autoRespondTimer.schedule(new AutoresponderTask(store, myEmail, password), 0, 30000);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle connection errors
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Autoresponder");
        alert.setHeaderText(null);
        alert.setContentText("Autoresponder started successfully!");
        Stage stage = (Stage) emailField.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    @FXML
    protected void openSettings(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Parent settingsRoot = loader.load();
        Stage settingsStage = new Stage();
        settingsStage.setScene(new Scene(settingsRoot));
        settingsStage.setTitle("Settings");

        Stage stage = (Stage) emailField.getScene().getWindow();

        settingsStage.initOwner(stage);
        settingsStage.showAndWait();
    }

    public void setApplication(AutoResponderApplication responderApplication) {
        this.responderApplication = responderApplication;
    }
}