package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.saprykin.vitaliy.DBConnector;
import ru.saprykin.vitaliy.GUI.SceneStarters.ExplorerStarter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthSceneController extends SceneController {
    @FXML
    private Button buttonGuest;
    @FXML
    private Button buttonAdmin;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Label labelConnectionState;

    @FXML
    private void buttonGuestClicked() {
        buttonGuest.setText("Button clicked!");
        ExplorerStarter starter = new ExplorerStarter();

        starter.startExplorerScene(true, null);
    }

    @FXML
    private void buttonAdminClicked() {
        buttonGuest.setText("Button clicked!");
        ExplorerStarter starter = new ExplorerStarter();
        if (checkLogin(textFieldLogin.getText(), textFieldPassword.getText())) {
            starter.startExplorerScene(false, textFieldLogin.getText());
        }

    }

    private boolean checkLogin(String login, String password) {
        try {
            Connection connection = DBConnector.connectToDBExploration();
            Statement statement = connection.createStatement();
            //TODO:
            ResultSet resultSet  = statement.executeQuery("SELECT *");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            labelConnectionState.setText("Error connecting to the account database");
        }

    }
}
