package ru.saprykin.vitaliy.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.saprykin.vitaliy.GUI.SceneContollers.AuthSceneController;
import ru.saprykin.vitaliy.GUI.SceneContollers.MainSceneController;

import java.io.IOException;
import java.net.URL;

public class MainGUI extends Application {
    static Stage stage;

    @Override
    public void start(Stage stage) {
        MainGUI.stage = stage;

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new AuthSceneController());
        URL xmlUrl = getClass().getResource("/AuthScene.fxml");
        loader.setLocation(xmlUrl);

        try {
            Parent root = loader.load();

            stage.setScene(new Scene(root, 640 , 480));
            stage.setTitle("DBExplorer. Authentication");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startMainScene() {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new MainSceneController());
        URL xmlUrl = MainGUI.class.getResource("/MainScene.fxml");
        loader.setLocation(xmlUrl);
        try {
            Parent root = loader.load();

            stage.setScene(new Scene(root, 1920 , 1080));
            stage.setTitle("DBExplorer");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }

}