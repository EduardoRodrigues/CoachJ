package coachj.utils;

/**
 * Utility class for time
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 29/08/2013
 */
public class TimeUtils {

    public static String intToTime(int integerValue) {
        int minutes = integerValue / 60;
        int seconds = integerValue % 60;
                
        return String.format("%02d:%02d", minutes, seconds);
    }
    
} // end TimeUtils
