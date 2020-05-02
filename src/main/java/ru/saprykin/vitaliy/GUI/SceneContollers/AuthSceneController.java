package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private PasswordField passwordFieldPassword;
    @FXML
    private Label labelConnectionState;

    private Connection appDBConnection;

    public void init(Connection appDBConnection){
        this.appDBConnection = appDBConnection;
    }

    @FXML
    private void buttonGuestClicked() {
        buttonGuest.setText("Button clicked!");
        ExplorerStarter starter = new ExplorerStarter();

        starter.startExplorerScene(true, null);
    }

    @FXML
    private void buttonAdminClicked() throws SQLException {
        buttonGuest.setText("Button clicked!");
        ExplorerStarter starter = new ExplorerStarter();
        if (Authentication(textFieldLogin.getText(), passwordFieldPassword.getText())) {
            appDBConnection.close();
            starter.startExplorerScene(false, textFieldLogin.getText());
        }
        else {
            labelConnectionState.setText("Invalid login or password");
        }
    }

    private boolean Authentication(String login, String password) {
        try {
            if (login.isBlank() || password.isBlank()){
                return false;
            }
            Statement statement = appDBConnection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT name, password FROM profiles;");

            while (resultSet.next()) {
                if (resultSet.getString("name").equals(login)) {
                    return resultSet.getString("password").equals(password);
                }
            }
            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            labelConnectionState.setText("Error connecting to the profiles database");
        }
        return false;
    }
}
