package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.saprykin.vitaliy.DBConnector;
import ru.saprykin.vitaliy.GUI.SceneStarters.ExplorerStarter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionSceneController extends SceneController {
    @FXML
    VBox VBoxSpec;
    @FXML
    VBox VBoxProfile;
    @FXML
    ChoiceBox<String> chooseDB;
    @FXML
    TextField tfHost;
    @FXML
    TextField tfPort;
    @FXML
    TextField tfDBName;
    @FXML
    TextField tfLogin;
    @FXML
    TextField tfPassword;
    @FXML
    Label connectionError;

    private Connection appDBConnection;
    private String login;
    private boolean guest;

    ResultSet predefinedDatabases;


    public DBConnectionSceneController(boolean guest, String login) throws SQLException {
        this.appDBConnection = DBConnector.getAppDBConnection();
        this.login = login;
        this.guest = guest;
    }

    public void init() {
        try {
            Statement statement = appDBConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            predefinedDatabases = statement.executeQuery("SELECT * FROM db_profiles");
            ObservableList<String> listOfResults = FXCollections.observableArrayList();
            while (predefinedDatabases.next()) {
                listOfResults.add(predefinedDatabases.getString("dbName"));
            }
            chooseDB.setItems(listOfResults);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void clickedSpec() {
        VBoxProfile.setStyle("-fx-background-color: ddd");
        VBoxSpec.setStyle("-fx-background-color: fff");
        VBoxProfile.setDisable(true);
        VBoxSpec.setDisable(false);
    }

    @FXML
    private void clickedProfile() {
        VBoxProfile.setStyle("-fx-background-color: fff");
        VBoxSpec.setStyle("-fx-background-color: ddd");
        VBoxProfile.setDisable(false);
        VBoxSpec.setDisable(true);
    }

    @FXML
    private void buttonSpecClicked() {
        String hostNameORAddress = tfHost.getText();
        int port = Integer.parseInt(tfPort.getText());
        String dbName = tfDBName.getText();
        String dbUser = tfLogin.getText();
        String dbPassword = tfPassword.getText();
        try {
            Connection externalDBConnection = DBConnector.getExternalDBConnection(hostNameORAddress, port, dbName, dbUser, dbPassword);

            new ExplorerStarter().startScene(guest, login, externalDBConnection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connectionError.setVisible(true);
        }
    }

    @FXML
    private void buttonProfileClicked() {
        String dbName = chooseDB.getValue();
        try {
            predefinedDatabases.beforeFirst();
            while (predefinedDatabases.next()) {
                if (predefinedDatabases.getString("dbName").equals(dbName)) {
                    String hostNameORAddress = predefinedDatabases.getString("hostNameORAddress");
                    int port = predefinedDatabases.getInt("port");
                    String dbUser = predefinedDatabases.getString("dbUser");
                    String dbPassword = predefinedDatabases.getString("dbPassword");

                    Connection externalDBConnection = DBConnector.getExternalDBConnection(hostNameORAddress, port, dbName, dbUser, dbPassword);

                    new ExplorerStarter().startScene(guest, login, externalDBConnection);
                    return;
                }
            }
            connectionError.setVisible(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
