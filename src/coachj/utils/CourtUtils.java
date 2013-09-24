package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.CourtSpot;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
     * @return
     */
    private static Map<Integer, ArrayList<Integer>> getCourtAdjacentZonesMap() {
        Map<Integer, ArrayList<Integer>> courtAdjacentZonesMap = new HashMap<>();
        ArrayList<Integer> zone = new ArrayList<>();

        /**
         * Inserting adjacent zones arrays into the map
         *
         * Zone 1 (right corner)
         */
        zone.add(2);
        zone.add(4);
        zone.add(5);
        courtAdjacentZonesMap.put(1, zone);
        zone.clear();

        /**
         * Zone 2 (right wing)
         */
        zone.add(1);
        zone.add(4);
        zone.add(5);
        zone.add(8);
        zone.add(14);
        courtAdjacentZonesMap.put(2, zone);
        zone.clear();

        /**
         * Zone 3 (right low post)
         */
        zone.add(4);
        zone.add(6);
        courtAdjacentZonesMap.put(3, zone);
        zone.clear();

        /**
         * Zone 4 (right side of the lane)
         */
        zone.add(1);
        zone.add(2);
        zone.add(3);
        zone.add(5);
        zone.add(6);
        zone.add(7);
        courtAdjacentZonesMap.put(4, zone);
        zone.clear();

        /**
         * Zone 5 (right elbow)
         */
        zone.add(1);
        zone.add(2);
        zone.add(4);
        zone.add(6);
        zone.add(7);
        zone.add(8);
        courtAdjacentZonesMap.put(5, zone);
        zone.clear();

        /**
         * Zone 6 (lane)
         */
        zone.add(3);
        zone.add(4);
        zone.add(5);
        zone.add(7);
        zone.add(9);
        zone.add(10);
        zone.add(11);
        courtAdjacentZonesMap.put(6, zone);
        zone.clear();

        /**
         * Zone 7 (top of the key)
         */
        zone.add(4);
        zone.add(5);
        zone.add(6);
        zone.add(8);
        zone.add(10);
        zone.add(11);
        zone.add(11);
        courtAdjacentZonesMap.put(7, zone);
        zone.clear();

        /**
         * Zone 8 (top of the arc)
         */
        zone.add(2);
        zone.add(5);
        zone.add(7);
        zone.add(11);
        zone.add(13);
        zone.add(14);
        courtAdjacentZonesMap.put(8, zone);
        zone.clear();

        /**
         * Zone 9 (left low post)
         */
        zone.add(6);
        zone.add(10);
        courtAdjacentZonesMap.put(9, zone);
        zone.clear();

        /**
         * Zone 10 (left side of the lane)
         */
        zone.add(6);
        zone.add(7);
        zone.add(9);
        zone.add(11);
        zone.add(12);
        zone.add(13);
        courtAdjacentZonesMap.put(10, zone);
        zone.clear();

        /**
         * Zone 11 (left elbow)
         */
        zone.add(6);
        zone.add(7);
        zone.add(8);
        zone.add(10);
        zone.add(12);
        zone.add(13);
        courtAdjacentZonesMap.put(11, zone);
        zone.clear();

        /*
         * Zone 12 (left corner)
         */
        zone.add(10);
        zone.add(11);
        zone.add(13);
        courtAdjacentZonesMap.put(12, zone);
        zone.clear();

        /**
         * Zone 13 (left wing)
         */
        zone.add(8);
        zone.add(10);
        zone.add(11);
        zone.add(12);
        zone.add(14);
        courtAdjacentZonesMap.put(13, zone);
        zone.clear();

        /**
         * Zone 14 (offensive halfcourt)
         */
        zone.add(2);
        zone.add(8);
        zone.add(13);
        courtAdjacentZonesMap.put(14, zone);
        zone.clear();

        /**
         * Zone 15 (defensive halfcourt)
         */
        zone.add(14);
        courtAdjacentZonesMap.put(15, zone);
        zone.clear();

        return courtAdjacentZonesMap;
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
        Map<Integer, ArrayList<Integer>> courtAdjacentZonesMap = getCourtAdjacentZonesMap();
        ArrayList<Integer> adjacentZones;

        /**
         * Checking if the first court zone is found in the court map, if true,
         * check if the second zone is in adjacent zones list
         */
        if (!courtAdjacentZonesMap.containsKey(firstCourtZone)) {
            return false;
        } else if (firstCourtZone == secondCourtZone) {
            return true;
        } else {
            adjacentZones = courtAdjacentZonesMap.get(firstCourtZone);

            if (adjacentZones.contains(secondCourtZone)) {
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
        Map<Integer, ArrayList<Integer>> courtAdjacentZonesMap = getCourtAdjacentZonesMap();
        ArrayList<Integer> adjacentZones;

        /**
         * Retrieving the list of adjacent zones to that zone, adding the
         * current one to the list, shuffling the list and getting the first
         * element from it
         */
        adjacentZones = courtAdjacentZonesMap.get(courtZone);
        adjacentZones.add(courtZone);
        Collections.shuffle(adjacentZones);
        adjacentZone = adjacentZones.get(0);

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
