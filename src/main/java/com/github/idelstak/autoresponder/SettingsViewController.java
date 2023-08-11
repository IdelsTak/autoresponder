package com.github.idelstak.autoresponder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SettingsViewController {
    @FXML
    private TextField filtersField;
    @FXML
    private TextArea autoReplyTextArea;

    @FXML
    protected void saveSettings(ActionEvent actionEvent) {
    }
}
