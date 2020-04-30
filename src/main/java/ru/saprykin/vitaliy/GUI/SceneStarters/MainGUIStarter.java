package ru.saprykin.vitaliy.GUI.SceneStarters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.saprykin.vitaliy.GUI.SceneContollers.AuthSceneController;

import java.io.IOException;
import java.net.URL;

public class MainGUIStarter extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) {
        MainGUIStarter.stage = stage;

        SceneStarter.start(new AuthSceneController(), "/AuthScene.fxml", "DBExplorer. Authentication", false);
    }

    public static Stage getStage() {
        return stage;
    }


    public static void main(String[] args) {
        launch();
    }

}