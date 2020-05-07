package ru.saprykin.vitaliy.GUI.SceneContollers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import ru.saprykin.vitaliy.DBConnector;
import ru.saprykin.vitaliy.GUI.SceneStarters.DBConnectionStarter;

import java.sql.*;
import java.util.ArrayList;

public class ExplorerSceneController extends SceneController {
    private final boolean guest;
    private final String login;
    private final Connection exploredDBConnection;
    private Connection appDBConnection;
    private ArrayList<Filter> filters;
    private String dbName;

    //if null, then when new table selected, all columns will be shown
    //otherwise, only those with names specified in the variable will be shown
    private String[] activeFilteredColums = null;

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
    @FXML
    private HBox rowOfFilterButtons;

    private MenuButton columnsMenu;
    private ArrayList<String> currentCollumns;


    public ExplorerSceneController(boolean guest, String login, Connection exploredDBConnection, String dbName) {
        this.guest = guest;
        this.login = login;
        this.dbName = dbName;
        this.exploredDBConnection = exploredDBConnection;
        try {
            appDBConnection = DBConnector.getAppDBConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void init() {
        if (guest) {
            profileInformer.setText("Welcome, guest.");
        } else {
            profileInformer.setText("Welcome, " + login + ". You have administrator rights.");
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


    private class schemaChangeListener implements ChangeListener<String> {
        @Override
        //method that shows table content when another table name was selected in listView
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            fillInListOfTables(listOfSchemas.getValue());
            fillInFilterButtonsRow(listOfSchemas.getValue());
        }
    }

    private void fillInFilterButtonsRow(String schema) {
        rowOfFilterButtons.getChildren().clear();

        try {
            Statement statement = appDBConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM table_filters WHERE db_name= '" + dbName + "' AND schema= '" + schema + "' LIMIT 100");

            filters = new ArrayList<>();
            while (resultSet.next()) {
                Array filtered_columnsArr = resultSet.getArray("filtered_columns");
                String[] filtered_columns = (String[]) filtered_columnsArr.getArray();
                filters.add(new Filter(resultSet.getString("name"), resultSet.getString("db_name"), listOfSchemas.getValue(),
                        resultSet.getString("filtered_table"), filtered_columns));
            }

            //Show filters for our database
            for (Filter f : filters) {
                createFilterButton(f);
            }

            addPlusButton();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Create button for adding new filter
    private void addPlusButton() {
        //if you are admin and there are some tables in schema
        if (!guest && !noTablesInSchema.isVisible()) {
            Button plusButton = new Button("+");
            plusButton.styleProperty().bind(Bindings.concat("-fx-font-size:", 14));
            rowOfFilterButtons.getChildren().add(plusButton);
            plusButton.setOnAction(newFilter);
        }
    }

    EventHandler<ActionEvent> newFilter = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            rowOfFilterButtons.getChildren().remove(event.getSource());
            columnsMenu = new MenuButton();
            columnsMenu.setMinHeight(30);
            columnsMenu.setMinWidth(120);
            columnsMenu.setPrefHeight(30);
            columnsMenu.setPrefWidth(120);
            columnsMenu.styleProperty().bind(Bindings.concat("-fx-min-height:", 30));
            columnsMenu.styleProperty().bind(Bindings.concat("-fx-min-width:", 120));
            refreshContentOfChoiceBox();
            rowOfFilterButtons.getChildren().add(columnsMenu);
            Button addButton = new Button("Add new filter");
            addButton.styleProperty().bind(Bindings.concat("-fx-font-size:", 14));
            rowOfFilterButtons.getChildren().add(addButton);
        }
    };

    private void refreshContentOfChoiceBox() {
        if (columnsMenu != null && currentCollumns != null) {
            ObservableList<String> columnNames = FXCollections.observableArrayList(currentCollumns);
            columnNames.stream().map(CheckMenuItem::new).forEach(columnsMenu.getItems()::add);
        }
    }

    private void createFilterButton(Filter f) {
        Button button = new Button(f.name);
        button.styleProperty().bind(Bindings.concat("-fx-font-size:", 14));
        rowOfFilterButtons.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            f.execute();
        });
        if (!guest) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteFilter = new MenuItem("Delete filter");
            String filterName = f.getName();
            deleteFilter.setUserData(filterName);
            deleteFilter.setOnAction(deleteFilterEvent);
            contextMenu.getItems().add(deleteFilter);
            button.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenu.show(button, event.getScreenX(), event.getScreenY());
                }
            });
        }
    }

    EventHandler<ActionEvent> deleteFilterEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Statement statement = appDBConnection.createStatement();
                MenuItem item = (MenuItem) event.getSource();
                statement.execute("DELETE FROM table_filters WHERE name='" + item.getUserData() + "'");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("The deletion was completed successfully.");
                alert.showAndWait();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Deletion error!");
                alert.showAndWait();
            }
        }
    };

    //fill in the listView with tables names
    private void fillInListOfTables(String schemaName) {
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

    private class tableChangeListener implements ChangeListener<String> {
        @Override
        //When selected another table this method invoked
        public void changed(ObservableValue<? extends String> observableValue, String s, String newTable) {
            //We change contents of tableView
            changeContentOfTableView(newTable);
            refreshContentOfChoiceBox();
        }
    }

    //Method that shows table content in TableView
    private void changeContentOfTableView(String newTable) {
        try {
            Statement statement = exploredDBConnection.createStatement();

            String query = "SELECT * FROM " + listOfSchemas.getValue() + "." + newTable;
            if (activeFilteredColums != null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT ");
                int i = 0;
                for (i = 0; i < activeFilteredColums.length - 1; i++) {
                    queryBuilder.append(activeFilteredColums[i]);
                    queryBuilder.append(", ");
                }
                queryBuilder.append(activeFilteredColums[i]);
                queryBuilder.append(" FROM ");
                queryBuilder.append(listOfSchemas.getValue());
                queryBuilder.append(".");
                queryBuilder.append(newTable);
                //next time when any table will be selected, all columns will be displayed
                activeFilteredColums = null;
                query = queryBuilder.toString();
            }
            ResultSet resultSet = statement.executeQuery(query);
            ObservableList listOfResults = FXCollections.observableArrayList();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();

            tableView.getColumns().clear();

            currentCollumns = new ArrayList<>();
            for (int i = 0; i < columnsNumber; i++) {
                int iCopy = i;
                TableColumn tableColumn = new TableColumn(resultSetMetaData.getColumnName(i + 1));
                currentCollumns.add(resultSetMetaData.getColumnName(i + 1));
                tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(iCopy).toString());
                    }
                });

                tableView.getColumns().add(tableColumn);
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

    @FXML
    private void backToDBConnection() {
        new DBConnectionStarter().startScene(guest, login);
    }

    private class Filter {
        private String name;

        public String getName() {
            return name;
        }

        public String getDb_name() {
            return db_name;
        }

        public String getFiltered_table() {
            return filtered_table;
        }

        public String[] getFiltered_columns() {
            return filtered_columns;
        }

        private String db_name;
        private String schema;
        private String filtered_table;
        private String[] filtered_columns;

        public Filter(String name, String db_name, String schema, String filtered_table, String[] filtered_colums) {
            this.name = name;
            this.db_name = db_name;
            this.schema = schema;
            this.filtered_table = filtered_table;
            this.filtered_columns = filtered_colums;
        }

        public void execute() {
            MultipleSelectionModel<String> tablesSelectionModel = listOfTables.getSelectionModel();
            //show only columns with names filtered_columns
            activeFilteredColums = filtered_columns;
            tablesSelectionModel.select(filtered_table);
        }
    }
}



