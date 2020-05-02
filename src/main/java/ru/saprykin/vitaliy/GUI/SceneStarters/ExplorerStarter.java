package ru.saprykin.vitaliy.GUI.SceneStarters;

import ru.saprykin.vitaliy.GUI.SceneContollers.ExplorerSceneController;

public class ExplorerStarter {
    public void startExplorerScene(boolean guest, String login) {
        ExplorerSceneController sceneController = new ExplorerSceneController();
        SceneStarter.start(sceneController, "/FXML/ExplorerScene.fxml", "DBExplorer", true);
        sceneController.init(guest, login);
    }
}
