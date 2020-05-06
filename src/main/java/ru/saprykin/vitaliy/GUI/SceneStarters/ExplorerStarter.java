package ru.saprykin.vitaliy.GUI.SceneStarters;

import ru.saprykin.vitaliy.GUI.SceneContollers.ExplorerSceneController;

import java.sql.Connection;

public class ExplorerStarter {
    public void startScene(boolean guest, String login, Connection connection, String dbName) {
        ExplorerSceneController sceneController = new ExplorerSceneController(guest, login, connection, dbName);
        SceneStarter.start(sceneController, "/FXML/ExplorerScene.fxml", "DBExplorer", true, 0, 0);
        sceneController.init();
    }
}
