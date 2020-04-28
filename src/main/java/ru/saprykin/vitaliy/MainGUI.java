package ru.saprykin.vitaliy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) {
        //String javaVersion = System.getProperty("java.version");
        //String javafxVersion = System.getProperty("javafx.version");
        //Label labelSignIn = new Label("Sign in as an administrator:" );
        //labelSignIn.setAlignment(Pos.BASELINE_LEFT);
        //Scene scene = new Scene(new StackPane(labelSignIn), 640, 480);
        //stage.setTitle("Hello world Application");

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        try {
            Parent root = loader.load();


            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}