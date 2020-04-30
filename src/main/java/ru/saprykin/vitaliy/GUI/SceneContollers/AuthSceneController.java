package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.saprykin.vitaliy.GUI.SceneStarters.ExplorerStarter;

public class AuthSceneController extends SceneController {
    @FXML
    private Button buttonGuest;
    private Button buttonAdmin;

    @FXML
    private void buttonGuestClicked() {
        buttonGuest.setText("Button clicked!");
        ExplorerStarter starter = new ExplorerStarter();
        starter.startExplorerScene(true, null, null);
    }

    @FXML
    private void buttonAdminClicked() {
        buttonGuest.setText("Button clicked!");
        ExplorerStarter starter = new ExplorerStarter();
        starter.startExplorerScene(false, "abc", "ccc");
    }
}
