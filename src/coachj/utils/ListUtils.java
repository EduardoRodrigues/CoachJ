package coachj.utils;

import coachj.DraftController;
import coachj.builders.PlayerBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.lists.IdDescriptionListItem;
import coachj.models.Player;
import coachj.structures.DraftSummary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Utility class for list controls
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 02/08/2013
 */
public class ListUtils {

    /**
     * Populates an ObservableList with IdDescriptionListItem objects,
     * retrieving the data from the resultset returned by a sql statement
     *
     * @param sqlStatement SQL statement that retrieves the records
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static ObservableList<IdDescriptionListItem> fillIdDescriptionListFromSQL(String sqlStatement,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the list of items, the database connection, the
         * resultset
         */
        ObservableList<IdDescriptionListItem> observableList = FXCollections
                .observableArrayList();
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;

        /**
         * Auxiliary variables that store the id and the description of the item
         */
        int id;
        String description;

        /**
         * Opening connection, checking if there are records retrieved and, if
         * so, looping through the resulset to fill the observable list
         */
        connection.open();
        resultSet = connection.getResultSet(sqlStatement);

        try {
            if (!resultSet.next()) {
                return null;
            }

            resultSet.beforeFirst();

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                description = resultSet.getString("description");
                IdDescriptionListItem listItem = new IdDescriptionListItem(id, description);
                observableList.add(listItem);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ListUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return observableList;
    }

    /**
     * Selects an item in an IdDescriptionListItem observablelist based on its
     * index
     *
     * @param list IdDescriptionListItem observablelist
     * @param id Id to be selected
     * @return
     */
    public static int selectComboBoxItem(ObservableList<IdDescriptionListItem> list, int id) {
        int selectedItemIndex = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                selectedItemIndex = i;
                break;
            }
        }

        return selectedItemIndex;
    }

    /**
     * Populates an ObservableList with Player objects, retrieving the data from
     * the resultset returned by a sql statement
     *
     * @param sqlStatement SQL statement that retrieves the records
     * @param connection Database connection used to retrieve data
     */
    public static ObservableList<Player> fillPlayerListFromSQL(String sqlStatement,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the list of items, the database connection, the
         * resultset
         */
        ObservableList<Player> observableList = FXCollections
                .observableArrayList();

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

        /**
         * Variables that store the database connection, resultset, sql
         * statement, Player object instance, that PlayerBuilder object
         * responsible to retrieve the attributes from the database
         */
        ResultSet resultSet;
        Player player;
        PlayerBuilder playerBuilder = new PlayerBuilder();
        short playerId;

        /**
         * opening connection and retrieving data into the resultset
         */
        connection.open();
        resultSet = connection.getResultSet(sqlStatement);

        /**
         * Populating table view
         */
        try {
            resultSet.beforeFirst();

            while (resultSet.next()) {
                playerId = resultSet.getShort("id");
                playerBuilder.fillAttributesFromDatabase(playerId, connection);
                player = playerBuilder.generatePlayerEntity();
                observableList.add(player);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DraftController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /**
             * Closing connection
             */
            connection.close();
        }

        return observableList;
    }    
    
    /**
     * Populates an ObservableList with DraftSummary objects, retrieving the data from
     * the resultset returned by a sql statement
     *
     * @param sqlStatement SQL statement that retrieves the records
     * @param connection Database connection used to retrieve data
     */
    public static ObservableList<DraftSummary> fillDraftSummaryListFromSQL(String sqlStatement,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the list of items, the database connection, the
         * resultset
         */
        ObservableList<DraftSummary> observableList = FXCollections
                .observableArrayList();

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

        /**
         * Variables that store the database connection, resultset, sql
         * statement, Player object instance, that PlayerBuilder object
         * responsible to retrieve the attributes from the database
         */
        ResultSet resultSet;
        DraftSummary draftOperation;

        /**
         * opening connection and retrieving data into the resultset
         */
        connection.open();
        resultSet = connection.getResultSet(sqlStatement);

        /**
         * Populating table view
         */
        try {
            resultSet.beforeFirst();

            while (resultSet.next()) {
               draftOperation = new DraftSummary();
                draftOperation.setDraftPick(resultSet.getShort("pick"));
                draftOperation.setDraftRound(resultSet.getShort("round"));
                draftOperation.setFranchiseId(resultSet.getShort("franchise"));
                draftOperation.setFranchiseName(FranchiseUtils.getFranchiseCompleteName(
                        resultSet.getShort("franchise"), connection));
                draftOperation.setPlayerId(resultSet.getShort("player"));
                draftOperation.setPlayerName(PlayerUtils.getPlayerCompleteName(
                        resultSet.getShort("player"), connection));
                observableList.add(draftOperation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DraftController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /**
             * Closing connection
             */
            connection.close();
        }

        return observableList;
    }
} // end ListUtils
