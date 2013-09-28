package coachj.utils;

import coachj.builders.PlayerGameStatsBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.structures.PlayerGameStats;
import coachj.structures.StatItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.XYChart;

/**
 * Utility class for stats
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class StatsUtils {

    /**
     * Returns a string with shots made, shots attempted and shooting
     * percentage, to be used for displaying purposes
     *
     * @param shotsMade Number of shots made
     * @param shotsAttempted Number of shots attempted
     * @return
     */
    public static String getBoxScoreShootingPercentage(int shotsMade, int shotsAttempted) {
        String boxScoreShootingPercentage;
        double shootingPercentage = 0;

        if (shotsAttempted > 0) {
            shootingPercentage = (double) shotsMade / shotsAttempted;
        }

        boxScoreShootingPercentage = String.format("%02d-%02d (%.3f)", shotsMade, shotsAttempted,
                shootingPercentage);

        return boxScoreShootingPercentage;
    }

    /**
     * Returns the league's average rate for the given attribute
     *
     * @param attribute Attribute to calculate average
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getLeagueAttributeAverageRate(String attribute,
            DatabaseDirectConnection connection) {

        double leagueAttributeAverage = 65;
        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + attribute + ") AS leagueAverage "
                + "FROM player WHERE active = true AND franchise IS NOT NULL";

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            leagueAttributeAverage = resultSet.getDouble("leagueAverage");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return leagueAttributeAverage;
    }

    /**
     * Returns an observable list with all the stats description and sql
     * equations to retrieve them
     *
     * @param rb Arquivo de resources
     * @return
     */
    public static ArrayList<StatItem> getStatsDescriptorsList(ResourceBundle rb) {
        ArrayList<StatItem> statsList = new ArrayList<>();
        StatItem currentStatListItem;
        Map<String, String> statsDescriptor = new HashMap<>();

        /**
         * Adding stats to the hashmap
         */
        statsDescriptor.put(rb.getString("ch_pontos"), "points");
        statsDescriptor.put(rb.getString("ch_minutos"), "playingTime / 60");
        statsDescriptor.put(rb.getString("ch_arremessos_campo_convertidos"), "fieldGoalsMade");
        statsDescriptor.put(rb.getString("ch_arremessos_campo_tentados"), "fieldGoalsAttempted");
        statsDescriptor.put(rb.getString("ch_arremessos_campo_percentual"), "fieldGoalsMade / fieldGoalsAttempted * 100");
        statsDescriptor.put(rb.getString("ch_lances_livres_convertidos"), "freeThrowsMade");
        statsDescriptor.put(rb.getString("ch_lances_livres_tentados"), "freeThrowsAttempted");
        statsDescriptor.put(rb.getString("ch_lances_livres_percentual"), "freeThrowsMade / freeThrowsAttempted * 100");
        statsDescriptor.put(rb.getString("ch_arremessos_tres_pontos_convertidos"), "threePointersMade");
        statsDescriptor.put(rb.getString("ch_arremessos_tres_pontos_tentados"), "threePointersAttempted");
        statsDescriptor.put(rb.getString("ch_arremessos_tres_pontos_percentual"), "threePointersMade / threePointersAttempted * 100");
        statsDescriptor.put(rb.getString("ch_assistencias"), "assists");
        statsDescriptor.put(rb.getString("ch_rebotes_ofensivos"), "offensiveRebounds");
        statsDescriptor.put(rb.getString("ch_rebotes_defensivos"), "defensiveRebounds");
        statsDescriptor.put(rb.getString("ch_rebotes"), "offensiveRebounds + defensiveRebounds");
        statsDescriptor.put(rb.getString("ch_tocos"), "blocks");
        statsDescriptor.put(rb.getString("ch_tocos_recebidos"), "blockedShots");
        statsDescriptor.put(rb.getString("ch_roubos_bola"), "steals");
        statsDescriptor.put(rb.getString("ch_entregas_bola"), "turnovers");
        statsDescriptor.put(rb.getString("ch_faltas"), "personalFouls");

        /**
         * Looping through the hashmap to transpose elements to the arraylist
         */
        for (Map.Entry currentStatDescriptor : statsDescriptor.entrySet()) {
            currentStatListItem = new StatItem();
            currentStatListItem.setDescription(currentStatDescriptor.getKey().toString());
            currentStatListItem.setSqlEquation((String) currentStatDescriptor.getValue());
            statsList.add(currentStatListItem);
        }

        return statsList;
    }

    /**
     * Returns an arraylist with all the game stats for given player in the
     * given season
     *
     * @param playerId Player's id
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static ArrayList<PlayerGameStats> getPlayerGameByGameStats(int playerId,
            int season, DatabaseDirectConnection connection) {

        ArrayList<PlayerGameStats> playerGameStats = new ArrayList<>();
        ResultSet resultSet;
        int gameId;
        String sqlStatement = "SELECT game FROM player_log WHERE player = "
                + playerId + " AND season = " + season + " ORDER BY gameDate";

        resultSet = connection.getResultSet(sqlStatement);

        /**
         * Looping throught the resulset to retrieve player's stats for each
         * game
         */
        try {
            while (resultSet.next()) {
                gameId = resultSet.getInt("game");
                PlayerGameStats currentGame = PlayerGameStatsBuilder
                        .buildPlayerGameStats(playerId, gameId, connection);

                playerGameStats.add(currentGame);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return playerGameStats;
    }

    /**
     * Returns a XY series to be displayed in line charts with game by game
     * stats for the given player in the given season
     *
     * @param playerId Player's id
     * @param season Season year
     * @param sqlEquation SQL equation that generates the stats
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static XYChart.Series getPlayerGameByGameStatSeries(int playerId,
            int season, String sqlEquation, DatabaseDirectConnection connection) {

        XYChart.Series playerGameByGameStatSeries = new XYChart.Series<>();
        String opponent;
        String homeRoad;
        String seriesLabel;
        Number stat;
        ResultSet resultSet;
        String sqlStatement = "SELECT (" + sqlEquation + ") AS value, opponent,"
                + "homeRoad FROM player_log "
                + "WHERE player = " + playerId + " AND season = " + season
                + " ORDER BY gameDate";

        resultSet = connection.getResultSet(sqlStatement);

        /**
         * Looping throught the resulset to retrieve player's stats for each
         * game
         */
        try {
            while (resultSet.next()) {
                stat = resultSet.getInt("value");
                opponent = FranchiseUtils.getFranchiseAbbreviature(resultSet.getInt("opponent"),
                        connection);
                homeRoad = resultSet.getString("homeRoad").equalsIgnoreCase("R")
                        ? "@" : "vs";
                seriesLabel = homeRoad + " " + opponent;

                playerGameByGameStatSeries.getData().add(new XYChart.Data(seriesLabel,
                        stat));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return playerGameByGameStatSeries;
    }

    /**
     * Returns the average for the given stat for the given player in a given
     * season
     *
     * @param playerId Player's id
     * @param season Season year
     * @param sqlEquation SQL equation that generates the stats
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getPlayerAverage(int playerId,
            int season, String sqlEquation, DatabaseDirectConnection connection) {
        double playerAverage = 0;

        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + sqlEquation + ") AS value FROM player_log "
                + "WHERE player = " + playerId + " AND playingTime > 0 AND season = "
                + season;

        resultSet = connection.getResultSet(sqlStatement);
        try {
            resultSet.first();
            playerAverage = resultSet.getDouble("value");
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return playerAverage;
    }

    /**
     * Returns the average for the given stat for the given franchise's leader
     * in a given season
     *
     * @param franchiseId Franchise's id
     * @param season Season year
     * @param sqlEquation SQL equation that generates the stats
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getTeamLeaderAverage(int franchiseId,
            int season, String sqlEquation, DatabaseDirectConnection connection) {
        double teamLeaderAverage = 0;

        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + sqlEquation + ") AS value FROM player_log "
                + "WHERE team = " + franchiseId + " AND season = " + season
                + " AND playingTime > 0 GROUP BY player ORDER BY value DESC LIMIT 1";

        resultSet = connection.getResultSet(sqlStatement);
        try {
            resultSet.first();
            teamLeaderAverage = resultSet.getDouble("value");
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teamLeaderAverage;
    }

    /**
     * Returns the average for the given stat for the league's leader in a given
     * season
     *
     * @param season Season year
     * @param sqlEquation SQL equation that generates the stats
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getLeagueLeaderAverage(int season, String sqlEquation,
            DatabaseDirectConnection connection) {
        double leagueLeaderAverage = 0;

        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + sqlEquation + ") AS value FROM player_log "
                + "WHERE " + season + " AND playingTime > 0 "
                + " GROUP BY player ORDER BY value DESC LIMIT 1";

        resultSet = connection.getResultSet(sqlStatement);
        try {
            resultSet.first();
            leagueLeaderAverage = resultSet.getDouble("value");
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return leagueLeaderAverage;
    }

    /**
     * Returns the league's average for the given stat in a given season
     *
     * @param season Season year
     * @param sqlEquation SQL equation that generates the stats
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getLeagueAverage(int season, String sqlEquation,
            DatabaseDirectConnection connection) {
        double leagueAverage = 0;

        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + sqlEquation + ") AS value FROM player_log "
                + "WHERE playingTime > 0 AND season = " + season;

        resultSet = connection.getResultSet(sqlStatement);
        try {
            resultSet.first();
            leagueAverage = resultSet.getDouble("value");
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return leagueAverage;
    }

    /**
     * Returns the average for the given stat in a given season for players in a
     * given position
     *
     * @param position Player's position
     * @param season Season year
     * @param sqlEquation SQL equation that generates the stats
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getPositionAverage(String position,
            int season, String sqlEquation, DatabaseDirectConnection connection) {
        double positionAverage = 0;

        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + sqlEquation + ") AS value FROM player_log "
                + "WHERE season = " + season + " AND playingTime > 0 AND player IN "
                + "(SELECT id FROM player WHERE position = '" + position + "')";

        resultSet = connection.getResultSet(sqlStatement);
        try {
            resultSet.first();
            positionAverage = resultSet.getDouble("value");
        } catch (SQLException ex) {
            Logger.getLogger(StatsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return positionAverage;
    }
} // end StatsUtils
