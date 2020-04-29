package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.saprykin.vitaliy.GUI.MainGUI;

public class AuthSceneController {
    @FXML
    private Button mainButton;

    @FXML
    private void buttonClicked() {
        mainButton.setText("Button clicked!");
        MainGUI.startMainScene();
    }
}
