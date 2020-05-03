package ru.saprykin.vitaliy.GUI.SceneStarters;

import ru.saprykin.vitaliy.GUI.SceneContollers.ExplorerSceneController;

import java.sql.Connection;

public class ExplorerStarter {
    public void startScene(boolean guest, String login, Connection connection) {
        ExplorerSceneController sceneController = new ExplorerSceneController(guest, login, connection);
        SceneStarter.start(sceneController, "/FXML/ExplorerScene.fxml", "DBExplorer", true);
        sceneController.init();
    }
}
