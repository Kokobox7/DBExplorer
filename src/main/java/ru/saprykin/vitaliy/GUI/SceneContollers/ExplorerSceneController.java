package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

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
    private TableView table;

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
        //Set tableView resizeable
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //fill in the tableView with names of tables
        try {
            Statement statement = exploredDBConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pg_tables WHERE schemaname !~ '^pg_|^information_schema'");
            ObservableList<String> listOfResults = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listOfResults.add(resultSet.getString("tablename"));
            }

            listOfTables.setItems(listOfResults);

            MultipleSelectionModel<String> tablesSelectionModel = listOfTables.getSelectionModel();
            tablesSelectionModel.selectedItemProperty().addListener(new tableChangeListener());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private class tableChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            Statement statement = null;
            try {
                statement = exploredDBConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " +  t1);
                ObservableList<String> listOfResults = FXCollections.observableArrayList();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnsNumber = resultSetMetaData.getColumnCount();

                table.getColumns().clear();

                for (int i =0; i < columnsNumber;  i++){
                    int i2 = i;
                    TableColumn tableColumn = new TableColumn(resultSetMetaData.getColumnName(i+1));
                    tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleObjectProperty(param.getValue().get(i2));
                        }
                    });
                    table.getColumns().addAll(tableColumn);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}



