package ru.saprykin.vitaliy.GUI.SceneStarters;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.saprykin.vitaliy.GUI.SceneContollers.SceneController;

import java.io.IOException;
import java.net.URL;

abstract public class SceneStarter {

    public static void start(SceneController sceneController, String fxmlResource, String title, boolean maximized, int height, int width) {
        Stage stage = MainGUIStarter.getStage();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(sceneController.getController());
        URL xmlUrl = SceneStarter.class.getResource(fxmlResource);
        loader.setLocation(xmlUrl);

        try {
            stage.hide();
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            if (!maximized) {
                stage.setMinHeight(height);
                stage.setMinWidth(width);
                stage.setHeight(height);
                stage.setWidth(width);
                stage.centerOnScreen();
                stage.setMaximized(false);
            }
            stage.setTitle(title);
            if (maximized) {
                // Get current screen of the stage
                ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));
                // Change stage properties
                stage.setMaximized(true);
                Rectangle2D bounds = screens.get(0).getVisualBounds();
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
            }
            stage.show();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
