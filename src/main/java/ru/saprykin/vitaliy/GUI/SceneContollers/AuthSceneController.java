package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.saprykin.vitaliy.DBConnector;
import ru.saprykin.vitaliy.GUI.SceneStarters.DBConnectionStarter;

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

    private final Connection appDBConnection;

    public AuthSceneController() throws SQLException {
        this.appDBConnection = DBConnector.getAppDBConnection();
    }


    @FXML
    private void buttonGuestClicked() {
        new DBConnectionStarter().startScene(true, null);
    }

    @FXML
    private void buttonAdminClicked() {
        if (Authentication(textFieldLogin.getText(), passwordFieldPassword.getText())) {
            new DBConnectionStarter().startScene(false, textFieldLogin.getText());
        } else {
            labelConnectionState.setText("Invalid login or password");
        }
    }

    private boolean Authentication(String login, String password) {
        try {
            if (login.isBlank() || password.isBlank()) {
                return false;
            }
            Statement statement = appDBConnection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT name, password FROM user_profiles;");

            while (resultSet.next()) {
                if (resultSet.getString("name").equals(login)) {
                    return resultSet.getString("password").equals(password);
                }
            }

            resultSet.close();
            statement.close();

            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            labelConnectionState.setText("Server connection error");
        }
        return false;
    }
}
