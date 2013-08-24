/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.utils;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date /2013
 */
public class Tests {

    public static void main(String[] args) {

        String baseDate = "2013-02-01";        
        System.out.println("Base Date:" + baseDate);
        System.out.println("2 days before:" + DateUtils.calculateDate(baseDate, -2));
        System.out.println("1 day before:" + DateUtils.calculateDate(baseDate, -1));
        System.out.println("1 day after:" + DateUtils.calculateDate(baseDate, 1));
    }
} // end Tests
