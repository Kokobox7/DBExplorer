package ru.saprykin.vitaliy.GUI.SceneStarters;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.saprykin.vitaliy.GUI.SceneContollers.AuthSceneController;

import java.io.IOException;
import java.net.URL;

public class AuthScene {
    public void startExplorerScene(boolean guest, String login, String password) {
        Stage stage = MainGUIStarter.getStage();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new AuthSceneController());
        URL xmlUrl = getClass().getResource("/AuthScene.fxml");
        loader.setLocation(xmlUrl);

        try {
            Parent root = loader.load();

            stage.setScene(new Scene(root, 640, 400));
            stage.centerOnScreen();
            stage.setTitle("DBExplorer. Authentication");
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
