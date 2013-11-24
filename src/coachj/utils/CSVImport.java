/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.utils;

import coachj.builders.PlayerBuilder;
import coachj.dao.DatabaseDirectConnection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date /2013
 */
public class CSVImport {

    public static void main(String args[]) {

         /**
         * Creating and opening database connection
         */
        DatabaseDirectConnection connection = new DatabaseDirectConnection();
        connection.open();
        
        String csvFile = "c:/players.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int countryId;
        String firstName;
        String lastName;
        String position;

        try {
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] player = line.split(cvsSplitBy);
                countryId = Integer.parseInt(player[0]);
                firstName = player[1];
                lastName = player[2];
                position = player[3];

                System.out.println(player[0] + " " + player[1] + " " + player[2] + " " + player[3]);

                PlayerBuilder playerBuilder = new PlayerBuilder(countryId,
                    firstName, lastName, position);
                playerBuilder.setAttributes(connection);
                String playerInsertSQL = playerBuilder.generateInsertSQL();
                connection.executeSQL(playerInsertSQL);

            }
        } catch (IOException ex) {
            Logger.getLogger(CSVImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} // end CSVImport
