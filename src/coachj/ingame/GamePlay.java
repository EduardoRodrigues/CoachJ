package coachj.ingame;

import coachj.builders.RefereeBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.models.Referee;
import coachj.utils.GameUtils;
import coachj.utils.RefereeUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stores information about the game that is about to be played
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class GamePlay {

    /**
     * Fields
     */
    private int gameId;
    private short homeTeamId;
    private short awayTeamId;
    private String gameDate;
    private String gameTime;
    private short refereeId;
    private int attendance;
    private short arenaId;
    private String gameType;
    private short awayScore;
    private short homeScore;
    private short timeLeft;
    private short shotClock; 
    private short period;
    private Referee referee;
    private ArrayList<Team> teams = new ArrayList<>();

    public GamePlay(int gameId, DatabaseDirectConnection connection) {
        this.gameId = gameId;
        
         /**
         * Retrieving data to set up fields
         */
        String sqlStatement = "SELECT homeTeam, awayTeam, date, time, referee, "
                + " arena, type FROM game WHERE id = " + gameId;
        ResultSet resultSet = connection.getResultSet(sqlStatement);
        Team awayTeam;
        Team homeTeam;
        
        try {
            resultSet.first();
            
            /**
             * Base data
             */
            this.homeTeamId = resultSet.getShort("homeTeam");
            this.awayTeamId = resultSet.getShort("awayTeam");
            this.gameDate = resultSet.getString("date");
            this.gameTime = resultSet.getString("time");
            this.arenaId = resultSet.getShort("arena");
            this.refereeId = RefereeUtils.getRandomRefereeId(gameDate, connection);
            this.gameType = resultSet.getString("type");
            this.attendance = GameUtils.calculateGameAttendance(gameType, 
                    awayTeamId, homeTeamId, arenaId, connection);
            this.awayScore = 0;
            this.homeScore = 0;
            this.shotClock = 24;
            this.timeLeft = 720;
            this.period = 1;
             
            /**
             * Creating teams
             */
            awayTeam = new Team(awayTeamId, connection);
            homeTeam = new Team(homeTeamId, connection);
            teams.add(awayTeam);
            teams.add(homeTeam);
            
            /**
             * Creating referee
             */
            RefereeBuilder refereeBuilder = new RefereeBuilder();
            refereeBuilder.fillAttributesFromDatabase(refereeId, connection);
            referee = refereeBuilder.generateRefereeEntity();
            
        } catch (SQLException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }    
    
    /* getters and setters */
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public short getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(short homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public short getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(short awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public short getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(short refereeId) {
        this.refereeId = refereeId;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public short getArenaId() {
        return arenaId;
    }

    public void setArenaId(short arenaId) {
        this.arenaId = arenaId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public short getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(short awayScore) {
        this.awayScore = awayScore;
    }

    public short getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(short homeScore) {
        this.homeScore = homeScore;
    }

    public short getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(short timeLeft) {
        this.timeLeft = timeLeft;
    }

    public short getShotClock() {
        return shotClock;
    }

    public void setShotClock(short shotClock) {
        this.shotClock = shotClock;
    }   

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public short getPeriod() {
        return period;
    }

    public void setPeriod(short period) {
        this.period = period;
    }
    
} // end GamePlay
