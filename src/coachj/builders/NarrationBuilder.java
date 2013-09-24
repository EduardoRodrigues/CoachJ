package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.GameNarration;
import coachj.utils.NarrationUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new game narration object
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 02/09/2013
 */
public class NarrationBuilder {

    public static GameNarration generateGameNarration(int narrationId, String language,
            DatabaseDirectConnection connection) {
        GameNarration narration = new GameNarration();
        
        ResultSet resultSet = NarrationUtils.getNarrationData(narrationId, connection);

        try {
            resultSet.first();
            narration.setId(resultSet.getShort("id"));
            narration.setNarration(resultSet.getString("narration_" + language));
            narration.setPlayType(resultSet.getString("playType"));

        } catch (SQLException ex) {
            Logger.getLogger(CourtSpotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return narration;
    }
} // end class NarrationBuilder
