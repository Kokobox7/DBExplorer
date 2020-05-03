package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;

public class ExplorerSceneController extends SceneController {
    private boolean guest;
    private final String login;
    private final Connection exploredDBConnection;

    @FXML
    private Label profileInformer;

    public ExplorerSceneController(boolean guest, String login, Connection exploredDBConnection) {
        this.guest = guest;
        this.login = login;
        this.exploredDBConnection = exploredDBConnection;
    }

    public void init() {
        if (guest) {
            profileInformer.setText("Welcome, guest.");
        } else {
            profileInformer.setText("Welcome, " + login + ".");
        }
    }
}
