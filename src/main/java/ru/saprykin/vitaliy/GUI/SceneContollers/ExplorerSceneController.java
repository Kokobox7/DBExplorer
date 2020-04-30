package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExplorerSceneController extends SceneController {
    static boolean guest;

    @FXML
    private Label profileInformer;

    public void init(boolean guest, String login) {
        if (guest) {
            ExplorerSceneController.guest = true;
            profileInformer.setText("Welcome, guest.");
        } else {
            ExplorerSceneController.guest = false;
            profileInformer.setText("Welcome, " + login + ".");
        }
    }
}
