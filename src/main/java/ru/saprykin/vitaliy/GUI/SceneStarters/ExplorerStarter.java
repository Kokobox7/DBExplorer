package ru.saprykin.vitaliy.GUI.SceneStarters;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.saprykin.vitaliy.GUI.SceneContollers.AuthSceneController;
import ru.saprykin.vitaliy.GUI.SceneContollers.ExplorerSceneController;

import java.io.IOException;
import java.net.URL;

public class ExplorerStarter {
    public void startExplorerScene(boolean guest, String login, String password) {
        ExplorerSceneController sceneController = new ExplorerSceneController();
        SceneStarter.start(sceneController, "/ExplorerScene.fxml", "DBExplorer", true);
        sceneController.init(guest, login, password);
    }
}
