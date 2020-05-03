package ru.saprykin.vitaliy.GUI.SceneStarters;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.saprykin.vitaliy.GUI.SceneContollers.SceneController;

import java.io.IOException;
import java.net.URL;

abstract public class SceneStarter {

    public static void start(SceneController sceneController, String fxmlResource, String title, boolean maximized) {
        Stage stage = MainGUIStarter.getStage();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(sceneController.getController());
        URL xmlUrl = SceneStarter.class.getResource(fxmlResource);
        loader.setLocation(xmlUrl);

        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            if (maximized) {
                stage.setMinHeight(1920);
                stage.setMinWidth(1080);
                stage.setMaximized(true);
            } else {
                stage.setMinHeight(400);
                stage.setMinWidth(660);
                stage.centerOnScreen();
            }
            stage.setTitle(title);
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
