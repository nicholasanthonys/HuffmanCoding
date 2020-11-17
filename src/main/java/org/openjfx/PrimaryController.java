package org.openjfx;

import java.io.IOException;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;


public class PrimaryController {
    @FXML private JFXTextField inputText;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void onButtonPressed() {
        inputText.clear();
        System.out.println(inputText.getText());
    }
}
