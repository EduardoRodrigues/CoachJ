package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.CourtSpot;
import coachj.utils.CourtUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new court spot object
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 02/09/2013
 */
public class CourtSpotBuilder {

    /**
     * Generates a new court spot object based on data retrieved from the
     * database
     *
     * @param courtSpotId Court spot's id
     * @param language Desired language
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static CourtSpot generateCourtSpot(int courtSpotId, String language,
            DatabaseDirectConnection connection) {
        CourtSpot courtSpot = new CourtSpot();

        ResultSet resultSet = CourtUtils.getCourtSpotData(courtSpotId, connection);

        try {
            resultSet.first();
            courtSpot.setBasketPoints(resultSet.getShort("score"));
            courtSpot.setCourtZone(resultSet.getShort("courtZone"));
            courtSpot.setDescription(CourtUtils.getCourtZoneDescription(courtSpot.getCourtZone(),
                    language, connection));
            courtSpot.setDistanceX(resultSet.getShort("horizontalDistance"));
            courtSpot.setDistanceY(resultSet.getShort("verticalDistance"));
            courtSpot.setId(courtSpotId);
            courtSpot.setReceivingConstruction(resultSet.getString("receivingConstruction_" + language));
            courtSpot.setScoringConstruction(resultSet.getString("scoringConstruction_" + language));

        } catch (SQLException ex) {
            Logger.getLogger(CourtSpotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courtSpot;
    }
} // end class CourtSpotBuilder
