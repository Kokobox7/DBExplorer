package ru.saprykin.vitaliy.GUI.SceneStarters;

import ru.saprykin.vitaliy.DBConnector;
import ru.saprykin.vitaliy.GUI.SceneContollers.DBConnectionSceneController;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionStarter {
    public void startScene(boolean guest, String login) {
        try {
            Connection appDBConnection = DBConnector.getAppDBConnection();
            DBConnectionSceneController sceneController = new DBConnectionSceneController(guest, login);
            SceneStarter.start(sceneController, "/FXML/DBConnectionScene.fxml", "DBExplorer. Connect to database", false);
            sceneController.init();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
