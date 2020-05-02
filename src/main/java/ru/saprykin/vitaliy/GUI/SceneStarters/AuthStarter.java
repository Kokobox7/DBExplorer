package ru.saprykin.vitaliy.GUI.SceneStarters;

import ru.saprykin.vitaliy.DBConnector;
import ru.saprykin.vitaliy.GUI.SceneContollers.AuthSceneController;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthStarter {
    public void startScene() {
        try {
            Connection appDBConnection = DBConnector.connectToApplicationDB();
            AuthSceneController sceneController = new AuthSceneController();
            SceneStarter.start(sceneController, "/FXML/AuthScene.fxml", "DBExplorer. Authentication", false);
            sceneController.init(appDBConnection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
