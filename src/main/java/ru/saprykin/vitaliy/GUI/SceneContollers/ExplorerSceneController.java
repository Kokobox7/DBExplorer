package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import ru.saprykin.vitaliy.GUI.SceneStarters.DBConnectionStarter;

import java.sql.*;

public class ExplorerSceneController extends SceneController {
    private final boolean guest;
    private final String login;
    private final Connection exploredDBConnection;

    @FXML
    private Label profileInformer;
    @FXML
    private ListView<String> listOfTables;
    @FXML
    private ChoiceBox<String> listOfSchemas;
    @FXML
    private TableView tableView;
    @FXML
    private Label noTablesInSchema;


    public ExplorerSceneController(boolean guest, String login, Connection exploredDBConnection) {
        this.guest = guest;
        this.login = login;
        this.exploredDBConnection = exploredDBConnection;
    }

    public void init() {
        if (guest) {
            profileInformer.setText("Welcome, guest.");
        } else {
            profileInformer.setText("Welcome, " + login + ".");
        }

        fillInListOfSchemas();
    }


    //fill in the choiceBox with schemas names
    private void fillInListOfSchemas() {
        try {
            Statement statement = exploredDBConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pg_catalog.pg_namespace WHERE nspname !~ '^pg_|^information_schema'");
            ObservableList<String> listOfResults = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listOfResults.add(resultSet.getString("nspname"));
            }

            listOfSchemas.setItems(listOfResults);

            SingleSelectionModel<String> tablesSelectionModel = listOfSchemas.getSelectionModel();
            tablesSelectionModel.selectedItemProperty().addListener(new schemaChangeListener());

            resultSet.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //fill in the listView with tables names
    private void fillInTableView(String schemaName) {
        try {
            noTablesInSchema.setVisible(false);
            Statement statement = exploredDBConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pg_tables WHERE schemaname = '" + schemaName + "'");
            ObservableList<String> listOfResults = FXCollections.observableArrayList();

            //Check if resultSet is empty
            if (!resultSet.isBeforeFirst()) {
                noTablesInSchema.setVisible(true);
                listOfTables.setDisable(true);
            } else {
                while (resultSet.next()) {
                    listOfResults.add(resultSet.getString("tablename"));
                }

                listOfTables.setItems(listOfResults);

                MultipleSelectionModel<String> tablesSelectionModel = listOfTables.getSelectionModel();
                tablesSelectionModel.selectedItemProperty().addListener(new tableChangeListener());
                listOfTables.setDisable(false);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private class schemaChangeListener implements ChangeListener<String> {
        @Override
        //method that shows table content when another table name was selected in listView
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            fillInTableView(listOfSchemas.getValue());
        }
    }


    private class tableChangeListener implements ChangeListener<String> {
        @Override
        //method that shows table content when another table name was selected in listView
        public void changed(ObservableValue<? extends String> observableValue, String s, String newTable) {
            try {
                Statement statement = exploredDBConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + listOfSchemas.getValue() + "." + newTable);
                ObservableList listOfResults = FXCollections.observableArrayList();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnsNumber = resultSetMetaData.getColumnCount();

                tableView.getColumns().clear();

                for (int i = 0; i < columnsNumber; i++) {
                    int i2 = i;
                    TableColumn tableColumn = new TableColumn(resultSetMetaData.getColumnName(i + 1));
                    tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                            new SimpleObjectProperty(param.getValue().get(i2)));
                    tableView.getColumns().addAll(tableColumn);
                }

                while (resultSet.next()) {
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(resultSet.getString(i));
                    }
                    listOfResults.add(row);
                }

                //FINALLY ADDED TO TableView
                tableView.setItems(listOfResults);

                resultSet.close();
                statement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @FXML
    private void backToDBConnection() {
        new DBConnectionStarter().startScene(guest, login);
    }
}



