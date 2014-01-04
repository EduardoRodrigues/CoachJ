package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.CourtSpot;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for courts
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 01/09/2013
 */
public class CourtUtils {

    /**
     * Returns a resultset with data for the given court spot
     *
     * @param courtSpotId Spot id from 1 to 420
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static ResultSet getCourtSpotData(int courtSpotId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT * FROM court_spot "
                + "WHERE id = " + courtSpotId + " LIMIT 1";

        /**
         * Executing query and retrieving resultset
         */
        resultSet = connection.getResultSet(sqlStatement);

        return resultSet;
    }

    /**
     * Returns the court zone description in the given language
     *
     * @param courtZoneId Court zone id
     * @param language Desired language
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getCourtZoneDescription(int courtZoneId, String language,
            DatabaseDirectConnection connection) {

        String courtZoneDescription = null;
        ResultSet resultSet;
        String sqlStatement = "SELECT description_" + language + " AS description "
                + "FROM court_zone WHERE id = " + courtZoneId;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            courtZoneDescription = resultSet.getString("description");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return courtZoneDescription;
    }

    /**
     * Returns a hashmap with all the adjacent zones of the court
     *
     * @param courtZone Given court zone
     * @return
     */
    private static ArrayList<Integer> getCourtAdjacentZones(int courtZone) {
        ArrayList<Integer> courtAdjacentZones = new ArrayList<>();

        /**
         * Checking the given zone and populating the array with the adjacent zones
         */
        switch (courtZone) {
            case 1: // right corner
                courtAdjacentZones.add(2);
                courtAdjacentZones.add(4);
                courtAdjacentZones.add(5);
                break;
            case 2: // right wing                 
                courtAdjacentZones.add(1);
                courtAdjacentZones.add(4);
                courtAdjacentZones.add(5);
                courtAdjacentZones.add(8);
                courtAdjacentZones.add(14);
                break;
            case 3: // right low post
                courtAdjacentZones.add(4);
                courtAdjacentZones.add(6);
                break;
            case 4: // right side of the lane
                courtAdjacentZones.add(1);
                courtAdjacentZones.add(2);
                courtAdjacentZones.add(3);
                courtAdjacentZones.add(5);
                courtAdjacentZones.add(6);
                courtAdjacentZones.add(7);
                break;
            case 5: // right elbow
                courtAdjacentZones.add(1);
                courtAdjacentZones.add(2);
                courtAdjacentZones.add(4);
                courtAdjacentZones.add(6);
                courtAdjacentZones.add(7);
                courtAdjacentZones.add(8);
                break;
            case 6: // lane
                courtAdjacentZones.add(3);
                courtAdjacentZones.add(4);
                courtAdjacentZones.add(5);
                courtAdjacentZones.add(7);
                courtAdjacentZones.add(9);
                courtAdjacentZones.add(10);
                courtAdjacentZones.add(11);
                break;
            case 7: // top of the key
                courtAdjacentZones.add(4);
                courtAdjacentZones.add(5);
                courtAdjacentZones.add(6);
                courtAdjacentZones.add(8);
                courtAdjacentZones.add(10);
                courtAdjacentZones.add(11);
                courtAdjacentZones.add(11);
                break;
            case 8: // top of the arc
                courtAdjacentZones.add(2);
                courtAdjacentZones.add(5);
                courtAdjacentZones.add(7);
                courtAdjacentZones.add(11);
                courtAdjacentZones.add(13);
                courtAdjacentZones.add(14);
                break;
            case 9: // left low post
                courtAdjacentZones.add(6);
                courtAdjacentZones.add(10);
                break;
            case 10: // left side of the lane
                courtAdjacentZones.add(6);
                courtAdjacentZones.add(7);
                courtAdjacentZones.add(9);
                courtAdjacentZones.add(11);
                courtAdjacentZones.add(12);
                courtAdjacentZones.add(13);
                break;
            case 11: // left elbow
                courtAdjacentZones.add(6);
                courtAdjacentZones.add(7);
                courtAdjacentZones.add(8);
                courtAdjacentZones.add(10);
                courtAdjacentZones.add(12);
                courtAdjacentZones.add(13);
                break;
            case 12: // left corner
                courtAdjacentZones.add(10);
                courtAdjacentZones.add(11);
                courtAdjacentZones.add(13);
                break;
            case 13: // left wing
                courtAdjacentZones.add(8);
                courtAdjacentZones.add(10);
                courtAdjacentZones.add(11);
                courtAdjacentZones.add(12);
                courtAdjacentZones.add(14);
                break;
            case 14: // offensive halfcourt
                courtAdjacentZones.add(2);
                courtAdjacentZones.add(8);
                courtAdjacentZones.add(13);
                break;
            case 15: // defensive halfcourt
                courtAdjacentZones.add(14);
                break;
        }
        return courtAdjacentZones;
    }

    /**
     * Returns whether the first court zone is adjacent to the second one
     *
     * @param firstCourtZone
     * @param secondCourtZone
     * @return
     */
    public static boolean isAdjacentZone(int firstCourtZone, int secondCourtZone) {
        boolean adjacentZone;
        ArrayList<Integer> courtAdjacentZones = getCourtAdjacentZones(firstCourtZone);        

        /**
         * Checking if the first court zone is found in the court map, if true, check if the second zone is in adjacent
         * zones list
         */
        if (!courtAdjacentZones.contains(firstCourtZone)) {
            return false;
        } else if (firstCourtZone == secondCourtZone) {
            return true;
        } else {
            if (courtAdjacentZones.contains(secondCourtZone)) {
                adjacentZone = true;
            } else {
                adjacentZone = false;
            }
        }

        return adjacentZone;
    }

    /**
     * Returns a random zone that's adjacent to the given one
     *
     * @param courtZone Given court zone
     * @return
     */
    public static int getAdjacentZone(int courtZone) {
        int adjacentZone;
        ArrayList<Integer> courtAdjacentZones = getCourtAdjacentZones(courtZone);        

        /**
         * Retrieving the list of adjacent zones to that zone, adding the current one to the list, shuffling the list
         * and getting the first element from it
         */        
        Collections.shuffle(courtAdjacentZones);
        adjacentZone = courtAdjacentZones.get(MathUtils.generateRandomInt(0, courtAdjacentZones.size() - 1));

        return adjacentZone;
    }

    /**
     * Returns the distance, in meters, between two spots in the court
     *
     * @param from Original spot
     * @param to Destination spot
     * @return
     */
    public static double distanceBetweenCourtSpots(CourtSpot from, CourtSpot to) {
        double distance = Math.sqrt(Math.pow(to.getDistanceX() - from.getDistanceX(), 2)
                + Math.pow(to.getDistanceY() - from.getDistanceY(), 2));
        distance = Math.abs(distance);

        return distance;
    }

    /**
     * Returns a random spot from the given court zone
     *
     * @param courtZone Court zone's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getRandomCourtZoneSpot(int courtZone,
            DatabaseDirectConnection connection) {
        int randomCourtZoneSpot = 1;
        String sqlStatement = " SELECT id FROM court_spot WHERE courtZone = "
                + courtZone + " ORDER BY RAND() LIMIT 1";
        ResultSet resultSet;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            randomCourtZoneSpot = resultSet.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return randomCourtZoneSpot;
    }

    /**
     * Returns a random sectioned zone of the court
     *
     * @param zoneSections Array list containing the sectioned zones
     * @return
     */
    public static int getRandomSectionedZone(ArrayList<Integer> zoneSections) {
        int randomSectionedZone = MathUtils.generateRandomInt(0, zoneSections.size() - 1);
        return zoneSections.get(randomSectionedZone);
    }
} // end CourtUtils
