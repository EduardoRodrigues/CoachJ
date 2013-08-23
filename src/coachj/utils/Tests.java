/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.utils;

import java.util.TreeMap;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date /2013
 */
public class Tests {

    public static void main(String[] args) {

        TreeMap<Short, String> map = new TreeMap<>();

        map.put((short) 89, "fieldGoals DESC, threePointers DESC, freeThrows DESC");
        map.put((short) 90, "pass DESC");
        map.put((short) 89, "(defensiveRebound + offensiveRebound) DESC");

        System.out.println(map.firstEntry().getValue());
        System.out.println(map.lastEntry().getValue());
        
    }
} // end Tests
