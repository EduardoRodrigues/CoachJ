package coachj.utils;

/**
 * Validation utilities
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 07/07/2013
 */
public class ValidationUtils {

    public static boolean isLong(String stringValue) {
        boolean isLong = true;

        try {
            long longValue = Long.parseLong(stringValue);
        } catch (NumberFormatException ex) {
            isLong = false;
        }

        return isLong;
    }
} // end class ValidationUtils
