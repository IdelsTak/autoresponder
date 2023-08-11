package com.github.idelstak.autoresponder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

public class AutoResponderApplication extends Application {

    private Timer autoRespondTimer;

    public void setAutoRespondTimer(Timer autoRespondTimer) {
        this.autoRespondTimer = autoRespondTimer;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        MainViewController controller = fxmlLoader.getController();

        System.out.println("controller: " + controller);

        controller.setApplication(this);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Autoresponder");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stopping auto responder: " + autoRespondTimer + "...");

        if (autoRespondTimer != null) {
            autoRespondTimer.cancel();
            System.out.println("auto responder has been cancelled");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}