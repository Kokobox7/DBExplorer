package ru.saprykin.vitaliy.GUI.SceneStarters;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUIStarter extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) {
        MainGUIStarter.stage = stage;
        AuthStarter authStarter = new AuthStarter();
        authStarter.startScene();
    }

    public static Stage getStage() {
        return stage;
    }


    public static void main(String[] args) {
        launch();
    }

}