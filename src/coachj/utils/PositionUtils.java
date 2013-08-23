package coachj.utils;

import java.util.Random;

/**
 * Utility class for defining players' positions
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 30/07/2013
 */
public class PositionUtils {

    /**
     * Gets a random position to a player
     *
     * @return
     */
    public static String getRandomPosition() {
        String position;
        String[] positions = {"PG", "SG", "SF", "PF", "C"};

        /**
         * Generates random numbers
         */
        Random generator = new Random();
        /**
         * generating random position
         */
        position = positions[generator.nextInt(5)];

        return position;
    }

    /**
     * Sets the second position to a player, according to his primary position
     * and his height
     * 
     * @param position Player's primary position
     * @param height Player's height
     * @return 
     */
    public static String setSecondPosition(String position, int height) {
        String position2 = null;

        switch (position) {
            case "PG":
                position2 = "SG";
                break;
            case "C":
                position2 = "PF";
                break;
            case "SG":
                if (height < 195) {
                position2 = "PG";
            } else {
                position2 = "SF";
            }
                break;
            case "SF":
                if (height < 200) {
                position2 = "SG";
            } else {
                position2 = "PF";
            }
                break;
            case "PF":
                if (height < 205) {
                position2 = "SF";
            } else {
                position2 = "C";
            }
                break;
        }

        return position2;
    }
    
    /**
     * 
     * @return 
     */
    public static char setRandomShootingHand()  {
        String hand;
        String[] hands = {"R", "R", "L", "R", "R", "L"};
        
        /**
         * Generates random numbers
         */
        Random generator = new Random();
        /**
         * generating random position
         */
        hand = hands[generator.nextInt(6)];
        
        return hand.charAt(0);
    }  
   

} // end PositionUtils
