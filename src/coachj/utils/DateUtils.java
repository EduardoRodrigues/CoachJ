package coachj.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for dates
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 23/08/2013
 */
public class DateUtils {

    /**
     * Calculate a date based on a given date
     *
     * @param baseDate Base date
     * @param daysToAdd An integer number to add or subtract from the given date
     * @return
     */
    public static String calculateDate(String baseDate, int daysToAdd) {
        String calculatedDate = baseDate;

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date day;

        try {
            day = dateFormat.parse(baseDate);
            calendar.setTime(day);
            calendar.add(Calendar.DATE, daysToAdd);
            calculatedDate = dateFormat.format(calendar.getTime());

        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return calculatedDate;
    }    
} // end DateUtils
